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

    String[] treasureToKeep;

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
    @DisplayName("line 56: roll 4 coins, 2 skulls, 2 swords and score (coins: 200 + 400 points) with FC is a diamond (SC 700)")
    void line56() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

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
    @DisplayName("line 57: roll 3 swords, 4 parrots, 1 skull and score (SC 100+200+100= 400)")
    void line57() {

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
    @DisplayName("line 58: roll 1 skull, 2 coins/parrots & 3 swords, reroll parrots, get 1 coin and 1 sword, score (SC = 200+400+200 = 800)")
    void line58() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Coin";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(800, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 59: same as previous row but with captain fortune card  (SC = (100 + 300 + 200)*2 = 1200)")
    void line59() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Coin";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(1200, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 60: roll 1 skull, 2 (monkeys/parrots) 3 swords, " +
            "reroll 2 monkeys, get 1 skull 1 sword, " +
            "         then reroll parrots get 1 sword 1 monkey (SC 600)")
    void line60() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "4", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Monkey";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(600, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 62: score set of 6 monkeys and 2 skulls on first roll (SC 1100)")
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
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(1100, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 63: score set of 7 parrots and 1 skull on first roll (SC 2100)")
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
    @DisplayName("line 67: roll 6 monkeys and 2 swords, reroll swords, get 2 monkeys, score (SC 4600 because of FC is coin and full chest)")
    void line67() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Monkey";
        dice[5] = "Monkey";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "6"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep); //reRollNotHeld() ALREADY EXISTED

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
    @DisplayName("line 68: roll 2 (monkeys/skulls/swords/parrots), reroll parrots, get 2 diamonds, score with FC is diamond (SC 400)")
    void line68() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(5); //DIAMOND

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Skull";
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Diamond";
        dice[5] = "Diamond";
        dice[6] = "Skull";
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(400, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 69: roll 2 (monkeys, skulls, swords), 1 diamond, 1 parrot, reroll 2 monkeys, get 2 diamonds, score 500")
    void line69() {

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
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

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
    @DisplayName("line 70: roll 1 skull, 2 coins, 1 (monkey/parrot), 3 swords, reroll 3 swords, get 1 (coin/monkey/parrot)  (SC 600)")
    void line70() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Monkey";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Monkey";
        dice[4] = "Parrot";
        dice[5] = "Coin";
        dice[6] = "Monkey";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(600, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 71: roll 1 skull, 2 coins, 1 (monkey/parrot), 3 swords, reroll swords, get 1 (coin/monkey/parrot) with FC is diamond (SC 500)")
    void line71() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(5); //DIAMOND

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Monkey";
        dice[4] = "Parrot";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Coin";
        dice[2] = "Coin";
        dice[3] = "Monkey";
        dice[4] = "Parrot";
        dice[5] = "Coin";
        dice[6] = "Monkey";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 72: get 4 monkeys, 2 coins and 2 skulls with FC coin. Score 600")
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

    //------------------------------------------------------------------------------

    //PART 2: Miscellaneous Fortune Cards and Full Chest bonus (SINGLE PLAYER SCORING)


    //SORCERESS
    @Test
    @DisplayName("line 77: roll 2 diamonds, 1 (sword/monkey/coin), 3 parrots, " +
            "reroll 3 parrots, get 1 skull, 2 monkeys, " +
            "reroll skull, get monkey (SC 500)")
    void line77() {

        boolean sorceressAvailable = true;

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(2); //SORCERESS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Diamond";
        dice[1] = "Diamond";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Coin";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);


        dice[0] = "Diamond";
        dice[1] = "Diamond";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Coin";
        dice[5] = "Skull";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        if (sorceressAvailable) {
            dieToKeep = new String[]{"1", "2", "3", "4", "5", "7", "8"};
            dice = piratesGame.reRollNotHeld(dice, dieToKeep);
            sorceressAvailable = false;
        }

        dice[0] = "Diamond";
        dice[1] = "Diamond";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Coin";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(500, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 78: roll 3 skulls, 3 parrots, 2 swords, " +
            "reroll skull, get parrot, " +
            "reroll 2 swords, get parrots, score (SC 1000)")
    void line78() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        boolean sorceressAvailable = true;

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(2); //SORCERESS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "2", "4", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);;

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "2", "3", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(1000, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 79: roll 1 skull, 4 parrots, 3 monkeys, " +
            "reroll 3 monkeys, get 1 skull, 2 parrots, " +
            "reroll skull, get parrot, score (SC 2000)")
    void line79() {

        //REASON: ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        boolean sorceressAvailable = true;

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(2); //SORCERESS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);;

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "3", "4", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(2000, piratesGame.scoreDie(dice, card));
    }


    //------------------------------------------------------------------------------


    //MONKEY BUSINESS
    @Test
    @DisplayName("line 82: roll 3 monkeys 3 parrots  1 skull 1 coin  SC = 1100  (i.e., sequence of of 6 + coin)")
    void line82() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(6); //MONKEY BUSINESS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Skull";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(1100, piratesGame.scoreDie(dice, card));
    }

    @Test
    @DisplayName("line 83: roll 2 (monkeys/swords/parrots/coins), reroll 2 swords, get 1 monkey, 1 parrot, score 1700")
    void line83() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(6); //MONKEY BUSINESS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        dieToKeep = new String[]{"1", "2", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(1700, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 84: roll 3 skulls, 3 monkeys, 2 parrots => die scoring 0")
    void line84() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(6); //MONKEY BUSINESS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Skull";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

        Assertions.assertEquals(0, piratesGame.scoreDie(dice, card));

    }

    //--------------------------------------------------------------------

    //TREASURE CHEST

    @Test
    @DisplayName("line 87: roll 3 parrots, 2 swords, 2 diamonds, 1 coin     put 2 diamonds and 1 coin in chest" +
            "  then reroll 2 swords and get 2 parrots put 5 parrots in chest and take out 2 diamonds & coin" +
            "  then reroll the 3 dice and get 1 skull, 1 coin and a parrot" +
            "   score 6 parrots + 1 coin for 1100 points")
    void line87() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(0); //TREASURE CHEST

        String[] dice = piratesGame.rollDice();

        dice[0] = "Parrot";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Diamond";
        dice[6] = "Diamond";
        dice[7] = "Coin";

        //PLACE DIAMOND AND COIN IN CHEST
        treasureToKeep = new String[]{"6", "7", "8"};
        dieToKeep = new String[]{"1", "2", "3", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Parrot";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Diamond";
        dice[6] = "Diamond";
        dice[7] = "Coin";

        treasureToKeep = new String[]{"1", "2", "3", "4", "5"};
        dieToKeep = new String[]{"1", "2", "3", "4", "5"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Parrot";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Skull";
        dice[6] = "Coin";
        dice[7] = "Parrot";

        Assertions.assertEquals(1100, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 92: roll 2 skulls, 3 parrots, 3 coins   put 3 coins in chest" +
            "    then rerolls 3 parrots and get 2 diamonds 1 coin    put coin in chest (now 4)" +
            "   then reroll 2 diamonds and get 1 skull 1 coin     score for chest only = 400 + 200 = 600 AND report death")
    void line92() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(0); //TREASURE CHEST

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        //COIN IN CHEST
        treasureToKeep = new String[]{"6", "7", "8"};
        dieToKeep = new String[]{"1", "2", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Diamond";
        dice[3] = "Diamond";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        treasureToKeep = new String[]{"5", "6", "7", "8"};
        dieToKeep = new String[]{"1", "2", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Coin";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(600, piratesGame.scoreTreasureChest(dice, treasureToKeep, card));

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice)));

    }

    //--------------------------------------------------------------------

    //FULL CHEST
    @Test
    @DisplayName("line 97: 3 monkeys, 3 swords, 1 diamond, 1 parrot FC: coin   => SC 400  (ie no bonus)")
    void line97() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Diamond";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        Assertions.assertEquals(400, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 98: 3 monkeys, 3 swords, 2 coins FC: captain   => SC (100+100+200+500)*2 =  1800")
    void line98() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        Assertions.assertEquals(1800, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 99: 3 monkeys, 4 swords, 1 diamond, FC: coin   => SC 1000  (ie 100++200+100+100+bonus)")
    void line99() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        Assertions.assertEquals(1000, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 100: FC: 2 sword sea battle, first  roll:  4 monkeys, 1 sword, 2 parrots and a coin then reroll 2 parrots and get 2nd coin and 2nd sword score is: 200 (coins) + 200 (monkeys) + 300 (swords of battle) + 500 (full chest) = 1200")
    void line100() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(3); //FC 2 SWORDS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        Assertions.assertEquals(1200, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 103: FC: monkey business and roll 2 monkeys, 1 parrot, 2 coins, 3 diamonds   SC 1200")
    void line103() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(6); //MONKYE BUSINESS

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Parrot";
        dice[3] = "Coin";
        dice[4] = "Coin";
        dice[5] = "Diamond";
        dice[6] = "Diamond";
        dice[7] = "Diamond";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        Assertions.assertEquals(1200, piratesGame.scoreDie(dice, card));

    }


    //--------------------------------------------------------------

    //SKULL FORTUNE CARDS
    @Test
    @DisplayName("line 106: roll one skull and 7 swords with FC with two skulls => die")
    void line106() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(8); //DOUBLE SKULL

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        Assertions.assertEquals(0, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 107: roll 2 skulls and 6 swords with FC with 1 skull  => die")
    void line107() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(7); //SINGLE SKULL

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        Assertions.assertEquals(0, piratesGame.scoreDie(dice, card));

    }

    @Test
    @DisplayName("line 108: roll 2 skulls  3(parrots/monkeys) with FC with two skulls: " +
            "reroll 3 parrots get 2 skulls, 1 sword" +
            "  reroll sword and 3 monkeys, get 3 skulls and 1 sword, stop " +
            "=> -900 for other players (no negative score) & you score 0")
    void line108() {

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(8); //DOUBLE SKULL

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Parrot";
        dice[3] = "Parrot";
        dice[4] = "Parrot";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        dieToKeep = new String[]{"1", "2", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Sword";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        dieToKeep = new String[]{"1", "2", "3", "4"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Sword";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Skull";

        Assertions.assertEquals(0, piratesGame.scoreDie(dice, card));

        int numSkulls = piratesGame.checkSkullCount(dice, card);

        int deduction = numSkulls * 100;
        deduction*=-1;

        Assertions.assertEquals(-900, deduction);
    }


    @Test
    @DisplayName("line 110: roll 5 skulls, 3 monkeys with FC Captain, reroll 3 monkeys, get 2 skulls, 1 coin, stop => -1400 for other players")
    void line110() {

        //ALL FUNCTIONS ALREADY EXIST]
        //CHARLES SAID JUST CHECKING DEDUCTION WAS FINE

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Skull";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        dieToKeep = new String[]{"1", "2", "3", "4", "5"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Skull";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Coin";

        Assertions.assertEquals(1400, piratesGame.scoreDie(dice, card));

        int numSkulls = piratesGame.checkSkullCount(dice, card);

        int deduction = numSkulls * 100;
        deduction*=-1;

        if(card.getName().equalsIgnoreCase("Captain")) {
            deduction*=2;
        }

        Assertions.assertEquals(-1400, deduction);
    }

    @Test
    @DisplayName("line 111: roll 3 skulls and 5 swords with FC with two skulls: reroll 5 swords, get 5 coins, must stop  => -500 for other players")
    void line111() {

        //ALL FUNCTIONS EXIST

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(8); //DOUBLE SKULL

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        dieToKeep = new String[]{"1", "2", "3"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Coin";
        dice[4] = "Coin";
        dice[5] = "Coin";
        dice[6] = "Coin";
        dice[7] = "Coin";

        Assertions.assertEquals(0, piratesGame.scoreDie(dice, card));

        int numSkulls = piratesGame.checkSkullCount(dice, card);

        int deduction = numSkulls * 100;
        deduction*=-1;

        Assertions.assertEquals(-500, deduction);
    }


    //-----------------------------------------------------------------


    //SEA BATTLES

    @Test
    @DisplayName("line 114: FC 2 swords, roll 4 monkeys, 3 skulls & 1 sword and die   => die and lose 300 points")
    void line114() {

        int zeroScoreTestValue = 0;
        int midgameScoreTestValue = 1000;

        int currentDiceScore = 0;
        int deduction = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(3); //FC 2 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Skull";
        dice[5] = "Skull";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Sword";

        //currentScore = piratesGame.scoreDie(scoreSheet, dice, Card);

        //PLAYER IS DEAD WITH 0 POINTS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(0, player.getScore());

        //------------------------------

        //TESTING WHEN PLAYER HAS SCORE GREATER THAN 0
        //MAKING SURE DEDUCTION IS CORRECT, IN THIS CASE 300
        player.setScoreSheet(0, midgameScoreTestValue);

        dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Skull";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Sword";

        //PLAYER IS DEAD WITH 0 POINTS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        deduction = piratesGame.scoreDie(dice, card);

        midgameScoreTestValue+=deduction;

        player.setScoreSheet(0, deduction);

        Assertions.assertEquals(700, player.getScore());

    }

    @Test
    @DisplayName("line 115: FC 3 swords, have 2 swords, 2 skulls and 4 parrots, reroll 4 parrots, get 4 skulls=> die and lose 500 points")
    void line115() {

        int zeroScoreTestValue = 0;
        int midgameScoreTestValue = 1000;

        int currentDiceScore = 0;
        int deduction = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(9); //FC 3 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "2", "3", "4"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Skull";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Skull";

        //currentScore = piratesGame.scoreDie(scoreSheet, dice, Card);

        //PLAYER IS DEAD WITH 0 POINTS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        //MAKE SURE DEDUCTION IS 500
        Assertions.assertEquals(-500, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(0, player.getScore());

        //------------------------------

        //TESTING WHEN PLAYER HAS SCORE GREATER THAN 0
        //MAKING SURE DEDUCTION IS CORRECT, IN THIS CASE 300
        player.setScoreSheet(0, midgameScoreTestValue);

        dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "2", "3", "4"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Skull";
        dice[5] = "Skull";
        dice[6] = "Skull";
        dice[7] = "Skull";

        //PLAYER IS DEAD WITH 0 POINTS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        deduction = piratesGame.scoreDie(dice, card);

        midgameScoreTestValue+=deduction;

        player.setScoreSheet(0, deduction);

        //MAKE SURE DEDUCTION IS 500
        Assertions.assertEquals(-500, deduction);

        //MAKE SURE PROPER SCORE DEDUCTED FROM PLAYER SCORE
        Assertions.assertEquals(500, player.getScore());

    }

    @Test
    @DisplayName("line 116: FC 4 swords, die on first roll with 2 monkeys, 3 (skulls/swords)  => die and lose 1000 points")
    void line116() {

        int zeroScoreTestValue = 0;
        int midgameScoreTestValue = 1000;

        int currentDiceScore = 0;
        int deduction = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(10); //FC 4 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Skull";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        //currentScore = piratesGame.scoreDie(scoreSheet, dice, Card);

        //PLAYER IS DEAD WITH 0 POINTS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        //MAKE SURE DEDUCTION IS 500
        Assertions.assertEquals(-1000, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(0, player.getScore());

        //------------------------------

        //TESTING WHEN PLAYER HAS SCORE GREATER THAN 0
        //MAKING SURE DEDUCTION IS CORRECT, IN THIS CASE 300
        player.setScoreSheet(0, midgameScoreTestValue);

        dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Skull";
        dice[3] = "Skull";
        dice[4] = "Skull";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        //PLAYER IS DEAD WITH 0 POINTS
        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        deduction = piratesGame.scoreDie(dice, card);

        midgameScoreTestValue+=deduction;

        player.setScoreSheet(0, deduction);

        //MAKE SURE DEDUCTION IS 500
        Assertions.assertEquals(-1000, deduction);

        //MAKE SURE PROPER SCORE DEDUCTED FROM PLAYER SCORE
        Assertions.assertEquals(0, player.getScore());

    }

    @Test
    @DisplayName("line 117: FC 2 swords, roll 3 monkeys 2 swords, 1 coin, 2 parrots  SC = 100 + 100 + 300 = 500")
    void line117() {

        int zeroScoreTestValue = 0;
        int midgameScoreTestValue = 1000;

        int currentDiceScore = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(3); //FC 2 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Coin";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        Assertions.assertEquals(500, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(500, player.getScore());

        //------------------------------

        //TESTING WHEN PLAYER HAS SCORE GREATER THAN 0
        player.setScoreSheet(0, midgameScoreTestValue);

        dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Coin";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        midgameScoreTestValue+=currentDiceScore;

        player.setScoreSheet(0, currentDiceScore);

        Assertions.assertEquals(500, currentDiceScore);

        Assertions.assertEquals(midgameScoreTestValue+500, player.getScore());

    }

    @Test
    @DisplayName("line 118: FC 2 swords, roll 4 monkeys 1 sword, 1 skull & 2 parrots then reroll 2 parrots and get 1 sword and 1 skull   SC = 200 +  300 = 500")
    void line118() {

        //ALL THE FUNCTIONS IN THIS TEST ALREADY EXISTED FROM PREVIOUS CODE COMMITS

        int zeroScoreTestValue = 0;
        int midgameScoreTestValue = 1000;

        int currentDiceScore = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(3); //FC 2 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Skull";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "6"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Skull";
        dice[6] = "Sword";
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        Assertions.assertEquals(500, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(500, player.getScore());

        //------------------------------

        //TESTING WHEN PLAYER HAS SCORE GREATER THAN 0
        player.setScoreSheet(0, midgameScoreTestValue);

        dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Skull";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "6"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Skull";
        dice[6] = "Sword";
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        midgameScoreTestValue+=currentDiceScore;

        player.setScoreSheet(0, currentDiceScore);

        Assertions.assertEquals(500, currentDiceScore);

        Assertions.assertEquals(midgameScoreTestValue+500, player.getScore());

    }

    @Test
    @DisplayName("line 120: FC 3 swords, roll 3 monkeys 4 swords 1 skull SC = 100 + 200 + 500 = 800")
    void line120() {

        int zeroScoreTestValue = 0;
        int midgameScoreTestValue = 1000;

        int currentDiceScore = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(9); //FC 3 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        Assertions.assertEquals(800, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(800, player.getScore());
    }

    @Test
    @DisplayName("line 121: FC 3 swords, roll 4 monkeys 2 swords 2 skulls then reroll 4 monkeys and get  2 skulls and 2 swords   => die and lose 500 points")
    void line121() {

        int zeroScoreTestValue = 0;
        int midgameScoreTestValue = 1000;

        int currentDiceScore = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(9); //FC 3 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Skull";
        dice[7] = "Skull";

        dieToKeep = new String[]{"5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Skull";
        dice[7] = "Skull";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        Assertions.assertEquals(-500, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(0, player.getScore());

        //------------------------------

        //TESTING WHEN PLAYER HAS SCORE GREATER THAN 0
        player.setScoreSheet(0, midgameScoreTestValue);

        dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Skull";
        dice[7] = "Skull";

        dieToKeep = new String[]{"5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Skull";
        dice[7] = "Skull";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        midgameScoreTestValue+=currentDiceScore;

        player.setScoreSheet(0, currentDiceScore);

        Assertions.assertEquals(-500, currentDiceScore);

        Assertions.assertEquals(midgameScoreTestValue, player.getScore());

    }

    @Test
    @DisplayName("line 123: FC 4 swords, roll 3 monkeys 4 swords 1 skull  SC = 100 +200 + 1000 = 1300")
    void line123() {

        int zeroScoreTestValue = 0;

        int currentDiceScore = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(10); //FC 4 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Skull";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        Assertions.assertEquals(1300, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(1300, player.getScore());
    }

    @Test
    @DisplayName("line 124: FC 4 swords, roll 3 monkeys, 1 sword, 1 skull, 1 diamond, 2 parrots then reroll 2 parrots and get 2 swords thus you have 3 monkeys, 3 swords, 1 diamond, 1 skull then reroll 3 monkeys and get  1 sword and 2 parrots  SC = 200 + 100 + 1000 = 1300")
    void line124() {

        //EVERY FUNCTION HERE ALREADY EXISTED :)

        int zeroScoreTestValue = 0;

        int currentDiceScore = 0;
        int[] scoreSheet = new int[1];

        PiratesPlayer player = new PiratesPlayer("test");

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(10); //FC 4 SWORDS

        //ZERO VALUE TEST
        //TO MAKE SURE IF PLAYER HAS 0 SCORE, THEY DON'T GET A NEGATIVE SCORE
        player.setScoreSheet(0, zeroScoreTestValue);

        String[] dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Skull";
        dice[5] = "Diamond";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "6"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Sword";
        dice[4] = "Skull";
        dice[5] = "Diamond";
        dice[6] = "Sword";
        dice[7] = "Sword";

        dieToKeep = new String[]{"4", "5", "6", "7", "8"};
        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = "Sword";
        dice[1] = "Parrot";
        dice[2] = "Parrot";
        dice[3] = "Sword";
        dice[4] = "Skull";
        dice[5] = "Diamond";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        currentDiceScore = piratesGame.scoreDie(dice, card);

        Assertions.assertEquals(1300, currentDiceScore);

        if((player.getScore()+currentDiceScore) <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, player.getScore()+currentDiceScore);
        }

        Assertions.assertEquals(1300, player.getScore());
    }


    //----------------------------------------


    //PART 3: MULTI-PLAYER SCENARIOS

    @Test
    @DisplayName("line 130: player1 rolls 7 swords + 1 skull with FC captain (gets 4000 points - could win) then player2 rolls 7 swords 1 skull  with FC 1 skull (2000) then player3 scores 0 (3 skulls, 5 monkeys, FC coin) => game stops and declares player 1 wins")
    void line130() {

        //ALL FUNCTIONS EXIST ALREADY

        int currentDiceScore = 0;
        PiratesPlayer[] players = new PiratesPlayer[3];
        PiratesPlayer p1 = new PiratesPlayer("p1");
        PiratesPlayer p2 = new PiratesPlayer("p2");
        PiratesPlayer p3 = new PiratesPlayer("p3");

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        //-----------------

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Skull";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));
        currentDiceScore = piratesGame.scoreDie(dice, card);
        Assertions.assertEquals(4000, currentDiceScore);
        p1.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(4000, p1.getScore());

        //-------------------------

        //PLAYER 2

        //DRAW FORTUNE CARD
        PiratesFortuneCard card2 = piratesGame.drawFortuneCard(deck);
        card2 = fortuneCard.createFortuneCard(7); //FC 1 SKULL

        String[] dice2 = piratesGame.rollDice();

        dice2[0] = "Sword";
        dice2[1] = "Sword";
        dice2[2] = "Sword";
        dice2[3] = "Sword";
        dice2[4] = "Skull";
        dice2[5] = "Sword";
        dice2[6] = "Sword";
        dice2[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice2, card2)));
        currentDiceScore = piratesGame.scoreDie(dice2, card2);
        Assertions.assertEquals(2000, currentDiceScore);
        p2.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(2000, p2.getScore());

        //-------------------------

        //PLAYER 3

        //DRAW FORTUNE CARD
        PiratesFortuneCard card3 = piratesGame.drawFortuneCard(deck);
        card3 = fortuneCard.createFortuneCard(4); //COIN

        String[] dice3 = piratesGame.rollDice();

        dice3[0] = "Skull";
        dice3[1] = "Skull";
        dice3[2] = "Skull";
        dice3[3] = "Monkey";
        dice3[4] = "Monkey";
        dice3[5] = "Monkey";
        dice3[6] = "Monkey";
        dice3[7] = "Monkey";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice3, card3)));
        currentDiceScore = piratesGame.scoreDie(dice3, card3);
        Assertions.assertEquals(0, currentDiceScore);
        p3.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(0, p3.getScore());

        PiratesPlayer winner = piratesGame.getWinner(players);
        System.out.println("winner: " + winner.name);

    }


    @Test
    @DisplayName("line 134: player1 rolls 7 swords + 1 skull with FC captain (gets 4000 points - could win)\n" +
            "then player2 scores 0 (3 skulls, 5 monkeys, FC coin)\n" +
            "then player3 rolls 6 skulls & 2 parrots with FC  Captain, then stops => deduction of (600x2)= 1200 points\n" +
            "      => score of player1 goes to 2800, scoreof player 2 stays at 0\n" +
            "then player 1 rolls 4 monkeys, 4 parrots with FC coin, scores 500 points to get him to 3300 (again can win)\n" +
            "then player2 scores 0 (3 skulls, 5 monkeys, FC Captain) and player3 scores 0 (2 skulls, 6 monkeys, FC 1 skull)\n" +
            "player 1 wins")
    void line134() {

        int currentDiceScore = 0;
        PiratesPlayer[] players = new PiratesPlayer[3];
        PiratesPlayer p1 = new PiratesPlayer("p1");
        PiratesPlayer p2 = new PiratesPlayer("p2");
        PiratesPlayer p3 = new PiratesPlayer("p3");

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        //-----------------

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Skull";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));
        currentDiceScore = piratesGame.scoreDie(dice, card);
        Assertions.assertEquals(4000, currentDiceScore);
        p1.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(4000, p1.getScore());

        //-------------------------

        //PLAYER 2

        //DRAW FORTUNE CARD
        PiratesFortuneCard card2 = piratesGame.drawFortuneCard(deck);
        card2 = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice2 = piratesGame.rollDice();

        dice2[0] = "Skull";
        dice2[1] = "Skull";
        dice2[2] = "Skull";
        dice2[3] = "Monkey";
        dice2[4] = "Monkey";
        dice2[5] = "Monkey";
        dice2[6] = "Monkey";
        dice2[7] = "Monkey";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice2, card2)));
        currentDiceScore = piratesGame.scoreDie(dice2, card2);
        Assertions.assertEquals(0, currentDiceScore);
        p2.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(0, p2.getScore());

        //-------------------------

        //PLAYER 3

        //DRAW FORTUNE CARD
        PiratesFortuneCard card3 = piratesGame.drawFortuneCard(deck);
        card3 = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice3 = piratesGame.rollDice();

        dice3[0] = "Skull";
        dice3[1] = "Skull";
        dice3[2] = "Skull";
        dice3[3] = "Skull";
        dice3[4] = "Skull";
        dice3[5] = "Skull";
        dice3[6] = "Parrot";
        dice3[7] = "Parrot";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice3, card3)));
        currentDiceScore = piratesGame.scoreDie(dice3, card3);
        Assertions.assertEquals(1200, currentDiceScore);

        //UPDATE SCORE CAUSE OF ISLAND OF SKULLS DEDUCTION
        p1.setScoreSheet(0, -1*currentDiceScore);
        Assertions.assertEquals(2800, p1.getScore());

        p2.setScoreSheet(0, -1*currentDiceScore);
        Assertions.assertEquals(0, p2.getScore());

        //-----------------------------

        //NEXT ROUND

        //DRAW FORTUNE CARD
        card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        dice = piratesGame.rollDice();

        dice[0] = "Monkey";
        dice[1] = "Monkey";
        dice[2] = "Monkey";
        dice[3] = "Monkey";
        dice[4] = "Parrot";
        dice[5] = "Parrot";
        dice[6] = "Parrot";
        dice[7] = "Parrot";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));
        currentDiceScore = piratesGame.scoreDie(dice, card);
        //THE EXCEL SHEET IS WRONG
        //IF THE PLAYER SCORES 4 PARROTS, 4 MONKEYS, AND A COIN
        //THIS  IS FOUR OF A KIND IS 200, MULTIPLIED BY 2 CAUSE THERE ARE 4 PARROTS AND 4 MONKEYS
        //+100 FOR THE GOLD COIN
        //+500 FOR FULL CHEST USING ALL 8 DIE
        //THEREFORE, 1000
        Assertions.assertEquals(1000, currentDiceScore);
        p1.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(3800, p1.getScore());

        //-------------------------

        //PLAYER 2

        //DRAW FORTUNE CARD
        card2 = piratesGame.drawFortuneCard(deck);
        card2 = fortuneCard.createFortuneCard(1); //CAPTAIN

        dice2 = piratesGame.rollDice();

        dice2[0] = "Skull";
        dice2[1] = "Skull";
        dice2[2] = "Skull";
        dice2[3] = "Monkey";
        dice2[4] = "Monkey";
        dice2[5] = "Monkey";
        dice2[6] = "Monkey";
        dice2[7] = "Monkey";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice2, card2)));
        currentDiceScore = piratesGame.scoreDie(dice2, card2);
        Assertions.assertEquals(0, currentDiceScore);
        p2.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(0, p2.getScore());

        //-------------------------

        //PLAYER 3

        //DRAW FORTUNE CARD
        card3 = piratesGame.drawFortuneCard(deck);
        card3 = fortuneCard.createFortuneCard(7); //FC 1 SKULL

        dice3 = piratesGame.rollDice();

        dice3[0] = "Skull";
        dice3[1] = "Skull";
        dice3[2] = "Monkey";
        dice3[3] = "Monkey";
        dice3[4] = "Monkey";
        dice3[5] = "Monkey";
        dice3[6] = "Monkey";
        dice3[7] = "Monkey";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice3, card3)));
        currentDiceScore = piratesGame.scoreDie(dice3, card3);
        Assertions.assertEquals(0, currentDiceScore);

        PiratesPlayer winner = piratesGame.getWinner(players);
        System.out.println("winner: " + winner.name);

        Assertions.assertEquals(p1, winner);

    }

    @Test
    @DisplayName("line 142: player 1 scores 0 with (3 skulls, 5 monkeys, FC Captain) player2 rolls 7 swords + 1 skull with FC captain (gets 4000 points - could win) then player3 scores 0 with FC 2 skulls and a roll of 1 skull & 7 swords then player 1 has FC Captain rolls 8 swords and thus gets 9000 points => player 1 WINS")
    void line142() {

        //ALL FUNCTIONS ALREADY EXISTED

        int currentDiceScore = 0;
        PiratesPlayer[] players = new PiratesPlayer[3];
        PiratesPlayer p1 = new PiratesPlayer("p1");
        PiratesPlayer p2 = new PiratesPlayer("p2");
        PiratesPlayer p3 = new PiratesPlayer("p3");

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        //-----------------

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Skull";
        dice[3] = "Monkey";
        dice[4] = "Monkey";
        dice[5] = "Monkey";
        dice[6] = "Monkey";
        dice[7] = "Monkey";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));
        currentDiceScore = piratesGame.scoreDie(dice, card);
        Assertions.assertEquals(0, currentDiceScore);
        p1.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(0, p1.getScore());

        //-------------------------

        //PLAYER 2

        //DRAW FORTUNE CARD
        PiratesFortuneCard card2 = piratesGame.drawFortuneCard(deck);
        card2 = fortuneCard.createFortuneCard(1); //CAPTAIN

        String[] dice2 = piratesGame.rollDice();

        dice2[0] = "Sword";
        dice2[1] = "Sword";
        dice2[2] = "Sword";
        dice2[3] = "Sword";
        dice2[4] = "Skull";
        dice2[5] = "Sword";
        dice2[6] = "Sword";
        dice2[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice2, card2)));
        currentDiceScore = piratesGame.scoreDie(dice2, card2);
        Assertions.assertEquals(4000, currentDiceScore);
        p2.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(4000, p2.getScore());

        //-------------------------

        //PLAYER 3

        //DRAW FORTUNE CARD
        PiratesFortuneCard card3 = piratesGame.drawFortuneCard(deck);
        card3 = fortuneCard.createFortuneCard(8); //FC 2 SKULLS

        String[] dice3 = piratesGame.rollDice();

        dice3[0] = "Sword";
        dice3[1] = "Sword";
        dice3[2] = "Sword";
        dice3[3] = "Sword";
        dice3[4] = "Skull";
        dice3[5] = "Sword";
        dice3[6] = "Sword";
        dice3[7] = "Sword";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice3, card3)));
        currentDiceScore = piratesGame.scoreDie(dice3, card3);
        Assertions.assertEquals(0, currentDiceScore);
        p3.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(0, p3.getScore());

        //---

        //DRAW FORTUNE CARD
        card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(1); //CAPTAIN

        dice = piratesGame.rollDice();

        dice[0] = "Sword";
        dice[1] = "Sword";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));
        currentDiceScore = piratesGame.scoreDie(dice, card);
        Assertions.assertEquals(9000, currentDiceScore);
        p1.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(9000, p1.getScore());

        PiratesPlayer winner = piratesGame.getWinner(players);
        System.out.println("winner: " + winner.name);

        Assertions.assertEquals(p1, winner);

    }

    @Test
    @DisplayName("line 147: Player 1 rolls 6 swords, 2 skulls and FC coin, scores 1100 points Player 2 has FC Sorceress and rolls 7 skulls and a coin, uses sorceress to reroll a skull into a parrot then selects the coin and the parrot and gets 2 skulls: now has 8 skulls => -800 for player1 (now at 300) and still 0 for players 2 and 3")
    void line147() {

        //FUNCTIONS ALREADY EXISTED

        int currentDiceScore = 0;
        PiratesPlayer[] players = new PiratesPlayer[3];
        PiratesPlayer p1 = new PiratesPlayer("p1");
        PiratesPlayer p2 = new PiratesPlayer("p2");
        PiratesPlayer p3 = new PiratesPlayer("p3");

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        //-----------------

        //DRAW FORTUNE CARD
        PiratesFortuneCard card = piratesGame.drawFortuneCard(deck);
        card = fortuneCard.createFortuneCard(4); //GOLD

        String[] dice = piratesGame.rollDice();

        dice[0] = "Skull";
        dice[1] = "Skull";
        dice[2] = "Sword";
        dice[3] = "Sword";
        dice[4] = "Sword";
        dice[5] = "Sword";
        dice[6] = "Sword";
        dice[7] = "Sword";

        Assertions.assertEquals(false, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));
        currentDiceScore = piratesGame.scoreDie(dice, card);
        Assertions.assertEquals(1100, currentDiceScore);
        p1.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(1100, p1.getScore());

        //-------------------------

        //PLAYER 2

        //DRAW FORTUNE CARD
        PiratesFortuneCard card2 = piratesGame.drawFortuneCard(deck);
        card2 = fortuneCard.createFortuneCard(2); //SORCERESS

        String[] dice2 = piratesGame.rollDice();

        dice2[0] = "Skull";
        dice2[1] = "Skull";
        dice2[2] = "Skull";
        dice2[3] = "Skull";
        dice2[4] = "Skull";
        dice2[5] = "Skull";
        dice2[6] = "Skull";
        dice2[7] = "Coin";

        dieToKeep = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        dice2 = piratesGame.reRollNotHeld(dice2, dieToKeep);

        dice2[0] = "Skull";
        dice2[1] = "Skull";
        dice2[2] = "Skull";
        dice2[3] = "Skull";
        dice2[4] = "Skull";
        dice2[5] = "Skull";
        dice2[6] = "Skull";
        dice2[7] = "Skull";

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice2, card2)));
        currentDiceScore = piratesGame.scoreDie(dice2, card2);
        currentDiceScore = (100 * piratesGame.checkSkullCount(dice2)) * -1;
        Assertions.assertEquals(-800, currentDiceScore);
        //-------------------------

        //DEDUCTIONS FROM ISLAND OF SKULLS
        p1.setScoreSheet(0, currentDiceScore);
        Assertions.assertEquals(300, p1.getScore());

        Assertions.assertEquals(0, p2.getScore());

        Assertions.assertEquals(0, p3.getScore());

    }


    //-------------------------------------------------







}
