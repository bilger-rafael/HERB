package herb.server.ressources;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.Rank;
import herb.server.ressources.core.Suit;
import herb.server.ressources.core.Trump;

public class Card extends CardBase {

	public Card(Suit suit, Rank rank, Trump trump) {
		super(suit, rank, trump);
		// TODO Auto-generated constructor stub
	}

	@Override //Etter gibt den Punktewert der Karte zurück
	public int getPoints() {
		int points = 0;
		
		//Obeabe
		if(isTopDown()) {
			int i = rank.ordinal();
			switch (i) {
				case(0): points = 0;
					break;
				case(1): points = 0;
					break;
				case(2): points = 8;
					break;
				case(3): points = 0;
					break;				
				case(4): points = 10;
					break;			
				case(5): points = 2;
					break;				
				case(6): points = 3;
					break;				
				case(7): points = 4;
					break;				
				case(8): points = 11;
					break;
			}
		}
		//Undeufe	
		if(isBottomUp()) {
			int i = rank.ordinal();
			switch (i) {
				case(0): points = 11;
					break;
				case(1): points = 0;
					break;
				case(2): points = 8;
					break;
				case(3): points = 0;
					break;				
				case(4): points = 10;
					break;			
				case(5): points = 2;
					break;				
				case(6): points = 3;
					break;				
				case(7): points = 4;
					break;				
				case(8): points = 0;
					break;
			}
		}
		//Trumpf
		if(isTrump()) {
			int i = rank.ordinal();
			switch (i) {
				case(0): points = 0;
					break;
				case(1): points = 0;
					break;
				case(2): points = 0;
					break;
				case(3): points = 14;
					break;				
				case(4): points = 10;
					break;			
				case(5): points = 20;
					break;				
				case(6): points = 3;
					break;				
				case(7): points = 4;
					break;				
				case(8): points = 11;
					break;
			}
		}
		//Nicht Trumpf
		if(!isTrump() && !isTopDown() && !isBottomUp()) {
			int i = rank.ordinal();
			switch (i) {
				case(0): points = 0;
					break;
				case(1): points = 0;
					break;
				case(2): points = 0;
					break;
				case(3): points = 0;
					break;				
				case(4): points = 10;
					break;			
				case(5): points = 2;
					break;				
				case(6): points = 3;
					break;				
				case(7): points = 4;
					break;				
				case(8): points = 11;
					break;
			}	
		}
		return points;
	}
	
	private boolean isTrump() {
		if(this.suit.name() == this.trump.name())
			return true;
		else return false;
	}
	
	private boolean isTopDown() {
		if (this.trump.ordinal() == 4)
			return true;
		else return false;
	}
	
	private boolean isBottomUp() {
		if (this.trump.ordinal() == 5)
			return true;
		else return false;
	}
	

}
