package herb.server.ressources;

import java.util.Arrays;
import java.util.Comparator;

import herb.server.ressources.core.HandBase;

//Etter
public class Hand extends HandBase<Card>{
	private int addIndex;
	
	public Hand() {
		this.addIndex=0;
		super.setCards(new Card[9]);
	}

	@Override
	public void play(Card card) {
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
	public void addCard(Card card) {
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
	private class HandSorter implements Comparator<Card>{
		@Override
			public int compare(Card o1, Card o2) {
				int score = 0;
				int scoreO = 0;
				int suitValue = o1.getSuit().ordinal();
				int suitValueO = o2.getSuit().ordinal();
			
				//Diese Karte
				switch (suitValue) {
				case(0): 
					score+=10;
					score= score+(o1.getRank().ordinal());
					break;
				case(1): 
					score+=20;
					score= score+(o1.getRank().ordinal());
					break;
				case(2): 
					score+=30;
					score= score+(o1.getRank().ordinal());
					break;
				case(3): 
					score+=40;
					score= score+(o1.getRank().ordinal());	
					break;
				}
				//Trump höher werten
				if(o1.isTrump()) {
					score+=40;
					if(o1.getPoints()==20) {
						score+=100;
					}
					if(o1.getPoints()==14) {
						score+=50;
					}
				}
				
				//Die andere Karte
				switch (suitValueO) {
				case(0): 
					scoreO+=10;
					scoreO= scoreO+(o2.getRank().ordinal());
					break;
				case(1): 
					scoreO+=20;
					scoreO= scoreO+(o2.getRank().ordinal());
					break;
				case(2): 
					scoreO+=30;
					scoreO= scoreO+(o2.getRank().ordinal());
					break;
				case(3): 
					scoreO+=40;
					scoreO= scoreO+(o2.getRank().ordinal());	
					break;
				}
				//Trump höher werten
				if(o2.isTrump()) {
					scoreO+=40;
					if(o2.getPoints()==20) {
						scoreO+=100;
					}
					if(o2.getPoints()==14) {
						scoreO+=50;
					}
				}
				
			//System.out.println("Karte "+score+"andere Karte"+scoreO);
			return score-scoreO;
		}
	}

	@Override
	public Card getCard(int i) {
		return this.getCards()[i];
	}


}
