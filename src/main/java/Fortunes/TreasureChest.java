package Fortunes;

import Card.PiratesFortuneCard;

public class TreasureChest extends PiratesFortuneCard {

    private final int count = 4;

    public TreasureChest() {
        fortuneCardID = 0;
        name = "Treasure Chest";
        description = "In the Treasure Chest you may protect your fortune. After each roll you may place (or take out) any die that you decide to " +
                "keep on the Treasure Chest card. If you are disqualified, you still score the points for the dice that you have placed on the card.";
    }

}
