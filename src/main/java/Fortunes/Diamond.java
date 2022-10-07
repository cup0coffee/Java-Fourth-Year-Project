package Fortunes;

import Card.PiratesFortuneCard;

public class Diamond extends PiratesFortuneCard {

    private final int count = 4;

    public Diamond() {
        fortuneCardID = 5;
        name = "Diamond";
        description = "You start your turn with one diamond. It is counted for its face value as well as for a set.";
    }

    @Override
    public String getName() {
        return name;
    }

}
