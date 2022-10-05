import Card.FortuneGenerator;
import Card.PiratesFortuneCard;
import Card.PiratesFortuneCardGenerator;
import org.junit.jupiter.api.*;

class PiratesGameTest {

    PiratesGame piratesGame;
    PiratesFortuneCardGenerator fortuneCard = new FortuneGenerator();
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

        String[] dice = piratesGame.rollDice(); //rollDice() ALREADY EXISTED

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
        dice = piratesGame.reRollNotHeld(dice, dieToKeep); //reRollNotHeld() ALREADY EXISTED

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

    @Test
    @DisplayName("line 48: roll 1 skull, 4 parrots, 3 swords, hold parrots, " +
            "reroll swords, get 1 skull 2 monkeys, " +
            "reroll 2 monkeys, get 1 skull 1 monkey and die")
    void line48() {

        String[] dice = piratesGame.rollDice(); //rollDice() ALREADY EXISTED

        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep); //reRollNotHeld() ALREADY EXISTED

        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Skull";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "6"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Monkey";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));
    }

    //-----------------------------------------------------------------------------------------------------
    //PLAYER POINT/SCORE TESTING

    @Test
    @DisplayName("line 50: roll 1 skull, 2 parrots, 3 swords, 2 coins, " +
            "reroll parrots get 2 coins" +
            "reroll 3 swords, get 3 coins (SC 4000 for seq of 8 (with FC) + 8x100=800 = 4800) ")
    void line50() {

        PiratesFortuneCard card = fortuneCard.createFortuneCard(4);

        String[] dice = piratesGame.rollDice();

        //CARD == COIN
        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));


        dieToKeep = new String[]{"1", "4", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        //CARD == COIN
        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(1100, piratesGame.scoreDie(dice, card));

        dieToKeep = new String[]{"1", "2", "3", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        //CARD == COIN
        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Coin";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(4800, piratesGame.scoreDie(dice, card));
    }

}
