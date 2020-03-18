package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;

public class Card extends CardBase {

	//Etter
	public Card(Suit suit, Rank rank, Trump trump) {
		super(suit, rank, trump);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPoints() {
		return 0;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(CardBase o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean isTrump() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isTopDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isBottomUp() {
		// TODO Auto-generated method stub
		return false;
	}

}
