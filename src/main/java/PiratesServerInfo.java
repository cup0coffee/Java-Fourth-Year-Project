import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class is to be connected to GameServer as a child thread and creates ServerInfoServers
 * for testCases to communicate with GameServer is to act as a mediator
 *
 * @author Sebastian Gadzinski
 */

public class PiratesServerInfo implements Runnable{

    //SERVER INFO
    PiratesServer piratesServer;
    boolean isRunning = true;
    public static final int serverInfoPortNum = 3012;

    public PiratesServerInfo(PiratesServer gameServer) {
        this.piratesServer = gameServer;
    }

    public void terminate(){
        isRunning = false;
    }

    /**
     * Listens to TestCases trying to connect via socket and gives them separate ServerInfoServers to communicate with
     *
     */
    @Override
    public void run() {
        //Create the listener socket and wait for a new test case to connect
        ServerSocket listener = null;
        try {
            listener = new ServerSocket(serverInfoPortNum);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Assign a separate ServerInfoServer to communicate with the test case
        while (isRunning) {
            Socket client = null;
            try {
                client = listener.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server Info Connected");

            ServerInfoServer serverInfoServer = new ServerInfoServer(client);
            Thread serverInfoThread = new Thread(serverInfoServer);

            serverInfoThread.start();
        }
    }

    /**
     * ServerInfoServer Object that is to be connected to GameServer as a child thread and is to act as a mediator for the TestCase and GameServer
     *
     * @author Sebastian Gadzinski
     */
    public class ServerInfoServer implements Runnable {
        private Socket socket;
        private ObjectInputStream dIn;
        private ObjectOutputStream dOut;
        private int playerId;
        private boolean isRunning = true;

        public ServerInfoServer(Socket s) {
            socket = s;
            try {
                dOut = new ObjectOutputStream(socket.getOutputStream());
                dIn = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                System.out.println("Server Connection failed");
            }
        }

        /*
         * Life of the ServerInfoServer
         *
         */
        public void run() {

            while (isRunning) {
                //While this server is alive
                String toDo = readString();
                System.out.println("\nServerInfo received command : " + toDo);

                //This is usually called when a new test in a TestCase is being done
                if (toDo.equals("ensureAcceptingConnections")) {
                    try {
                        if(!piratesServer.isAcceptingConnections){
                            piratesServer.acceptConnections();
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                //This is called when a TestCase test wants to start a game, it asks then is required to give max turns, turns made,
                //and current player. You could make a GameServerMetaData Serializable class with all the metadata and just use that, but
                //I got lazy \:
                else if (toDo.equals("startGame")) {
                    piratesServer.setTurnsMade(readInt());
                    piratesServer.setCurrentPlayer(readInt());
                    piratesServer.gameLoop();
                }
                //This is called at the bottom of a test to reset the players and the game-loop
                else if (toDo.equals("hardReset")) {
                    try {
                        String hardResetResult = piratesServer.hardReset();
                        sendString(hardResetResult);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //This is called when you want to see the players connected currently
                else if (toDo.equals("getPlayersConnected")) {
                    sendPlayers(piratesServer.players);
                }
                //This is called to kill the ServerInfoServer
                else if (toDo.equals("kill")) {
                    break;
                }
            }

        }

        /**
         * ================================================_NETWORKING_================================================
         */

        /**
         * Reads a string from the NetworkTests Input Stream
         *
         */
        private String readString() {
            try {
                System.out.println("\nServerInfo waiting for next request...\n");
                return dIn.readUTF();

            } catch (Exception e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Writes a string to the NetworkTests Output Stream
         *
         * @param aString String to send
         */
        public void sendString(String aString) {
            try {
                dOut.writeUTF(aString);
                dOut.flush();
            } catch (Exception e) {
                System.out.println("Score sheet not sent");
                e.printStackTrace();
            }
        }

        /**
         * Writes all players from GameServer to the ServerInfoSockets Output Stream
         *
         * @param pl players to send
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

        /**
         * Reads int from ServerInfoSockets Output Stream
         *
         */
        private int readInt() {
            try {
                return dIn.readInt();

            } catch (Exception e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
            return -1;
        }


        void terminate() throws IOException {
            socket.close();
            isRunning = false;
        }

    }


}

