package Fortunes;

import Card.PiratesFortuneCard;

public class SeaBattleTwoSword extends PiratesFortuneCard {

    public SeaBattleTwoSword() {
        fortuneCardID = 3;
        name = "Sea Battle (2 Swords)";
        description = "Your ship is engaged in a sea battle. To win, you must get the indicated number of swords. " +
                "If you make it, you get the indicated bonus in addition to your score.\n " +
                "If you fail, however, your dice are ignored and you lose the indicated bonus points. " +
                "A player who is engaged in a sea battle cannot go to the Island of the Dead.";
    }

}
