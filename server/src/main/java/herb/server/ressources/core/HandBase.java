package herb.server.ressources.core;


public abstract class HandBase {
	private CardBase[] cards;
	
	public abstract void play(CardBase card);
	
	public abstract void addCard(CardBase card);
	
	public abstract void clearCards();
	
	public abstract void sortCards();
	
}
