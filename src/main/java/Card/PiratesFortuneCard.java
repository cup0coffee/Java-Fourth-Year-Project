package Card;

public abstract class PiratesFortuneCard extends Card {

    public int fortuneCardID;

    //FUNCTIONS HERE SPECIFIC TO FORTUNE CARD
    public void printCard() {
        System.out.println(name + " - " + description);
    }
}
