package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;

public class Card extends CardBase {

	// default constructor for json deserialization
	public Card() {
		super(Suit.Hearts,Rank.Queen,Trump.Hearts);
	}
	
	public Card(Suit suit, Rank rank, Trump trump) {
		super(suit, rank, trump);
		// TODO Auto-generated constructor stub
	}

}
