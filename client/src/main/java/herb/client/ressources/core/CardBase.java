package herb.client.ressources.core;

import herb.client.ressources.Trump;


//Etter
public abstract class CardBase{
    private final Suit suit;
    private final Rank rank;
    private final Trump trump;
    
    public CardBase(Suit suit, Rank rank, Trump trump) {
    	this.suit = suit;
    	this.rank = rank;
    	this.trump = trump;
    }
    
    public abstract int getPoints();
    
    protected abstract boolean isTrump();
    
    protected abstract boolean isTopDown();
    
    protected abstract boolean isBottomUp();
}
