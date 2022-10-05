package Fortunes;

import Card.PiratesFortuneCard;

public class Gold extends PiratesFortuneCard {

    private final int count = 4;

    public Gold() {
        fortuneCardID = 4;
        name = "Gold";
        description = "You start your turn with one gold coin. It is counted for its face value as well as for a set.";
    }

    @Override
    public String getName() {
        return name;
    }

}
