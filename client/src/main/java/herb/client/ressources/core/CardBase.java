package herb.client.ressources.core;


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
    
    protected abstract boolean isTrump();
    
    protected abstract boolean isTopDown();
    
    protected abstract boolean isBottomUp();

	public abstract int compareTo(CardBase o);
	
	public abstract boolean compareToPlayable(CardBase o);

	public abstract Suit getSuit();
	
	public abstract Rank getRank();
	
	public abstract Trump getTrump();
	
	public abstract CardBase getCard(int i);
}
