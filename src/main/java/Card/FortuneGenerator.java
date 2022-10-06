package Card;

import Fortunes.*;

public class FortuneGenerator extends PiratesFortuneCardGenerator {

    PiratesFortuneCard createCard(int fortuneCardID) {
        if (fortuneCardID == 1) {
            return new Captain();
        } else if (fortuneCardID == 4) {
            return new Gold();

        } else {
            return null;
        }
    }

}
