package herb.server.ressources;

import java.util.Arrays;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.HandBase;

//Etter
public class Hand extends HandBase {
	private CardBase[] cards;
	private int index;
	
	public Hand() {
		this.index=0;
	}

	@Override
	public void play(CardBase card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCard(CardBase card) {
		this.cards[this.index] = card;
		this.index++;
	}

	@Override
	public void clearCards() {
		Arrays.fill(this.cards, null);
	}

	@Override
	public void sortCards() {
		// TODO Auto-generated method stub
		
	}
}
