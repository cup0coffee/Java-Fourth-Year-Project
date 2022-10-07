import Card.FortuneGenerator;
import Card.PiratesFortuneCard;
import Card.PiratesFortuneCardGenerator;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

class PiratesGameTest {

    PiratesGame piratesGame;
    PiratesFortuneCardGenerator fortuneCard = new FortuneGenerator();

    ArrayList<PiratesFortuneCard> deck;
    String[] dice;
    String[] dieToKeep;

    @BeforeEach
    void init() {
        piratesGame = new PiratesGame();
        dice = new String[8];
        deck = piratesGame.createFortuneDeck();
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

    @Test
    @DisplayName("line 52: score first roll with nothing but 2 diamonds and 2 coins and FC is captain (SC 800)")
    void line52() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);

        //HARD CODE CAPTAIN CARD WHICH IS ID 1 IN FORTUNE GENERATOR
        card = fortuneCard.createFortuneCard(1);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Sword";
        dice[4] = "Diamond";
        dice[5] = "Diamond";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(800, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 53: get set of 2 monkeys on first roll, get 3rd monkey on 2nd roll (SC 200 since FC is coin)")
    void line53() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Parrot";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(200, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 54: score 2 sets of 3 (monkey, swords) in RTS on first roll (SC 300)")
    void line54() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(300, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 55: score 2 sets of 3 (monkey, parrots) in RTS using 2 rolls (SC 300)")
    void line55() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(200, piratesGame.scoreDie(dice, card));

        //REROLL
        dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(300, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 56: score a set of 3 diamonds correctly (i.e., 400 points)   (SC 500)")
    void line56() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Parrot";
        dice[5] = "Diamond";
        dice[6] = "Diamond";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 57: score a set of 4 coins correctly (i.e., 200 + 400 points) with FC is a diamond (SC 700)")
    void line57() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(5); //DIAMOND

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(700, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 58: score set of 3 swords and set of 4 parrots correctly on first roll (SC 400 because of FC)")
    void line58() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(400, piratesGame.scoreDie(dice, card));
    }




}
