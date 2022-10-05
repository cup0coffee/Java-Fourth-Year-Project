package Card;

public abstract class PiratesFortuneCardGenerator {

    abstract PiratesFortuneCard createCard(int cardID);

    public PiratesFortuneCard createFortuneCard(int fortuneCardID) {
        PiratesFortuneCard fortuneCard = createCard(fortuneCardID);
        return fortuneCard;
    }
}
