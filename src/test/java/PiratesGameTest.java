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
    @DisplayName("line 45: die with 3 skulls 5 swords on first roll: player gets a score of 0")
    void line45() {

        PiratesFortuneCard card = fortuneCard.createFortuneCard(4);

        //1. ROLL DICE
        String[] dice = piratesGame.rollDice();

        //2. HARD CODE DICE
        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        //ASSERT DEAD IF 3 SKULLS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(0, piratesGame.scoreDie(dice, card));

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
    @DisplayName("line 52: score first roll with 2 (monkeys/parrot/diamonds/coins) and FC is captain (SC 800)")
    void line52() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);

        //HARD CODE CAPTAIN CARD WHICH IS ID 1 IN FORTUNE GENERATOR
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Diamond";
        dice[5] = "Diamond";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(800, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 53: roll 2 (monkeys/skulls/swords/parrots), reroll parrots and get 1 sword & 1 monkey (SC 300 since FC is coin)")
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
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(300, piratesGame.scoreDie(dice, card));
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
    @DisplayName("line 55: roll 3 diamonds, 2 skulls, 1 monkey, 1 sword, 1 parrot, score (diamonds = 100 + 300 points)   (SC 500)")
    void line55() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Diamond";
        dice[3] = "Diamond";
        dice[4] = "Diamond";
        dice[5] = "Monkey";
        dice[6] = "Sword";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));
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

    @Test
    @DisplayName("line 59: score set of 3 coins+ FC and set of 4 swords correctly over several rolls (SC = 200+400+200 = 800)")
    void line59() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Diamond";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));

        dieToKeep = new String[]{"1", "2", "3", "4", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Diamond";
        dice[4] = "Sword";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(600, piratesGame.scoreDie(dice, card));

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(800, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 60: same as previous row but with captain fortune card  (SC = (100 + + 300 + 200)*2 = 1200)")
    void line60() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Diamond";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(600, piratesGame.scoreDie(dice, card));

        dieToKeep = new String[]{"1", "2", "3", "4", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Diamond";
        dice[4] = "Sword";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(800, piratesGame.scoreDie(dice, card));

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(1200, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 61: score set of 5 swords over 3 rolls (SC 600)")
    void line61() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Diamond";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));

        dieToKeep = new String[]{"1", "2", "3",};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));

        dieToKeep = new String[]{"1", "2", "3", "4"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(600, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 62: score set of 6 monkeys on first roll (SC 1100)")
    void line62() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Monkey";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(1100, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 63: score set of 7 parrots on first roll (SC 2100)")
    void line63() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(2100, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 64: score set of 8 coins on first roll (SC 5400)  seq of 8 + 9 coins(FC is coin) +  full chest")
    void line64() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Coin";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Coin";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(5400, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 65: score set of 8 coins on first roll and FC is diamond (SC 5400)")
    void line65() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(5); //DIAMOND

        String[] dice = piratesGame.rollDice();

        dice[0] = "Coin";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Coin";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(5400, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 66: score set of 8 swords on first roll and FC is captain (SC 4500x2 = 9000) since full chest")
    void line66() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(9000, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 67: score set of 8 monkeys over several rolls (SC 4600 because of FC is coin and full chest)")
    void line67() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep); //reRollNotHeld() ALREADY EXISTED

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Coin";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Monkey";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(4600, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 68: score a set of 2 diamonds over 2 rolls with FC is diamond (SC 400)")
    void line68() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(5); //DIAMOND

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"2", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Diamond";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(400, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 69: score a set of 3 diamonds over 2 rolls (SC 500)")
    void line69() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Diamond";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"2", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Diamond";
        dice[2] = "Sword";
        dice[3] = "Diamond";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 70: score a set of 3 coins over 2 rolls  (SC 600)")
    void line70() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"2", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Coin";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Skull";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(600, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 71: score a set of 3 coins over 2 rolls  with FC is diamond (SC 500)")
    void line71() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(5); //DIAMOND

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"2", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Coin";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Skull";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 72: score a set of 4 monkeys and a set of 3 coins (including the COIN fortune card) (SC 600)")
    void line72() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Skull";
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(600, piratesGame.scoreDie(dice, card));
    }


}
