package herb.server.ressources;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.yaml.snakeyaml.nodes.CollectionNode;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.HandBase;

//Etter
public class Hand extends HandBase{
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
	public boolean cardsEmpty() {
		if (cards.length==0) {
			return true;
		}else {
			return false;
		}
	}

	@Override //Sortiert das Array für das UI
	public void sortCards() {
		Arrays.sort(cards, new HandSorter());	
	}
	
	//Gibt zurück ob eine Karte höher ist beim Einordnen
	private class HandSorter implements Comparator<CardBase>{
		@Override
			public int compare(CardBase o1, CardBase o2) {
				int score = 0;
				int suitValue = o1.getSuit().ordinal();
			
				switch (suitValue) {
				case(0): 
					score+=10;
					score= score+(o1.getRank().ordinal()-o2.getRank().ordinal());
					break;
				case(1): 
					score+=20;
					score= score+(o1.getRank().ordinal()-o2.getRank().ordinal());
					break;
				case(2): 
					score+=30;
					score= score+(o1.getRank().ordinal()-o2.getRank().ordinal());
					break;
				case(3): 
					score+=40;
					score= score+(o1.getRank().ordinal()-o2.getRank().ordinal());	
					break;
			}
			return score;
		}
	}


}
