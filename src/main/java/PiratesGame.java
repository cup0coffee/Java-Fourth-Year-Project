
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

    public int scoreDie(String[] dieRoll, PiratesFortuneCard fortuneCard) {

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

    public int applyFortuneCardLogic(PiratesFortuneCard fortuneCard, int score) {
        if (fortuneCard.getName().equalsIgnoreCase("Captain")) {
            return fortuneCard.applyCardRule(score);
        }

        return score;
    }



    public boolean isPlayerDead(int skullCount) {

        boolean isDead = false;

        if (skullCount >= 3 && skullCount != 4) {
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

    //ADDED: DECK OF FORTUNE CARDS
    private ArrayList<PiratesFortuneCard> deck = new ArrayList<PiratesFortuneCard>();

    public ArrayList<PiratesFortuneCard> createFortuneDeck() {

        //CARDS
        PiratesFortuneCardGenerator fortuneCard = new FortuneGenerator();

        for (int i = 0; i < 4; i++) {
            PiratesFortuneCard gold = fortuneCard.createFortuneCard(4);
            deck.add(gold);
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

}
