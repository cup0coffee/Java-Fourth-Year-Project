import Card.PiratesFortuneCard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class PiratesPlayer implements Serializable {

    private static final long serialVersionUID = 1L;
    public String name;

    int playerId = 0;

    PiratesGame piratesGame = new PiratesGame();
    private int[] scoreSheet = new int[1];

    public int skullIslandDeduction = 0;

    Client clientConnection;

    PiratesPlayer[] players = new PiratesPlayer[3];
    Boolean inTestMode = false;

    //ADDED: DECK OF FORTUNE CARDS
    private ArrayList<PiratesFortuneCard> deck;

    //MENU FOR PLAYER ON THEIR SPECIFIC SCREEN
    public int[] playRound(String[] dieRoll, PiratesFortuneCard fortuneCard, int deckSize) {

        Scanner myObj = new Scanner(System.in);

        //FROM YAHTZEE
        int count = 0;
        int stop = 0;
        //----

        int numOfRoundRolls = 0;
        int skullCount = 0;
        int swordCount = 0;
        int currentDiceScore = 0;
        boolean hasRolledAtLeastOneSkull = true;
        boolean hasSorceressRerollAvailable = true;
        String[] treasureToKeep = new String[0];

        //PRINT FORTUNE CARD
        piratesGame.printFortuneCard(fortuneCard, deckSize);

        //ROLL INITIAL 8 DIE
        piratesGame.printDieRoll(dieRoll);

        while (stop == 0) {

            //CHECK FOR SKULL COUNT
            skullCount = piratesGame.checkSkullCount(dieRoll, fortuneCard);

            //CHECK FOR SKULL ISLAND
            if(skullCount >= 4 && numOfRoundRolls == 0) {

                //HANDLE ISLAND OF SKULLS
                boolean invalidReroll = true;

                String[] die = null;

                int startingSkullCount = skullCount;

                //KEEP GOING UNTIL NO NEW SKULLS ARE ROLLED
                while(hasRolledAtLeastOneSkull) {

                    //START WITH REROLL PROMPT
                    //WHILE LOOP TO MAKE SURE REROLL IS VALID
                    while (invalidReroll) {

                        //PLAYER MENU - NUMBER OF SKULLS
                        System.out.print("Skull(s): ");
                        if (skullCount > 0) {
                            for (int i = 0; i < skullCount; i++) {
                                System.out.print("☠");
                            }
                        } else {
                            System.out.print("0");
                            System.out.println();
                        }

                        System.out.println();

                        System.out.println("YOUR'E IN SKULL ISLAND!");
                        System.out.println("Select the die to hold (Ones not held get rerolled): (1,2...) ");
                        die = (myObj.next()).replaceAll("\\s", "").split(",");

                        //CHECK FOR ROLLING 1 DIE
                        if (die.length == 7) {
                            //CHECK FOR SORCERESS CARD TO ENABLE 1 ROLL DIE
                            if (fortuneCard.getName().equalsIgnoreCase("Sorceress") && hasSorceressRerollAvailable) {
                                //INDICATE SORCERESS CARD HAS BEEN USED, CAN NO LONGER BE USED FOR OTHER TURNS
                                System.out.println("Sorceress card used!");
                                hasSorceressRerollAvailable = false;
                                break;
                            } else if (fortuneCard.getName().equalsIgnoreCase("Sorceress") && hasSorceressRerollAvailable == false) {
                                System.out.println("You used your sorceress card already...nice try :p");
                                System.out.println("Invalid: You need to roll more than one die.");
                                System.out.println();
                                continue;
                            }
                        }

                        //CHECK FOR NOT REROLLING SKULL
                        ArrayList<Integer> rolls = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
                        for (String s : die) {
                            int rem = Integer.parseInt(s) - 1;
                            rolls.remove(rolls.indexOf(rem));
                        }

                        int skullsFound = 0;

                        for (int s : rolls) {
                            if (dieRoll[s].equalsIgnoreCase("Skull")) {
                                System.out.println("Error: You cannot reroll a skull.");
                                skullsFound++;
                                break;
                            }
                        }

                        if (skullsFound == 0) {
                            invalidReroll = false;
                        }
                    }
                    System.out.println();
                    dieRoll = piratesGame.reRollNotHeld(dieRoll, die);
                    System.out.println("New Roll: ");
                    piratesGame.printDieRoll(dieRoll);

                    //DETERMINE IF SKULL WAS ROLLED TO CONTINUE OR NOT
                    skullCount = piratesGame.checkSkullCount(dieRoll, fortuneCard);

                    if(skullCount > startingSkullCount) {
                        startingSkullCount++;
                    } else if (skullCount == startingSkullCount) {
                        break;
                    }
                }

                //PLAYER MENU - NUMBER OF SKULLS
                System.out.print("Skull(s): ");
                if (skullCount > 0) {
                    for (int i = 0; i < skullCount; i++) {
                        System.out.print("☠");
                    }
                } else {
                    System.out.print("0");
                    System.out.println();
                }

                System.out.println();

                //DEDUCT POINTS FROM OTHER PLAYERS
                System.out.println("Total skulls rolled in skull island: " + skullCount);

                if(fortuneCard.getName().equalsIgnoreCase("Captain")) {
                    setSkullIslandDeduction((skullCount * 100) * 2);
                } else {
                    setSkullIslandDeduction(skullCount * 100);
                }

                System.out.println(getSkullIslandDeduction() + " points deducted from other players!");



                stop = 1;

            } else {

                numOfRoundRolls++;

                //CHECK FOR SWORD COUNT
                swordCount = piratesGame.checkSwordCount(dieRoll);

                //CHECK FOR DEATH
                if (piratesGame.isPlayerDead(skullCount)) {
                    System.out.println("YOU DEAD (You rolled " + skullCount + " skulls)");

                    //SCORE WHAT'S IN TREASURE CHEST
                    if (fortuneCard.getName().equalsIgnoreCase("Treasure Chest")) {
                        setScoreSheet(0, piratesGame.scoreDieUponDeathWithTreasure(scoreSheet, dieRoll, treasureToKeep, fortuneCard));
                        System.out.println("You just earned " + piratesGame.scoreDieUponDeathWithTreasure(scoreSheet, dieRoll, treasureToKeep, fortuneCard) + " points from your treasure! :)");
                        piratesGame.printTreasureChest(dieRoll, treasureToKeep, fortuneCard);
                    }

                    //DECREMENT SEA BATTLE BONUS
                    if (fortuneCard.getName().equalsIgnoreCase("Sea Battle (2 Swords)") ||
                            fortuneCard.getName().equalsIgnoreCase("Sea Battle (3 Swords)") ||
                            fortuneCard.getName().equalsIgnoreCase("Sea Battle (4 Swords)")) {

                        currentDiceScore = piratesGame.scoreDie(dieRoll, fortuneCard);

                        //CHECK TO MAKE SURE PLAYER'S SCORE WON'T BECOME NEGATIVE FROM LOSING POINTS
                        setScoreSheet(0, piratesGame.scoreDie(scoreSheet, dieRoll, fortuneCard));


                        //CHECK IF THEY LOST POINTS OR GAINED
                        if (currentDiceScore >= 0) {
                            System.out.println("You just earned " + piratesGame.printCurrentScore(dieRoll, fortuneCard) + " points!");
                        } else if (currentDiceScore < 0) {
                            System.out.println("You just lost " + piratesGame.printCurrentScore(dieRoll, fortuneCard) + " points...");
                        }
                    }

                    stop = 1;
                    continue;
                }

                //INCREMENT TURN
                numOfRoundRolls++;

                //PLAYER MENU - DICE ROLL SCORE
                System.out.println(piratesGame.printCurrentScoreDice(dieRoll, fortuneCard));

                //PLAYER MENU - SCORE
                System.out.println("If you score this round, you will earn: " + piratesGame.printCurrentScore(dieRoll, fortuneCard));

                //PLAYER MENU - # ROLLS MADE THIS ROUND BY PLAYER
                System.out.println("# rolls made this round: " + numOfRoundRolls);

                //PLAYER MENU - NUMBER OF SKULLS
                System.out.print("Skull(s): ");
                if (skullCount > 0) {
                    for (int i = 0; i < skullCount; i++) {
                        System.out.print("☠");
                    }
                } else {
                    System.out.print("0");
                    System.out.println();
                }

                System.out.println();

                //IF SWORD CARD
                if (fortuneCard.getName().equalsIgnoreCase("Sea Battle (2 Swords)") ||
                        fortuneCard.getName().equalsIgnoreCase("Sea Battle (3 Swords)") ||
                        fortuneCard.getName().equalsIgnoreCase("Sea Battle (4 Swords)")) {
                    System.out.print("Sword(s): ");
                    if (swordCount > 0) {
                        for (int i = 0; i < swordCount; i++) {
                            System.out.print("⚔");
                        }
                    } else {
                        System.out.print("0");
                    }


                }

                System.out.println();
                System.out.println();


                //PLAYER MENU - OPTION SELECTION
                System.out.println("Select an action: ");
                if (count < 3) {
                    System.out.println("(1) Choose dice number to roll again");
                    System.out.println("(2) Roll all again");
                }
                System.out.println("(3) Score this round");

                //FOR TREASURE CHEST
                if (fortuneCard.getName().equalsIgnoreCase("Treasure Chest")) {
                    System.out.println("(4) Select dice to place in treasure chest");
                }

                int act = myObj.nextInt();


                //OPTION 1
                if (act == 1 && count < 3) {

                    boolean invalidReroll = true;

                    String[] die = null;

                    while (invalidReroll) {

                        System.out.println("Select the die to hold (Ones not held get rerolled): (1,2...) ");
                        die = (myObj.next()).replaceAll("\\s", "").split(",");

                        //CHECK FOR ROLLING 1 DIE
                        if (die.length == 7) {
                            //CHECK FOR SORCERESS CARD TO ENABLE 1 ROLL DIE
                            if (fortuneCard.getName().equalsIgnoreCase("Sorceress") && hasSorceressRerollAvailable) {
                                //INDICATE SORCERESS CARD HAS BEEN USED, CAN NO LONGER BE USED FOR OTHER TURNS
                                System.out.println("Sorceress card used!");
                                hasSorceressRerollAvailable = false;
                                break;
                            } else if (fortuneCard.getName().equalsIgnoreCase("Sorceress") && hasSorceressRerollAvailable == false) {
                                System.out.println("You used your sorceress card already...nice try :p");
                                System.out.println("Invalid: You need to roll more than one die.");
                                System.out.println();
                                continue;
                            }
                        }

                        //CHECK FOR NOT REROLLING SKULL
                        ArrayList<Integer> rolls = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
                        for (String s : die) {
                            int rem = Integer.parseInt(s) - 1;
                            rolls.remove(rolls.indexOf(rem));
                        }

                        int skullsFound = 0;

                        for (int s : rolls) {
                            if (dieRoll[s].equalsIgnoreCase("Skull")) {
                                System.out.println("Error: You cannot reroll a skull.");
                                skullsFound++;
                                break;
                            }
                        }

                        if (skullsFound == 0) {
                            invalidReroll = false;
                        }
                    }
                    System.out.println();
                    dieRoll = piratesGame.reRollNotHeld(dieRoll, die);
                    System.out.println("New Roll: ");
                    piratesGame.printDieRoll(dieRoll);
                    numOfRoundRolls++;
                }

                if (act == 2 && count < 3) {
                }

                if (act == 3) {

                    currentDiceScore = piratesGame.scoreDie(dieRoll, fortuneCard);

                    setScoreSheet(0, piratesGame.scoreDie(scoreSheet, dieRoll, fortuneCard));

                    //CHECK IF THEY LOST POINTS OR GAINED
                    if (currentDiceScore >= 0) {
                        System.out.println("You just earned " + piratesGame.printCurrentScore(dieRoll, fortuneCard) + " points!");
                    } else if (currentDiceScore < 0) {
                        System.out.println("You just lost " + piratesGame.printCurrentScore(dieRoll, fortuneCard) + " points...");
                    }

                    stop = 1;
                }

                //OPTION 4: TREASURE CHEST
                if (act == 4) {
                    boolean invalidReroll = true;

                    while (invalidReroll) {

                        System.out.println("Select the die to hold in treasure chest (Ones not held get rerolled): (1,2...) ");
                        treasureToKeep = (myObj.next()).replaceAll("\\s", "").split(",");

                        //CHECK FOR ROLLING MORE THAN 1 DIE
                        if (treasureToKeep.length <= 0 && treasureToKeep.length > 8) {
                            System.out.println("Invalid: You need to store between 1 & 8 dice in treasure chest!");
                            continue;
                        }

                        //DO SKULL CHECK FOR TREASURE CHEST LATER ON

                        if ((treasureToKeep.length > 0 && treasureToKeep.length <= 8)) {
                            invalidReroll = false;
                        }
                    }

                    //TREASURE CHEST IS STORED
                    piratesGame.printTreasureChest(dieRoll, treasureToKeep, fortuneCard);
                    System.out.println("Treasure chest stored. Please choose how to proceed.");
                    System.out.println();
                    piratesGame.printDieRoll(dieRoll);
                    System.out.println();

                }
            }


        }


        return this.scoreSheet;

    }

    public int[] scoreRound(int r, String[] dieRoll) {
        return getScoreSheet();


    }

    public int getScore() {
        int sc = 0;
        if (getScoreSheet()[0] >= 0)
            sc += scoreSheet[0];
        return sc;
    }

    public int getSkullIslandDeduction() {
        return this.skullIslandDeduction;
    }

    public int[] getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(int cat, int score) {
        int newScore = this.getScore() + score;

        //CHECK THAT SCORE DOES NOT BECOME NEGATIVE
        if (newScore < 0) {
            newScore = 0;
        }

        this.scoreSheet[cat] = newScore;
        //this.scoreSheet[cat] = score;
    }

    public void setScoreSheet(int[] ss) {
        this.scoreSheet = ss;
    }

    public void setSkullIslandDeduction(int ss) {
        this.skullIslandDeduction = ss;
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

    /*
     * update turns
     */
    public void printPlayerScores(PiratesPlayer[] pl) {
        // print the scoresheets

        if (playerId == 1) {
            piratesGame.printScoreSheet(pl[0]);
            piratesGame.printScoreSheet(pl[1]);
            piratesGame.printScoreSheet(pl[2]);
        } else if (playerId == 2) {
            piratesGame.printScoreSheet(pl[1]);
            piratesGame.printScoreSheet(pl[0]);
            piratesGame.printScoreSheet(pl[2]);
        } else {
            piratesGame.printScoreSheet(pl[2]);
            piratesGame.printScoreSheet(pl[0]);
            piratesGame.printScoreSheet(pl[1]);
        }
    }

    public void startGame() {
        // receive players once for names
        players = clientConnection.receivePlayer();


        while (true) {

            //EACH PLAYER'S MENU
            int round = clientConnection.receiveRoundNo();
            if (round == -1)
                break;
            //----------------
            //int skullIslandDeduction = clientConnection.receiveSkullIslandDeduction();
            //---------------
            System.out.println("\n \n \n ********Round Number " + round + "********");
            int[][] pl = clientConnection.receiveScores();
            for (int i = 0; i < 3; i++) {
                players[i].setScoreSheet(pl[i]);
            }
            printPlayerScores(players);

            PiratesFortuneCard fortuneCard = clientConnection.receiveFortuneCard();
            int fortuneCardsRemaining = clientConnection.receiveDeckSize();

            //-------------------------------
            //CREATE DECK - old code
            //piratesGame.createFortuneDeck();
            //-------------------------------

            //GET INITIAL DIE ROLL
            String[] dieRoll = piratesGame.rollDice();

            //SEND FORTUNE CARD AND DIE ROLL
            clientConnection.sendScores(playRound(dieRoll, fortuneCard, fortuneCardsRemaining));
            clientConnection.sendSkullIslandDeduction(getSkullIslandDeduction());
        }

    }

    public PiratesPlayer returnWinner() {
        try {
            int[][] pl = clientConnection.receiveScores();
            for (int i = 0; i < 3; i++) {
                players[i].setScoreSheet(pl[i]);
            }
            printPlayerScores(players);
            PiratesPlayer win = (PiratesPlayer) clientConnection.dIn.readObject();
            if (playerId == win.playerId) {
                System.out.println("You win!");
            } else {
                System.out.println("The winner is " + win.name);
            }

            System.out.println("Game over!");
            return win;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

        public void sendSkullIslandDeduction(int deduction) {
            try {
                dOut.writeInt(deduction);
                dOut.flush();

            } catch (IOException e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
        }

        /*
         * receive scoresheet
         */
        public void sendScores(int[] scores) {
            try {
                for (int i = 0; i < scores.length; i++) {
                    dOut.writeInt(scores[i]);
                }
                dOut.flush();

            } catch (IOException e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
        }

        //ADDED: RECEIVE DECK
        public PiratesFortuneCard receiveFortuneCard() {

            PiratesFortuneCard receivedFortuneCard = null;

            try {
                receivedFortuneCard = (PiratesFortuneCard) dIn.readObject();
            } catch (IOException e) {
                System.out.println("Card not received");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                e.printStackTrace();
            }

            return receivedFortuneCard;

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
        public int[][] receiveScores() {
            try {
                int[][] sc = new int[3][1];
                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < 1; i++) {
                        sc[j][i] = dIn.readInt();
                    }
                    System.out.println();
                }

                return sc;
            } catch (Exception e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
            return null;
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

        public int receiveDeckSize() {
            try {
                return dIn.readInt();

            } catch (IOException e) {
                System.out.println("Deck size not received");
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

        public int receiveSkullIslandDeduction() {
            try {
                return dIn.readInt();

            } catch (IOException e) {
                System.out.println("Score sheet not received");
                e.printStackTrace();
            }
            return 0;
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
        p.returnWinner();
        myObj.close();
    }
}

