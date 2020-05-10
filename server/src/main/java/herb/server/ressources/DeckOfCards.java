package herb.server.ressources;

import java.util.ArrayList;
import java.util.Collections;

import herb.server.ressources.core.Rank;
import herb.server.ressources.core.Suit;

//Etter (Idee vom Pokergame, SE HS19)
public class DeckOfCards {
	private final ArrayList<Card> cardSet = new ArrayList<>();


	// Erstellen und mischen
	public DeckOfCards() {
		initializeCards();
		shuffle();
	}
	
	public int getCardsRemaining() {
		return cardSet.size();
	}

	// Ein Set der 36 Karten erstellen
	public void initializeCards() {
		cardSet.clear();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				Card card = new Card(suit, rank);
				cardSet.add(card);
			}
		}
	}

	// Mischen
	public void shuffle() {
		Collections.shuffle(cardSet);
	}

	public Card dealCard() {
		Card card = (cardSet.size() > 0) ? cardSet.remove(cardSet.size() - 1) : null;
		return card;
	}
	
	//Getter für Bot
	public ArrayList<Card> getCardSet() {
		return cardSet;
	}

}
