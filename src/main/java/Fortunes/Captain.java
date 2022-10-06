package Fortunes;

import Card.PiratesFortuneCard;

public class Captain extends PiratesFortuneCard {

    private final int count = 4;

    public Captain() {
        fortuneCardID = 1;
        name = "Captain";
        description = "The score you make this turn is doubled. If you go to the Island of the Dead, " +
                "each player will lose 200 points for each skull (instead of 100 points).";
    }

    public int applyCardRule(int score) {
        return score * 2;
    }

}
