
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

    //GAME STEUP TO ENABLE TEST
    @Given("game setup")
    public void init() {
        piratesGame = new PiratesGame();
        dice = new String[8];
        deck = piratesGame.createFortuneDeck();
    }

    //SINGLE PLAYER OBJECT
    @Given("a player object")
    public void create_a_player() {
        PiratesPlayer p1 = new PiratesPlayer("p1");
    }

    //DEFAULT CARD WHEN A CARD ISN'T SPECIFIED
    @Given("default gold card")
    public void give_gold_card() {
        System.out.println("Dealing fortune card...");
        card = piratesGame.drawFortuneCard(deck);

        card = fortuneCard.createFortuneCard(4);
        System.out.println("Default gold card dealt!");
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

        piratesGame.isPlayerDead(piratesGame.checkSkullCount(dice));

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


}

