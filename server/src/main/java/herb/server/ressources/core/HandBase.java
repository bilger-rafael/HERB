package herb.server.ressources.core;


public abstract class HandBase {
	protected CardBase[] cards;
	
	public CardBase[] getCards() {
		return cards;
	}

	public abstract void play(CardBase card);
	
	public abstract void addCard(CardBase card);
	
	public abstract void clearCards();
	
	public abstract void sortCards();
	
	public abstract boolean cardsEmpty();
	
	public abstract CardBase getCard(int i);
	
	
}
