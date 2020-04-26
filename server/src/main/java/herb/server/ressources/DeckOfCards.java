package herb.server.ressources;

import java.util.ArrayList;
import java.util.Collections;

import herb.server.ressources.core.Rank;
import herb.server.ressources.core.Suit;
import javafx.beans.property.SimpleIntegerProperty;

//Etter (Idee vom Pokergame, SE HS19)
public class DeckOfCards {
	private final ArrayList<Card> cardSet = new ArrayList<>();
	private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();

	// Erstellen und mischen
	public DeckOfCards() {
		initializeCards();
		shuffle();
	}

	// Hilfe für die verbleibenden Karten (nur für andere Spielvarianten)
	public SimpleIntegerProperty getCardsRemainingProperty() {
		return cardsRemaining;
	}

	public int getCardsRemaining() {
		return cardsRemaining.get();
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
		cardsRemaining.setValue(cardSet.size());
	}

	public Card dealCard() {
		Card card = (cardSet.size() > 0) ? cardSet.remove(cardSet.size() - 1) : null;
		cardsRemaining.setValue(cardSet.size());
		return card;
	}

}
