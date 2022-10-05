
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;


public class PiratesPlayer implements Serializable {

    private static final long serialVersionUID = 1L;
    public String name;

    int playerId = 0;

    PiratesGame piratesGame = new PiratesGame();
    private int[] scoreSheet = new int[1];

    Client clientConnection;

    PiratesPlayer[] players = new PiratesPlayer[3];
    Boolean inTestMode = false;

    //MENU FOR PLAYER ON THEIR SPECIFIC SCREEN
    public int[] playRound(String[] dieRoll) {

        Scanner myObj = new Scanner(System.in);

        int count = 0; // reroll 3 times
        int stop = 0;



        while (stop == 0) {


            //PLAYER MENU - OPTION SELECTION
            System.out.println("Select an action: ");
            if (count < 3) {
                System.out.println("(1) Choose dice number to roll again");
                System.out.println("(2) Roll all again");
            }
            System.out.println("(3) Score this round");

            int act = myObj.nextInt();


            //OPTION 1
            if (act == 1 && count < 3) {

            }

            if (act == 2 && count < 3) {

            }


            if (act == 3) {

            }
            stop = 1;
        }
        return this.scoreSheet;
    }


    public PiratesPlayer getPlayer() {
        return this;
    }

    /*
     * ----------Network Stuff------------
     */

    /*
     * send the to do to test server
     */
    public void sendStringToServer(String str) {
        clientConnection.sendString(str);
    }

    public void connectToClient() {
        clientConnection = new Client();
    }

    public void connectToClient(int port) {
        clientConnection = new Client(port);
    }

    public void killClient() {
        clientConnection.terminate();
    }

    public void initializePlayers() {
        for (int i = 0; i < 3; i++) {
            players[i] = new PiratesPlayer(" ");
        }
    }


    public void startGame() {
        // receive players once for names
        players = clientConnection.receivePlayer();


        while (true) {
            int round = clientConnection.receiveRoundNo();
            if (round == -1)
                break;
        }

    }

    public static final int piratesServerPortNum = 3010;

    private class Client {
        Socket socket;
        private ObjectInputStream dIn;
        private ObjectOutputStream dOut;

        public Client() {
            try {
                socket = new Socket("localhost", piratesServerPortNum);
                dOut = new ObjectOutputStream(socket.getOutputStream());
                dIn = new ObjectInputStream(socket.getInputStream());

                playerId = dIn.readInt();

                System.out.println("Connected as " + playerId);
                sendPlayer();

            } catch (IOException ex) {
                System.out.println("Client failed to open");
            }
        }

        public Client(int portId) {
            try {
                socket = new Socket("localhost", portId);
                dOut = new ObjectOutputStream(socket.getOutputStream());
                dIn = new ObjectInputStream(socket.getInputStream());

                playerId = dIn.readInt();

                System.out.println("Connected as " + playerId);
                sendPlayer();

            } catch (IOException ex) {
                System.out.println("Client failed to open");
            }
        }

        /*
         * function to
         */
        public void sendPlayer() {
            try {
                dOut.writeObject(getPlayer());
                dOut.flush();
            } catch (IOException ex) {
                System.out.println("Player not sent");
                ex.printStackTrace();
            }
        }

        /*
         * function to send strings
         */
        public void sendString(String str) {
            try {
                dOut.writeUTF(str);
                dOut.flush();
            } catch (IOException ex) {
                System.out.println("String not sent");
                ex.printStackTrace();
            }
        }

        /*
         * receive scores of other players
         */
        public PiratesPlayer[] receivePlayer() {
            PiratesPlayer[] pl = new PiratesPlayer[3];
            try {
                PiratesPlayer p = (PiratesPlayer) dIn.readObject();
                pl[0] = p;
                p = (PiratesPlayer) dIn.readObject();
                pl[1] = p;
                p = (PiratesPlayer) dIn.readObject();
                pl[2] = p;
                return pl;

            } catch (IOException e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                e.printStackTrace();
            }
            return pl;
        }

        /*
         * receive scores of other players
         */
        public int receiveRoundNo() {
            try {
                return dIn.readInt();

            } catch (IOException e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
            return 0;
        }

        void terminate() {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
        }

    }

    /*
     * ---------Constructor and Main class-----------
     */

    /*
     * constructor takes the name of the player and sets the score to 0
     */
    public PiratesPlayer(String n) {
        name = n;
        for (int i = 0; i < scoreSheet.length; i++) {
            scoreSheet[i] = -1;
        }
    }

    public static void main(String args[]) {

        //ASK PLAYER FOR NAME
        Scanner myObj = new Scanner(System.in);
        System.out.print("What is your name ? ");
        String name = myObj.next();
        PiratesPlayer p = new PiratesPlayer(name);


        p.initializePlayers();
        p.connectToClient();
        p.startGame();
        myObj.close();
    }
}

