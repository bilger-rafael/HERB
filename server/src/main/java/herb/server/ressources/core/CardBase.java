package herb.server.ressources.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CardBase {
    protected final Suit suit;
    protected final Rank rank;
    @JsonIgnore
    protected Trump trump;
	protected boolean playable;
    
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
    
    public void setPlayable(boolean playable) {
		this.playable = playable;
	}

	public boolean isPlayable() {
		return playable;
	}
	
	public boolean isTrump() {
		if(this.suit.ordinal() == this.trump.ordinal()) {
			return true;
		}else { return false;}
	}

	public CardBase(Suit suit, Rank rank, Trump trump) {
    	this.suit = suit;
    	this.rank = rank;
    	this.trump = trump;
    }
	
	protected  boolean isTopDown() {
		if (this.trump.ordinal() == 4)
			return true;
		else return false;
	}
	
	protected boolean isBottomUp() {
		if (this.trump.ordinal() == 5)
			return true;
		else return false;
	}

	public Suit getSuit() {
		return this.suit;
	}
	
	public Rank getRank() {
		return this.rank;
	}
	
	public Trump getTrump() {
		return this.trump;
	}
	
    public void setTrump(Trump trump) {
		this.trump = trump;
	}
}
