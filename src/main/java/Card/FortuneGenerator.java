package Card;

import Fortunes.*;

public class FortuneGenerator extends PiratesFortuneCardGenerator {

    PiratesFortuneCard createCard(int fortuneCardID) {
        if (fortuneCardID == 1) {
            return new Captain();

        } else if (fortuneCardID == 2) {
            return new Sorceress();

        } else if (fortuneCardID == 4) {
            return new Gold();

        } else if (fortuneCardID == 5) {
            return new Diamond();

        } else if (fortuneCardID == 6) {
            return new MonkeyBusiness();

        } else {
            return null;
        }
    }

}
