
import Card.FortuneGenerator;
import Card.PiratesFortuneCard;
import Card.PiratesFortuneCardGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PiratesGame implements Serializable {


    private static final long serialVersionUID = 1L;

    public String[] rollDice() {
        String[] dieChoices = new String[6];

        dieChoices[0] = ("Monkey");
        dieChoices[1] = ("Parrot");
        dieChoices[2] = ("Coin");
        dieChoices[3] = ("Diamond");
        dieChoices[4] = ("Sword");
        dieChoices[5] = ("Skull");

        String[] die = new String[8];

        for (int i = 0; i < 8; i++) {
            int rand = (int) (Math.random() * 6);
            die[i] = dieChoices[rand];
        }
        return die;
    }

    public String[] reRollNotHeld(String[] dieRoll, String[] held) {

        ArrayList<Integer> rolls = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        for (String s : held) {
            int rem = Integer.parseInt(s) - 1;
            rolls.remove(rolls.indexOf(rem));
        }
        // remove the index from the ones to be rolled
        for (int s : rolls) {
            dieRoll = rerollDice(dieRoll, (s));
        }
        return dieRoll;
    }

    public int scoreTreasureChest(String[] dieRoll, String[] held, PiratesFortuneCard card) {

        ArrayList<Integer> rolls = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

        //HELD - 5,6,7,8
        for (String s : held) {
            int rem = Integer.parseInt(s) - 1;
            rolls.remove(rolls.indexOf(rem));
        }

        //ASSIGN SKULLS TO OTHER DICE THAT WEREN'T IN TREASURE CHEST
        //THIS IS SO NOT POINTS WILL BE COLLECTED FOR ANYTHING BESDIES WHAT WAS IN TREASURE CHEST
        for (int s : rolls) {
            dieRoll = assignSkull(dieRoll, (s));
        }


        return scoreDie(dieRoll, card);
    }

    public String[] assignSkull(String[] dieRoll, int i) {

        dieRoll[i] = "Skull";

        return dieRoll;
    }

    public String[] rerollDice(String[] dieRoll, int i) {

        String[] dieChoices = new String[6];

        dieChoices[0] = ("Monkey");
        dieChoices[1] = ("Parrot");
        dieChoices[2] = ("Coin");
        dieChoices[3] = ("Diamond");
        dieChoices[4] = ("Sword");
        dieChoices[5] = ("Skull");

        int rand = (int) (Math.random() * 6);
        dieRoll[i] = dieChoices[rand];

        return dieRoll;
    }

    public int scoreDieUponDeathWithTreasure(int[] scoreSheet, String[] dieRoll, String[] held, PiratesFortuneCard fortuneCard) {

        ArrayList<Integer> rolls = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

        //HELD - 5,6,7,8
        for (String s : held) {
            int rem = Integer.parseInt(s) - 1;
            rolls.remove(rolls.indexOf(rem));
        }

        //ASSIGN SKULLS TO OTHER DICE THAT WEREN'T IN TREASURE CHEST
        //THIS IS SO NOT POINTS WILL BE COLLECTED FOR ANYTHING BESDIES WHAT WAS IN TREASURE CHEST
        for (int s : rolls) {
            dieRoll = assignSkull(dieRoll, (s));
        }


        int monkey = 0;
        int parrot = 0;
        int sword = 0;
        int skull = 0;
        int coin = 0;
        int diamond = 0;

        int numOfEach[] = new int[6];

        int numOfDie = dieRoll.length;

        for(int i = 0; i < numOfDie; i++) {
            if (dieRoll[i].equalsIgnoreCase("Monkey")) {
                monkey++;
            } else if (dieRoll[i].equalsIgnoreCase("Parrot")) {
                parrot++;
            } else if (dieRoll[i].equalsIgnoreCase("Sword")) {
                sword++;
            } else if (dieRoll[i].equalsIgnoreCase("Skull")) {
                skull++;
            } else if (dieRoll[i].equalsIgnoreCase("Coin")) {
                coin++;
            } else if (dieRoll[i].equalsIgnoreCase("Diamond")) {
                diamond++;
            }
        }

        int score = 0;
        int monkeyBusiness = 0;

        if(fortuneCard.getName().equalsIgnoreCase("Gold") && coin == 8) {
            score += 100;
        } else if(fortuneCard.getName().equalsIgnoreCase("Gold") && coin != 8) {
            coin++;
        } else if(fortuneCard.getName().equalsIgnoreCase("Diamond") && diamond == 8) {
            score += 100;
        } else if(fortuneCard.getName().equalsIgnoreCase("Diamond") && diamond != 8) {
            diamond++;
        }

        //ACCOUNT FOR FORTUNE CARDS

        numOfEach[0] = monkey; //WON'T BE ACCOUNT FOR IN SCORE CALCULATION IF MONKEY BUSINESS CARD

        //ADJUSTMENT FOR MONKEY BUSINESS CARD
        int startingPoint = 0;
        if (fortuneCard.getName().equalsIgnoreCase("Monkey Business")) {
            monkeyBusiness = parrot + monkey;
            numOfEach[1] = monkeyBusiness;
            startingPoint = 1;
        } else {
            numOfEach[1] = parrot;
        }

        numOfEach[2] = sword;
        numOfEach[3] = skull;
        numOfEach[4] = coin;
        numOfEach[5] = diamond;

        //CALCULATE SCORE
        int fullChestBonus = 0;

        //ACCOUNT FOR IDENTICAL DIE
        for (int i = startingPoint; i < 6; i++) {
            if (numOfEach[i] == 3 && i != 3) {
                score+=100;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 4 && i != 3) {
                score+=200;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 5 && i != 3) {
                score+=500;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 6 && i != 3) {
                score+=1000;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 7 && i != 3) {
                score+=2000;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 8 && i != 3) {
                score+=4000;
                fullChestBonus += numOfEach[i];
            }
        }

        if((coin == 1 || coin == 2) && (!fortuneCard.getName().equalsIgnoreCase("Gold"))) {
            fullChestBonus += coin;
        }

        if((diamond == 1 || diamond == 2) && (!fortuneCard.getName().equalsIgnoreCase("Diamond"))) {
            fullChestBonus += diamond;
        }

        //CHECK FOR CHEST BONUS
        if (fullChestBonus == 8 && skull == 0) {
            score+=500;
        }

        //ACCOUNT FOR COINS
        score += (100 * coin) + (100 * diamond);

        //CARD CHECK
        score = applyFortuneCardLogic(fortuneCard, score);

        return score;
    }

    public int scoreDie(int[] scoreSheet, String[] dieRoll, PiratesFortuneCard fortuneCard) {

        int numSkulls = checkSkullCount(dieRoll, fortuneCard);

        int numSwords = checkSwordCount(dieRoll);

        //CHECK FOR SEA BATTLE
        //FAILED 2 SWORDS BY DEATH
        if(fortuneCard.getName().equalsIgnoreCase("Sea Battle (2 Swords)") && numSwords < 2 && numSkulls >= 3) {
            return fortuneCard.applyCardRule(scoreSheet[0]);
        }

        if (numSkulls == 3 || numSkulls > 4) {
            return 0;
        }

        int monkey = 0;
        int parrot = 0;
        int sword = 0;
        int skull = 0;
        int coin = 0;
        int diamond = 0;

        int numOfEach[] = new int[6];

        int numOfDie = dieRoll.length;

        for(int i = 0; i < numOfDie; i++) {
            if (dieRoll[i].equalsIgnoreCase("Monkey")) {
                monkey++;
            } else if (dieRoll[i].equalsIgnoreCase("Parrot")) {
                parrot++;
            } else if (dieRoll[i].equalsIgnoreCase("Sword")) {
                sword++;
            } else if (dieRoll[i].equalsIgnoreCase("Skull")) {
                skull++;
            } else if (dieRoll[i].equalsIgnoreCase("Coin")) {
                coin++;
            } else if (dieRoll[i].equalsIgnoreCase("Diamond")) {
                diamond++;
            }
        }

        //CHECK FOR DEATH
        if (skull >= 3 && skull != 4) {
            return 0;
        }

        int score = 0;
        int monkeyBusiness = 0;

        if(fortuneCard.getName().equalsIgnoreCase("Gold") && coin == 8) {
            score += 100;
        } else if(fortuneCard.getName().equalsIgnoreCase("Gold") && coin != 8) {
            coin++;
        } else if(fortuneCard.getName().equalsIgnoreCase("Diamond") && diamond == 8) {
            score += 100;
        } else if(fortuneCard.getName().equalsIgnoreCase("Diamond") && diamond != 8) {
            diamond++;
        }

        //ACCOUNT FOR FORTUNE CARDS

        numOfEach[0] = monkey; //WON'T BE ACCOUNT FOR IN SCORE CALCULATION IF MONKEY BUSINESS CARD

        //ADJUSTMENT FOR MONKEY BUSINESS CARD
        int startingPoint = 0;
        if (fortuneCard.getName().equalsIgnoreCase("Monkey Business")) {
            monkeyBusiness = parrot + monkey;
            numOfEach[1] = monkeyBusiness;
            startingPoint = 1;
        } else {
            numOfEach[1] = parrot;
        }

        numOfEach[2] = sword;
        numOfEach[3] = skull;
        numOfEach[4] = coin;
        numOfEach[5] = diamond;

        //CALCULATE SCORE
        int fullChestBonus = 0;

        //ACCOUNT FOR IDENTICAL DIE
        for (int i = startingPoint; i < 6; i++) {
            if (numOfEach[i] == 3 && i != 3) {
                score+=100;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 4 && i != 3) {
                score+=200;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 5 && i != 3) {
                score+=500;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 6 && i != 3) {
                score+=1000;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 7 && i != 3) {
                score+=2000;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 8 && i != 3) {
                score+=4000;
                fullChestBonus += numOfEach[i];
            }
        }

        if((coin == 1 || coin == 2) && (!fortuneCard.getName().equalsIgnoreCase("Gold"))) {
            fullChestBonus += coin;
        }

        if((diamond == 1 || diamond == 2) && (!fortuneCard.getName().equalsIgnoreCase("Diamond"))) {
            fullChestBonus += diamond;
        }

        //CHECK FOR CHEST BONUS
        if (fullChestBonus == 8 && skull == 0) {
            score+=500;
        }

        //ACCOUNT FOR COINS
        score += (100 * coin) + (100 * diamond);

        //CARD CHECK
        score = applyFortuneCardLogic(fortuneCard, score);

        return score;
    }

    public int scoreDie(String[] dieRoll, PiratesFortuneCard fortuneCard) {

        int numSkulls = checkSkullCount(dieRoll, fortuneCard);

        int numSwords = checkSwordCount(dieRoll);

        //CHECK FOR SEA BATTLE
        //FAILED 2 SWORDS BY DEATH
        if(fortuneCard.getName().equalsIgnoreCase("Sea Battle (2 Swords)") && (numSwords < 2) && (numSkulls >= 3)) {
            return -300;
        }

        if(fortuneCard.getName().equalsIgnoreCase("Sea Battle (3 Swords)") && (numSwords < 3) && (numSkulls >= 3)) {
            return -500;
        }

        if(fortuneCard.getName().equalsIgnoreCase("Sea Battle (4 Swords)") && (numSwords < 4) && (numSkulls >= 3)) {
            return -1000;
        }

        if ((numSkulls == 3 || numSkulls > 4) && !fortuneCard.getName().equalsIgnoreCase("Treasure Chest")) {
            return 0;
        }

        int monkey = 0;
        int parrot = 0;
        int sword = 0;
        int skull = 0;
        int coin = 0;
        int diamond = 0;

        int numOfEach[] = new int[6];

        int numOfDie = dieRoll.length;

        for(int i = 0; i < numOfDie; i++) {
            if (dieRoll[i].equalsIgnoreCase("Monkey")) {
                monkey++;
            } else if (dieRoll[i].equalsIgnoreCase("Parrot")) {
                parrot++;
            } else if (dieRoll[i].equalsIgnoreCase("Sword")) {
                sword++;
            } else if (dieRoll[i].equalsIgnoreCase("Skull")) {
                skull++;
            } else if (dieRoll[i].equalsIgnoreCase("Coin")) {
                coin++;
            } else if (dieRoll[i].equalsIgnoreCase("Diamond")) {
                diamond++;
            }
        }

        int score = 0;
        int monkeyBusiness = 0;

        if(fortuneCard.getName().equalsIgnoreCase("Gold") && coin == 8) {
            score += 100;
        } else if(fortuneCard.getName().equalsIgnoreCase("Gold") && coin != 8) {
            coin++;
        } else if(fortuneCard.getName().equalsIgnoreCase("Diamond") && diamond == 8) {
            score += 100;
        } else if(fortuneCard.getName().equalsIgnoreCase("Diamond") && diamond != 8) {
            diamond++;
        }

        //ACCOUNT FOR FORTUNE CARDS

        numOfEach[0] = monkey; //WON'T BE ACCOUNT FOR IN SCORE CALCULATION IF MONKEY BUSINESS CARD

        //ADJUSTMENT FOR MONKEY BUSINESS CARD
        int startingPoint = 0;
        if (fortuneCard.getName().equalsIgnoreCase("Monkey Business")) {
            monkeyBusiness = parrot + monkey;
            numOfEach[1] = monkeyBusiness;
            startingPoint = 1;
        } else {
            numOfEach[1] = parrot;
        }

        numOfEach[2] = sword;
        numOfEach[3] = skull;
        numOfEach[4] = coin;
        numOfEach[5] = diamond;

        //CALCULATE SCORE
        int fullChestBonus = 0;

        //ACCOUNT FOR IDENTICAL DIE
        for (int i = startingPoint; i < 6; i++) {
            if (numOfEach[i] == 3 && i != 3) {
                score+=100;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 4 && i != 3) {
                score+=200;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 5 && i != 3) {
                score+=500;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 6 && i != 3) {
                score+=1000;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 7 && i != 3) {
                score+=2000;
                fullChestBonus += numOfEach[i];
            } else if (numOfEach[i] == 8 && i != 3) {
                score+=4000;
                fullChestBonus += numOfEach[i];
            }
        }

        if((coin == 1 || coin == 2) && (!fortuneCard.getName().equalsIgnoreCase("Gold"))) {
            fullChestBonus += coin;
        }

        if((diamond == 1 || diamond == 2) && (!fortuneCard.getName().equalsIgnoreCase("Diamond"))) {
            fullChestBonus += diamond;
        }

        //CHECK FOR CHEST BONUS
        if (fullChestBonus == 8 && skull == 0) {
            score+=500;
        }

        //ACCOUNT FOR COINS
        score += (100 * coin) + (100 * diamond);

        //CARD CHECK
        score = applyFortuneCardLogic(fortuneCard, score);

        return score;
    }

    public int applyFortuneCardLogic(PiratesFortuneCard fortuneCard, int score) {
        if (fortuneCard.getName().equalsIgnoreCase("Captain")) {
            return fortuneCard.applyCardRule(score);
        }

        return score;
    }



    public boolean isPlayerDead(int skullCount) {

        boolean isDead = false;

        if (skullCount >= 3) {
            isDead = true;
        }

        return isDead;
    }


    public int checkSkullCount(String[] dieRoll) {

        int skull = 0;
        int numOfDie = dieRoll.length;

        for(int i =0; i < numOfDie; i++) {
            if (dieRoll[i].equalsIgnoreCase("Skull")) {
                skull++;
            }
        }

        return skull;
    }

    public int checkSkullCount(String[] dieRoll, PiratesFortuneCard fortuneCard) {

        int skull = 0;

        int numOfDie = dieRoll.length;


        //REUSED - MAKE IT A FUNCTION
        for(int i =0; i < numOfDie; i++) {
            if (dieRoll[i].equalsIgnoreCase("Skull")) {
                skull++;
            }
        }

        //IF SKULL CARD
        if (fortuneCard.getName().equalsIgnoreCase("One Skull")) {
            skull++;
        } else if (fortuneCard.getName().equalsIgnoreCase("Two Skulls")) {
            skull+=2;
        }

        return skull;

    }

    public int checkSwordCount(String[] dieRoll) {

        int sword = 0;
        int numOfDie = dieRoll.length;

        for(int i =0; i < numOfDie; i++) {
            if (dieRoll[i].equalsIgnoreCase("Sword")) {
                sword++;
            }
        }

        return sword;
    }

    //ADDED: DECK OF FORTUNE CARDS
    private ArrayList<PiratesFortuneCard> deck = new ArrayList<PiratesFortuneCard>();

    public ArrayList<PiratesFortuneCard> createFortuneDeck() {

        //CARDS
        PiratesFortuneCardGenerator fortuneCard = new FortuneGenerator();

        //TEST


        for (int i = 0; i < 4; i++) {

            PiratesFortuneCard treasure = fortuneCard.createFortuneCard(0);
            deck.add(treasure);

            PiratesFortuneCard captain = fortuneCard.createFortuneCard(1);
            deck.add(captain);

            PiratesFortuneCard sorceress = fortuneCard.createFortuneCard(2);
            deck.add(sorceress);

            PiratesFortuneCard gold = fortuneCard.createFortuneCard(4);
            deck.add(gold);

            PiratesFortuneCard diamond = fortuneCard.createFortuneCard(5);
            deck.add(diamond);

            PiratesFortuneCard monkey = fortuneCard.createFortuneCard(6);
            deck.add(monkey);
        }

        Collections.shuffle(deck);

        return deck;

    }
    public PiratesFortuneCard drawFortuneCard(ArrayList<PiratesFortuneCard> deck) {

        PiratesFortuneCard drawnCard = null;

        if (deck.size() > 0) {
            drawnCard = deck.get(0);
            deck.remove(0);
        } else if (deck.size() == 0) {
            System.out.println("DECK IS EMPTY!");
        }

        return drawnCard;
    }

    //UNTESTABLE FROM EXCEL SHEET

    public PiratesPlayer getWinner(PiratesPlayer[] pl) {
        PiratesPlayer temp = pl[1];
        if (pl[0].getScore() >= pl[1].getScore())
            temp = pl[0];
        if (pl[2].getScore() >= temp.getScore())
            return pl[2];
        return temp;
    }

    //PRINTING FUNCTIONS FOR PLAYER MENU
    //NOT TESTABLE

    public void printFortuneCard(PiratesFortuneCard card, int deckSize) {
        System.out.println("Fortune Card (" + deckSize + " remaining): ");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------|");
        card.printCard();
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------|");
    }

    public void printDieRoll(String[] dieRoll) {
        System.out.println("Dice rolls: ");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("| " + dieRoll[0] + " |  | " + dieRoll[1] + " |  | " + dieRoll[2] + " |  | " + dieRoll[3] + " |  | "
                + dieRoll[4] + " |  | " + dieRoll[5] + " |  | " + dieRoll[6] + " |  | " + dieRoll[7] + " | ");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------|");

    }

    public int printCurrentScore(String[] dieRoll, PiratesFortuneCard fortuneCard) {

        int currentScore = 0;

        int[] scoreSheet = new int[1];

        currentScore = scoreDie(scoreSheet, dieRoll, fortuneCard);

        return currentScore;
    }

    public String printCurrentScoreDice(String[] dieRoll, PiratesFortuneCard fortuneCard) {

        String currentScoreDice = "";

        int monkey = 0;
        int parrot = 0;
        int sword = 0;
        int skull = 0;
        int coin = 0;
        int diamond = 0;

        int numOfDie = dieRoll.length;


        //REUSED - MAKE IT A FUNCTION
        for(int i =0; i < numOfDie; i++) {
            if (dieRoll[i].equalsIgnoreCase("Monkey")) {
                monkey++;
            } else if (dieRoll[i].equalsIgnoreCase("Parrot")) {
                parrot++;
            } else if (dieRoll[i].equalsIgnoreCase("Sword")) {
                sword++;
            } else if (dieRoll[i].equalsIgnoreCase("Skull")) {
                skull++;
            } else if (dieRoll[i].equalsIgnoreCase("Coin")) {
                coin++;
            } else if (dieRoll[i].equalsIgnoreCase("Diamond")) {
                diamond++;
            }
        }

        int monkeyBusiness = 0;

        //ACCOUNT FOR GOLD AND DIAMOND CARDS
        if(fortuneCard.getName().equalsIgnoreCase("Gold")) {
            coin++;
        } else if (fortuneCard.getName().equalsIgnoreCase("Diamond")) {
            diamond++;
        } else if (fortuneCard.getName().equalsIgnoreCase("Monkey Business")) {
            monkeyBusiness = monkey + parrot;
            if (monkeyBusiness >= 3) {
                currentScoreDice += "+" + monkeyBusiness + " monkey business (" + monkey + " monkey(s) & " + parrot + " parrot(s)) \n";
            }
        } else {
            //IF THERE ARE 3 PAIR OR HIGHER, SHOW IT
            if(monkey >= 3) {
                currentScoreDice += "+" + monkey + " monkeys \n";
            }

            if(parrot >= 3) {
                currentScoreDice += "+" + parrot + " parrots \n";
            }

        }

        //IF THERE ARE 3 PAIR OR HIGHER, SHOW IT
        if(sword >= 3) {
            currentScoreDice += "+" + sword + " swords \n";
        }

        if(coin >= 3) {
            currentScoreDice += "+" + coin + " coins \n";
        }

        if(diamond >= 3) {
            currentScoreDice += "+" + diamond + " diamonds \n";
        }

        //BONUS POINTS FOR COIN/DIAMONDS
        if (coin >= 1) {
            currentScoreDice += "+" + (coin * 100) + " for " + coin + " coin(s) \n";
        }

        if (diamond >= 1) {
            currentScoreDice += "+" + (diamond * 100) + " for " + diamond + " diamond(s) \n";
        }


        return currentScoreDice;
    }

    public void printScoreSheet(PiratesPlayer p) {
        String[] sc = new String[p.getScoreSheet().length];
        for (int i = 0; i < p.getScoreSheet().length; i++) {
            if (p.getScoreSheet()[i] == -1) {
                sc[i] = "-";
            } else {
                sc[i] = "" + p.getScoreSheet()[i];
            }
        }
        System.out.println(
                "|---------------------------------------------------------------------------------------------------------------------------------------|");

        System.out.println("| Scores for player : " + p.name + "\t \t \t \t \t \t \t \t \t \t \t \t \t \t|");
        System.out.println("| Total Score : " + p.getScore() + "\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t|");
        System.out.println(
                "|---------------------------------------------------------------------------------------------------------------------------------------|");

    }

    public void printTreasureChest(String[] dieRoll, String[] held, PiratesFortuneCard card) {

        for (String s : held) {
            System.out.println("+ " + dieRoll[Integer.parseInt(s)-1] + " treasure item(s).");
        }

        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------------|");
    }
}
