package herb.client.ressources.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CardBase {
    protected final Suit suit;
    protected final Rank rank;
    protected final Trump trump;
    
    public CardBase(Suit suit, Rank rank, Trump trump) {
    	this.suit = suit;
    	this.rank = rank;
    	this.trump = trump;
    }
    
	@Override
	public boolean equals(Object o) {
		// self check
		if (this == o)
			return true;
		// null check
		if (o == null)
			return false;
		// type check and cast
		if (getClass() != o.getClass())
			return false;
		CardBase c = (CardBase) o;
		// field comparison	
		return this.getSuit().equals(c.getSuit()) && 
			   this.getRank().equals(c.getRank());
	}
    
    @JsonIgnore
    public abstract int getPoints();
    
    protected abstract boolean isTrump();
    
    protected abstract boolean isTopDown();
    
    protected abstract boolean isBottomUp();

	public abstract int compareTo(CardBase o);
	
	public abstract boolean compareToPlayable(CardBase o);

	public abstract Suit getSuit();
	
	public abstract Rank getRank();
	
	public abstract Trump getTrump();
}
