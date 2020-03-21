package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;

public class Card extends CardBase {

	public Card(Suit suit, Rank rank, Trump trump) {
		super(suit, rank, trump);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getPoints() {
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

	@Override
	public int compareTo(CardBase o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean compareToPlayable(CardBase o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Suit getSuit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rank getRank() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trump getTrump() {
		// TODO Auto-generated method stub
		return null;
	}


}
