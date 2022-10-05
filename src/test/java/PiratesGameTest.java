import org.junit.jupiter.api.*;

class PiratesGameTest {

    PiratesGame piratesGame;
    String[] dice;
    String[] dieToKeep;

    @BeforeEach
    void init() {
        piratesGame = new PiratesGame();
        dice = new String[8];
    }

    //-----------------------------------------------------------------------------------------------------
    //PLAYER DEATH UPON ROLLING DICE TESTING

    @Test
    @DisplayName("line 45: die with 3 skulls on first roll")
    void line45() {

        //1. ROLL DICE
        String[] dice = piratesGame.rollDice();

        //2. HARD CODE DICE
        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Monkey";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        //ASSERT DEAD IF 3 SKULLS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

    }

    @Test
    @DisplayName("line 46: roll 1 skull, 4 parrots, 3 swords, hold parrots, reroll 3 swords, get 2 skulls 1 sword  die")
    void line46() {

        //1. ROLL DICE
        String[] dice = piratesGame.rollDice();

        //2. HARD CODE DICE
        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";


        //ASSERT THEY ARE NOT DEAD UNLESS THEY HAVE 3 OR MORE SKULLS (AND NOT FOUR)
        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        //3. REROLL DICE THAT WERE SPECIFIED TO REROLL
        dieToKeep = new String[]{"1", "2", "3", "4", "5"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        //4. HARDCODE VALUES AGAIN
        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Skull";
        dice[7] = "Skull";

        //ASSERT DEAD IF 3 SKULLS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));
    }

    @Test
    @DisplayName("line 47: roll 2 skulls, 4 parrots, 2 swords, " +
            "hold parrots, reroll swords, get 1 skull 1 sword  die")
    void line47() {

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "6"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Sword";
        dice[7] = "Skull";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));
    }

}
