
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;


//GAME SERVER IMPLEMENTS:
// SERIALIZABLE AND RUNNABLE (WHICH ARE BUILT IN)
public class PiratesServer implements Serializable, Runnable {

	//SERVER LOGIC
	private static final long serialVersionUID = 1L;
	public static final int piratesServerPortNum = 3010;

	//3 PLAYERS SPECIFIED
	Server[] playerServer = new Server[3];
	ServerSocket ss;
	Boolean isRunning = true;
	//-------------------------------------------


	//GAME LOGIC
	PiratesGame game = new PiratesGame();
	int numPlayers;
	PiratesPlayer[] players = new PiratesPlayer[3];
	public boolean isAcceptingConnections;
	private int turnsMade;
	private int currentPlayer = 0;
	//-------------------------------------------



	//MAIN
	public static void main(String args[]) throws Exception {
		//1. START CREATING A GAME SERVER
		PiratesServer piratesServer = new PiratesServer();

		//2. ACCEPT CONNECTIONS
		piratesServer.acceptConnections();

		//3. START THE GAME LOOP
		piratesServer.gameLoop();
	}

	//
	@Override
	public void run() {
		try {
			acceptConnections();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		while(isRunning){

		}
	}

	//STEP 1
	public PiratesServer() {
		System.out.println("Starting game server");

		//SPECIFY SOME GAME LOGIC VARIABLES
		numPlayers = 0;
		turnsMade = 0;


		// initialize the players list with new players
		for (int i = 0; i < players.length; i++) {
			players[i] = new PiratesPlayer(" ");
		}

		//CREATES GAME SERVER
		try {
			ss = new ServerSocket(piratesServerPortNum);
		} catch (IOException ex) {
			System.out.println("Server Failed to open");
		}
		PiratesServerInfo myServerInfo = new PiratesServerInfo(this);
		Thread myServerInfoThread = new Thread(myServerInfo);
		myServerInfoThread.start();
	}

	/**
	 * Resets the server
	 *
	 */

	//JUST HARD RESETS THE GAME
	synchronized public String hardReset() throws IOException {
		numPlayers = 0;
		//turnsMade = 0;
		//maxTurns = 13;
		currentPlayer = 0;

		for (int i = 0; i < players.length; i++) {
			players[i] = new PiratesPlayer(" ");
			playerServer[i].terminate();
			playerServer[i] = null;
		}
		return "server reset";
	}


	synchronized public String [] getPlayerOrder(){
		String [] playerOrder = new String [3];
		for (int i = 0; i < 3; i++){
			playerOrder[i] = players[i].name;
		}
		return playerOrder;
	}

	/*
	 * -----------Networking stuff ----------
	 * 
	 */

	//STEP 2: ONCE GAME SERVER OBJECT CREATED,
	//THEN IT WILL START ACCEPTING CONNECTIONS
	synchronized public void acceptConnections() throws ClassNotFoundException {

		//SERVER ALREADY SET UP
		//WAITING FOR CLIENTS (PLAYERS)
		try {
			System.out.println("Waiting for players...");

			//GAME SET AT 3 PLAYERS
			while (numPlayers < 3) {
				isAcceptingConnections = true;

				//ACCEPTS A PLAYER, INCREASE NUMBER PLAYERS BY 1
				Socket s = ss.accept();
				numPlayers++;


				//----------------------------------------------------
				//SO THIS THEN CREATES A SERVER
				//EACH PLAYER NEEDS ITS OWN SERVER?
				Server server = new Server(s, numPlayers);
				// send the player number
				server.dOut.writeInt(server.playerId);
				server.dOut.flush();
				//---------------------------------------------------

				// get the player name
				//CREATING PLAYER OBJ
				//PRINT NAME ONCE JOINED
				PiratesPlayer in = (PiratesPlayer) server.dIn.readObject();
				System.out.println("Player " + server.playerId + " ~ " + in.name + " ~ has joined");


				// add the player to the player list
				//ADDING TO PLAYERS AND PLAYERSERVER LIST FROM TOP
				players[server.playerId - 1] = in;
				playerServer[numPlayers - 1] = server;
			}
			System.out.println("Three players have joined the game");

			//3 PLAYERS HAVE NOW BEEN ADDED
			//NOW START A THREAD FOR EACH PLAYER IN PLAYERSERVER
			// start the server threads
			for (int i = 0; i < playerServer.length; i++) {
				Thread t = new Thread(playerServer[i]);
				t.start();
			}


			// start their threads
		} catch (IOException ex) {
			System.out.println("Could not connect 3 players");
		}

		//JUST A VAR TO ACCEPT CONNECTIONS OR NOT
		isAcceptingConnections = false;
	}

	//AT THIS POINT 3 (OR SET NUM OF PLAYERS) HAVE CONNECTED TO SERVER
	//---------------------------------------------------------------------



	//STEP 3: STARTING THE LOGIC OF THE GAME
	synchronized public void gameLoop() {

		playerServer[0].sendPlayers(players);
		playerServer[1].sendPlayers(players);
		playerServer[2].sendPlayers(players);

		playerServer[0].sendTurnNo(-1);
		playerServer[1].sendTurnNo(-1);
		playerServer[2].sendTurnNo(-1);


	}

	public void setTurnsMade(int turnsMade) {
		this.turnsMade = turnsMade;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	//SERVER
	public class Server implements Runnable {
		private Socket socket;
		private ObjectInputStream dIn;
		private ObjectOutputStream dOut;
		private int playerId;
		private Boolean isRunning = true;

		public Server(Socket s, int playerid) {
			socket = s;
			playerId = playerid;
			try {
				dOut = new ObjectOutputStream(socket.getOutputStream());
				dIn = new ObjectInputStream(socket.getInputStream());
			} catch (IOException ex) {
				System.out.println("Server Connection failed");
			}
		}

		/*
		 * run function for threads --> main body of the thread will start here
		 */
		public void run() {
			try {
				while (isRunning) {
				}

			} catch (Exception ex) {
				{
					System.out.println("Run failed");
					ex.printStackTrace();
				}
			}
		}

		/*
		 * send the scores to other players
		 */
		public void sendPlayers(PiratesPlayer[] pl) {
			try {
				for (PiratesPlayer p : pl) {
					dOut.writeObject(p);
					dOut.flush();
				}

			} catch (IOException ex) {
				System.out.println("Score sheet not sent");
				ex.printStackTrace();
			}

		}

		/*
		 * receive scores of other players
		 */
		public void sendTurnNo(int r) {
			try {
				dOut.writeInt(r);
				dOut.flush();
			} catch (Exception e) {
				System.out.println("Score sheet not received");
				e.printStackTrace();
			}
		}

		void terminate() throws IOException {
			socket.close();
			isRunning = false;
		}

	}

}
