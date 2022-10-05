
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
