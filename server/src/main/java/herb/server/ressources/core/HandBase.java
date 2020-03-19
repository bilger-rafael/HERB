package herb.server.ressources.core;

import java.util.Comparator;

import herb.server.ressources.Card;


public abstract class HandBase {
	private CardBase[] cards;
	
	public abstract void play(CardBase card);
	
	public abstract void addCard(CardBase card);
	
	public abstract void clearCards();
	
	public abstract void sortCards();
	
	public abstract boolean cardsEmpty();
	
}
