package herb.client.ressources.core;

public abstract class CardBase {
    private final Suit suit;
    private final Rank rank;
    
    public CardBase(Suit suit, Rank rank) {
    	this.suit = suit;
    	this.rank = rank;
    }
    
    public abstract void getPoints();
}
