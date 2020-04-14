package herb.client.ressources.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class HandBase <Card extends CardBase> {
	@JsonIgnoreProperties({ "trump" })
	protected Card[] cards;
	
	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	
	public Card[] getCards() {
		return cards;
	}

	public abstract void play(Card card);
	
	public abstract void addCard(Card card);
	
	public abstract void clearCards();
	
	public abstract void sortCards();
	
	public abstract boolean cardsEmpty();
	
	public abstract Card getCard(int i);
	
	
}
