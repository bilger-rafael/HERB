package herb.server.ressources.core;

public abstract class CardBase {
    protected final Suit suit;
    protected final Rank rank;
    protected final Trump trump;
    
    public CardBase(Suit suit, Rank rank, Trump trump) {
    	this.suit = suit;
    	this.rank = rank;
    	this.trump = trump;
    }
    
    public abstract int getPoints();
}
