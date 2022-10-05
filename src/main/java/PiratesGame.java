
import Card.PiratesFortuneCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

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

        if(fortuneCard.getName().equalsIgnoreCase("Gold")) {
            coin++;
        }

        numOfEach[0] = monkey;
        numOfEach[1] = parrot;
        numOfEach[2] = sword;
        numOfEach[3] = skull;
        numOfEach[4] = coin;
        numOfEach[5] = diamond;

        //CALCULATE SCORE
        int score = 0;
        int fullChestBonus = 0;

        //ACCOUNT FOR IDENTICAL DIE
        for (int i = 0; i < 6; i++) {
            if (numOfEach[i] == 3) {
                score+=100;
            } else if (numOfEach[i] == 4) {
                score+=200;
            } else if (numOfEach[i] == 5) {
                score+=500;
            } else if (numOfEach[i] == 6) {
                score+=1000;
            } else if (numOfEach[i] == 7) {
                score+=2000;
            } else if (numOfEach[i] == 8) {
                score+=4000;
            }
        }

        //ACCOUNT FOR COINS
        score += (100 * coin);

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

}
