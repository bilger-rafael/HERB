package herb.server.ressources;

import java.util.Arrays;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.HandBase;

//Etter
public class Hand extends HandBase {
	private CardBase[] cards;
	private int addIndex;
	
	public Hand() {
		this.addIndex=0;
	}

	@Override
	public void play(CardBase card) {
		try { 
			for (int i=0; i<this.cards.length; i++) {
			if (this.cards[i] == card) {
				this.cards[i] = null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception => Meldung auf UI
		}
		
	}

	@Override
	public void addCard(CardBase card) {
		this.cards[this.addIndex] = card;
		this.addIndex++;
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
