import Card.PiratesFortuneCard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


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
	private int maxTurns;
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
		maxTurns = 13;


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
		System.out.println("Inside of gameloop");

		System.out.println("Draw a random fortune card, starting with player 1!");

		//PUT THE DECK HERE
		ArrayList<PiratesFortuneCard> deck = game.createFortuneDeck();
		int fortuneCardsRemaining = deck.size();

		//SENDS SCORE SHEET TO SERVER
		try {

			playerServer[0].sendPlayers(players);
			playerServer[1].sendPlayers(players);
			playerServer[2].sendPlayers(players);

			//KEEP GOING UNTIL SPECIFIC MAX TURNS IS REACHED
			//EACH PLAYER'S HAND IN A ROUND
			//NO MAX TURNS, NEED TO EDIT
			while (turnsMade < maxTurns) {

				//DRAW FORTUNE CARD
				PiratesFortuneCard drawnFortuneCard = game.drawFortuneCard(deck);

				//INCREMENT TURN
				turnsMade++;

				// send the round number
				System.out.println("*****************************************");
				System.out.println("Round number " + turnsMade);
				//CURRENT PLAYER RECEIVES THE FOLLOWING
				playerServer[currentPlayer].sendTurnNo(turnsMade);
				playerServer[currentPlayer].sendScores(players);
				//ADDED FOR SENDING DECK AND DECK SIZE
				playerServer[currentPlayer].sendFortuneCard(drawnFortuneCard);
				playerServer[currentPlayer].sendDeckSize(deck.size());

				//-------------------------
				players[currentPlayer].setScoreSheet(playerServer[currentPlayer].receiveScores());
				System.out.println("Player " + currentPlayer + " completed turn and their score is " + players[currentPlayer].getScore());

				currentPlayer++;
				if (currentPlayer == 3) currentPlayer = 0;

			}

			playerServer[0].sendTurnNo(-1);
			playerServer[1].sendTurnNo(-1);
			playerServer[2].sendTurnNo(-1);

			// send final score sheet after bonus added
			playerServer[0].sendScores(players);
			playerServer[1].sendScores(players);
			playerServer[2].sendScores(players);


			PiratesPlayer p = game.getWinner(players);
			System.out.println("The winner is " + p.name);
			for (int i = 0; i < playerServer.length; i++) {
				playerServer[i].dOut.writeObject(p);
				playerServer[i].dOut.flush();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	public void setMaxTurns(int maxTurns) {
		this.maxTurns = maxTurns;
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

		//SEND THE REMAINING DECK TO OTHER PLAYERS
		public void sendFortuneCard(PiratesFortuneCard drawnFortuneCard) {
			try {
				dOut.writeObject(drawnFortuneCard);

			} catch (IOException ex) {
				System.out.println("Card not sent!");
				ex.printStackTrace();
			}
		}

		public void sendDeckSize(int r) {
			try {
				dOut.writeInt(r);
				dOut.flush();
			} catch (Exception e) {
				System.out.println("Deck size not received");
				e.printStackTrace();
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

		/*
		 * receive scores of other players
		 */
		public int[] receiveScores() {
			try {
				int[] sc = new int[1];
				for (int i = 0; i < 1; i++) {
					sc[i] = dIn.readInt();
				}
				return sc;
			} catch (Exception e) {
				System.out.println("Score sheet not received");
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * send scores of other players
		 */
		public void sendScores(PiratesPlayer[] pl) {
			try {
				for (int i = 0; i < pl.length; i++) {
					for (int j = 0; j < pl[i].getScoreSheet().length; j++) {
						dOut.writeInt(pl[i].getScoreSheet()[j]);
					}
				}
				dOut.flush();
			} catch (Exception e) {
				System.out.println("Score sheet not sent");
				e.printStackTrace();
			}
		}

		void terminate() throws IOException {
			socket.close();
			isRunning = false;
		}

	}

}
