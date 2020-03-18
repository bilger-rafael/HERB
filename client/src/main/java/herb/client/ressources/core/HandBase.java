package herb.client.ressources.core;


//Etter
public abstract class HandBase {
	private CardBase[] cards;
	private int index;
	
	public abstract void play(CardBase card);
	
	public abstract void addCard(CardBase card);
	
	public abstract void clearCards();
	
	public abstract void sortCards();
	
}
