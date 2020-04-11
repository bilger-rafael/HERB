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

	@JsonIgnore
    public abstract int getPoints();
    
    protected abstract boolean isTrump();
    
    protected abstract boolean isTopDown();
    
    protected abstract boolean isBottomUp();

	public abstract int compareTo(CardBase o);
	
	public abstract boolean compareToPlayable(CardBase o);
	
    public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	public Trump getTrump() {
		return trump;
	}



}
