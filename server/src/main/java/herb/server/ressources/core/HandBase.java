package herb.server.ressources.core;

import java.util.ArrayList;

//Etter
public abstract class HandBase<Card extends CardBase> {
	protected ArrayList<Card> cards;

	public HandBase() {
		this.cards = new ArrayList<Card>(9);
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public abstract void play(Card card);

	public abstract void addCard(Card card);

	public abstract void clearCards();

	public abstract void sortCards();

}
