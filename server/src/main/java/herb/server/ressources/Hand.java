package herb.server.ressources;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.HandBase;

//Etter
public class Hand extends HandBase{
	private int addIndex;
	
	public Hand() {
		this.addIndex=0;
		super.setCards(new Card[9]);
	}

	@Override
	public void play(CardBase card) {
		try { 
			for (int i=0; i<this.getCards().length; i++) {
			if (this.getCards()[i] == card) {
				this.getCards()[i] = null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception => Meldung auf UI
		}
		
	}

	@Override
	public void addCard(CardBase card) {
		this.getCards()[this.addIndex] = card;
		this.addIndex++;
	}

	@Override
	public void clearCards() {
		Arrays.fill(this.getCards(), null);
	}

	@Override
	public boolean cardsEmpty() {
		if (this.getCards().length==0) {
			return true;
		}else {
			return false;
		}
	}

	@Override //Sortiert das Array für das UI
	public void sortCards() {
		Arrays.sort(this.getCards(), new HandSorter());	
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

	@Override
	public CardBase getCard(int i) {
		return this.getCards()[i];
	}


}
