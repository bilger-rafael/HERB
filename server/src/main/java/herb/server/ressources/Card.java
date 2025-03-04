package herb.server.ressources;

import com.fasterxml.jackson.annotation.JsonIgnore;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.Rank;
import herb.server.ressources.core.Suit;

//Etter
public class Card extends CardBase {

	public Card(Suit suit, Rank rank) {
		super(suit, rank);
	}

	//gibt den Punktewert der Karte zurück
	@JsonIgnore
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
			if(this.getTrump()!=null) {
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
		}
		return points;
	}
	
	//o ist die Karte vom vorherigen Spieler, gibt Int mit der Rank-Differenz zurück
	public int compareTo(CardBase o) {
		int result=0;
		//Ich habe Trumpf
		if(this.isTrump()) {
			//Beide Trumpf
			if (this.getSuit()==o.getSuit()) {
					//Buur und Nell
					if(this.getRank().ordinal()==3 || this.getRank().ordinal()==5 || o.getRank().ordinal()==3 || o.getRank().ordinal()==5) {
						if (this.getRank().ordinal()==3) result=9;
						if (o.getRank().ordinal()==3)	result=-9;
						if (this.getRank().ordinal()==5) result=10;
						if (o.getRank().ordinal()==5) 	result=-10;
						//Kein Buur und Nell	
					}else {
						result=this.getRank().ordinal()-o.getRank().ordinal();
					}	

				}else { 
				//Ich habe Trump, der Andere nicht
					result = 8;
				}
		}
		// Ich habe keinen Trumpf
		if(!this.isTrump()&&!this.isBottomUp()&&!this.isTopDown()) {
			//Gleiche Suit
			if(this.getSuit()==o.getSuit()) {
				result=this.getRank().ordinal()-o.getRank().ordinal();
			}else {
				result=-8;
			}
		}
		//Obeabe
		if (this.isTopDown()) {
			//gleicher Suit
			if (this.getSuit()==o.getSuit()) {
				result=this.getRank().ordinal()-o.getRank().ordinal();
			}else {
			//unterschiedlicher Suit
				result=-8;					
			}
		}
		//Undeufe
		if(this.isBottomUp()) {
			//gleicher Suit
			if (this.getSuit()==o.getSuit()) {
				result=o.getRank().ordinal()-this.getRank().ordinal();	
			}else {
			//unterschiedlicher Suit
				result=-8;
			}
		}
		return result;
	}
	
	//Gibt zurück ob die Karte gewinnt gegen die andere Karte
	public boolean compareToPlayable(CardBase o) {
		 if(this.compareTo(o)>0) {
			 return true;
		 }else return false;
	}

	//Gibt den Werte der Karte wieder für den Spieler
	@JsonIgnore
	public int getGameValue() {
		int value = 0;
		
		//Trumpf
		if(isTrump()) {
			value+=10;
			int temp = this.getRank().ordinal();
			//Nell
			if(temp==3) {
				value+=10;
			}
			//Buur
			if(temp==5) {
				value+=15;
			}
			if(temp!=3&&temp!=5) {
				value+=this.getRank().ordinal();
			}
		}
		
		//TopDown
		if(isTopDown()) {
			value+=this.getRank().ordinal();
		}
		
		
		//BottomUp
		if(isBottomUp()) {
			value-=this.getRank().ordinal();
		}
		
		//Nicht Trumpf
		if(!isTrump()&&!isBottomUp()&&!isTopDown()) {
			if(this.getTrump()!=null)
			value+=this.getRank().ordinal();
		}
		
		return value;
	}

	//Boolean ob eine Karte gewinnt
	public boolean compareToWinnerCard(Card o) {
		if(this.getGameValue()>o.getGameValue()) {
			return true;
		}
		
		return false;
	}

}
