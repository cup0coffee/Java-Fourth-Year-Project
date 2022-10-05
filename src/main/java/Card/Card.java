package Card;

import java.io.Serializable;

public abstract class Card implements Serializable {

    //SPECIFIC TO ALL CARDS
    protected String name;
    protected String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



}
