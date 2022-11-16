
import Card.FortuneGenerator;
import Card.PiratesFortuneCard;
import Card.PiratesFortuneCardGenerator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class MyStepdefs {

    PiratesGame piratesGame;
    PiratesFortuneCardGenerator fortuneCard = new FortuneGenerator();

    PiratesFortuneCard card;

    ArrayList<PiratesFortuneCard> deck;
    String[] dice;
    String[] dieToKeep;

    PiratesPlayer player;
    PiratesPlayer player2;
    PiratesPlayer player3;
    PiratesPlayer[] players;
    String[] treasureToKeep;


    //GAME STEUP TO ENABLE TEST
    @Given("game setup with gold card")
    public void init() {
        piratesGame = new PiratesGame();
        dice = new String[8];
        deck = piratesGame.createFortuneDeck();
        player = new PiratesPlayer("p2");

        System.out.println("Dealing fortune card...");
        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(4);
        System.out.println("Default gold card dealt!");

    }

    @Given("game setup with {int}")
    public void init_with_card(int int1) {
        piratesGame = new PiratesGame();
        dice = new String[8];
        deck = piratesGame.createFortuneDeck();
        player = new PiratesPlayer("p2");

        System.out.println("Dealing fortune card...");
        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(int1);
        System.out.println("card dealt: " + card.getName());

    }

    //SINGLE PLAYER OBJECT
    @Given("a player object")
    public void create_a_player() {
        PiratesPlayer p1 = new PiratesPlayer("p1");
    }

    //PROVIDING SCORES FOR TESTING PURPOSES
    //SINGLE PLAYER OBJECT
    @And("player has {int} points")
    public void set_player_points(int int1) {
        player.setScoreSheet(0, int1);

        System.out.println("Player 2 score is : " + player.getScore());

    }

    @And("player {int} is {string} {string} {string} {string} {string} {string} {string} {string}")
    public void update_player_score(int int1, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        int currentDiceScore = 0;

        currentDiceScore = piratesGame.scoreDie(dice, card);

        System.out.println("player current score: " + player.getScore());
        System.out.println("score earned: " + currentDiceScore);

        int newScore = 0;

        newScore = ((player.getScore())+(currentDiceScore));

        if (newScore <= 0) {
            newScore = 0;
        }

        if(newScore <= 0) {
            player.setScoreSheet(0,0);
        } else {
            player.setScoreSheet(0, newScore);
        }

        Assertions.assertEquals(int1, newScore);

        System.out.println("player score is now: " + newScore);

    }

    //DEFAULT CARD WHEN A CARD ISN'T SPECIFIED
    @Given("default gold card")
    public void give_gold_card() {
        System.out.println("Dealing fortune card...");
        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(4);
        System.out.println("Default gold card dealt!");
    }

    //CARD IS SPECIFIED
    @Given("card is {int}")
    public void give_card(int int1) {
        System.out.println("Dealing fortune card...");
        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(int1);
        System.out.println("card dealt: " + card.getName());
    }

    //ROLLING DICE
    @When("roll is {string} {string} {string} {string} {string} {string} {string} {string}")
    public void roll_is(String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {

        System.out.println("Rolling the dice...");
        dice = piratesGame.rollDice();

        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        piratesGame.printDieRoll(dice);
    }

    //DO SAME FOR REROLL
    @And("player wants to hold {string} and reroll is {string} {string} {string} {string} {string} {string} {string} {string}")
    public void player_wants_to_hold_and_reroll(String string, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {

        System.out.println("Rerolling the dice...");

        dieToKeep = string.replaceAll("\\s", "").split(",");

        dice = piratesGame.reRollNotHeld(dice, dieToKeep);

        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        piratesGame.printDieRoll(dice);
    }

    //CHECKING FOR DEATH
    @Then("death with {string} {string} {string} {string} {string} {string} {string} {string}")
    public void check_if_dead(String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        Assertions.assertEquals(true, piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice, card)));

        System.out.println("You're dead!");

    }

    @Then("turn is complete")
    public void complete() {

        System.out.println("Turn is complete!");
        System.out.println("\n---------------------------------------------------\n\n");

    }

    //CHECKING FOR SCORE
    @And("{int} is {string} {string} {string} {string} {string} {string} {string} {string}")
    public void check_dice_score(int int1, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        System.out.println("score earned: " + piratesGame.scoreDie(dice, card));

        Assertions.assertEquals(int1, piratesGame.scoreDie(dice, card));

    }

    @And("{int} with treasure {string} is {string} {string} {string} {string} {string} {string} {string} {string}")
    public void check_treasure_score(int int1, String string, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {

        System.out.println("You died! \n Scoring treasure...");

        treasureToKeep = string.replaceAll("\\s", "").split(",");

        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        int treasurescore = 0;

        treasurescore = piratesGame.scoreTreasureChest(dice, treasureToKeep, card);

        System.out.println("score earned: " + treasurescore);

        Assertions.assertEquals(int1, treasurescore);

    }


    @And("player wants to hold {string} from {string} {string} {string} {string} {string} {string} {string} {string}")
    public void player_wants_treasure(String string, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {

        System.out.println("Stashing treasure...");

        treasureToKeep = string.replaceAll("\\s", "").split(",");

        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        piratesGame.printTreasureChest(dice, treasureToKeep, card);

    }


    @And("{int} deduction is {string} {string} {string} {string} {string} {string} {string} {string}")
    public void check_skull_island_score(int int1, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8) {
        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        int numSkulls = piratesGame.checkSkullCount(dice, card);

        int deduction = numSkulls * 100;
        deduction*=-1;

        if(card.getName().equalsIgnoreCase("Captain")) {
            deduction*=2;
        }

        Assertions.assertEquals(int1, deduction);

        System.out.println("score deduction for other players: " + deduction);

    }

    //MULTIPLAYER
    @Given("multiplayer game setup where player 1 starts a gets {int}")
    public void init_multiplayer(int int1) {
        piratesGame = new PiratesGame();
        dice = new String[8];
        deck = piratesGame.createFortuneDeck();

        int currentDiceScore = 0;
        players = new PiratesPlayer[3];

        //PLAYERS
        player = new PiratesPlayer("p1");
        player2 = new PiratesPlayer("p2");
        player3 = new PiratesPlayer("p3");

        players[0] = player;
        players[1] = player2;
        players[2] = player3;


        System.out.println("Dealing fortune card for p1...");
        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(int1);
        System.out.println("card dealt: " + card.getName());
    }

    @And("{int} is {string} {string} {string} {string} {string} {string} {string} {string} with {int}")
    public void check_dice_score_with_card(int int1, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8, int cardnum) {
        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        System.out.println("Dealing fortune card...");
        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(cardnum);
        System.out.println("card dealt: " + card.getName());

        System.out.println("score earned: " + piratesGame.scoreDie(dice, card));

        Assertions.assertEquals(int1, piratesGame.scoreDie(dice, card));

    }

    @And("winner based on {int}, {int}, and {int} is {string}")
    public void check_winner(int p1score, int p2score, int p3score, String winner) {

        //PLAYER 1 SCORESHEET
        player.setScoreSheet(0, p1score);

        //PLAYER 2 SCORESHEET
        player2.setScoreSheet(0, p2score);

        //PLAYER 3 SCORESHEET
        player3.setScoreSheet(0, p3score);


        PiratesPlayer winningPlayer = piratesGame.getWinner(players);
        System.out.println("winner: " + winningPlayer.name + " with " + winningPlayer.getScore() + " points!");

        Assertions.assertEquals(winner, winningPlayer.name);

    }

    @And("{int} deduction is {string} {string} {string} {string} {string} {string} {string} {string} with {int}")
    public void check_skull_island_score(int int1, String string1, String string2, String string3, String string4, String string5, String string6, String string7, String string8, int cardnum) {
        dice[0] = string1;
        dice[1] = string2;
        dice[2] = string3;
        dice[3] = string4;
        dice[4] = string5;
        dice[5] = string6;
        dice[6] = string7;
        dice[7] = string8;

        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(cardnum);

        int numSkulls = piratesGame.checkSkullCount(dice, card);

        int deduction = numSkulls * 100;
        deduction*=-1;

        if(card.getName().equalsIgnoreCase("Captain")) {
            deduction*=2;
        }

        Assertions.assertEquals(int1, deduction);

        System.out.println("score deduction for other players: " + deduction);

    }
    @And("scores are {int}, {int}, and {int}")
    public void set_scores(int p1score, int p2score, int p3score) {

        //PLAYER 1 SCORESHEET
        player.setScoreSheet(0, p1score);

        //PLAYER 2 SCORESHEET
        player2.setScoreSheet(0, p2score);

        //PLAYER 3 SCORESHEET
        player3.setScoreSheet(0, p3score);

    }

    @And("others lose {int}")
    public void deduct_skull_island(int deduction) {

        //PLAYER 1 SCORESHEET
        player.setScoreSheet(0, deduction);

        //PLAYER 2 SCORESHEET
        player2.setScoreSheet(0, deduction);

        System.out.println("player 1 loses " + deduction + " and now has " + player.getScore());
        System.out.println("player 2 loses " + deduction + " and now has " + player2.getScore());

    }

    @Then("show scores and {string}")
    public void show_scores(String winner) {

        System.out.println("player 1 has " + player.getScore());
        System.out.println("player 2 has " + player2.getScore());
        System.out.println("player 3 has " + player3.getScore());

        System.out.println();

        PiratesPlayer winningPlayer = piratesGame.getWinner(players);
        System.out.println("winner: " + winningPlayer.name + " with " + winningPlayer.getScore() + " points!");

        Assertions.assertEquals(winner, winningPlayer.name);




    }







}

