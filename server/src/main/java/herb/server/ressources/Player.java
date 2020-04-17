package herb.server.ressources;

import java.util.ArrayList;
import java.util.LinkedList;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Player extends PlayerBase<Hand, Round> {
	private ArrayList<PlayListener> playListeners;

	public Player(String username, String authToken) {
		super(username, authToken);
		super.setHand(new Hand());
		this.playListeners = new ArrayList<PlayListener>();
	}

	@Override
	public void play(CardBase card) throws ExceptionBase {
		// Aus Hand entfernen
		this.getHand().play((Card) card);
		// Karte dem Trick hinzufügen

		getLastTrick().addCardtoTrick((Card) card);

		// clone array to avoid exception when listener is removed in the for loop
		ArrayList<PlayListener> listeners = new ArrayList<PlayListener>(this.playListeners);

		for (PlayListener playListener : listeners) {
			playListener.played();
		}

		this.playListeners.clear();
	}

	public void addCardtoHand(CardBase card) {
		this.getHand().addCard((Card) card);
	}

	public void clearHand() {
		this.getHand().clearCards();
	}

	public boolean PlayerNoCards() {
		if (this.getHand().getCards().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public void sortHand() {
		this.getHand().sortCards();
	}

	public void updatePlayableHand(Trick t) {
		determinPlayableCards(t);
	}

	// Gibt die Karten zurück, die gespielt werden dürfen
	private void determinPlayableCards(Trick t) {
		Trick.PlayerNode startingPlayer = t.buildCircularLinkedList();
		
		// if its not the players turn, no card is playable
		if (!this.equals(t.getCurrentPlayer())) {
			this.getHand().getCards().forEach(x -> x.setPlayable(false));
			return;
		}

		// Logik
		int numberPlayedCards = 0;
		for (int i = 0; i < t.getPlayedCards().length; i++) {
			if (t.getPlayedCards()[i] != null)
				numberPlayedCards++;
		}
		;

		switch (numberPlayedCards) {
		// Ich bin der Startspieler
		case (0):
			// if counterPlayableCards is 0, all cards will be set playable
			break;
		// 2. Spieler
		case (1):
			// Spielen kann ich Trümpfe und gleiche Farbe, falls keine alles spielbar
			for (Card c : this.getHand().getCards()) {
				if (c.getSuit() == t.getPlayedCard(startingPlayer.data).getSuit()
						|| c.getTrump().ordinal() == c.getSuit().ordinal()) {
					c.setPlayable(true);
				}
			}
			break;
		// 3.Spieler
		// gleiche Farbe wie StartSpieler oder man gewinnt (höchster Trumpf), sonst
		// alles erlaubt
		case (2):
			for (Card c : this.getHand().getCards()) {
				if (c.getSuit() == t.getPlayedCard(startingPlayer.data).getSuit()
						|| (c.compareToPlayable(t.getPlayedCard(startingPlayer.data))
								&& c.compareToPlayable(t.getPlayedCard(startingPlayer.next.data)))) {
					c.setPlayable(true);
				}
			}
			break;
		// Letzter Spieler
		// gleiche Farbe wie StartSpieler oder man gewinnt (höchster Trumpf), sonst
		// alles erlaubt
		case (3):
			for (Card c : this.getHand().getCards()) {
				if (c.getSuit() == t.getPlayedCard(startingPlayer.data).getSuit()
						|| (c.compareToPlayable(t.getPlayedCard(startingPlayer.data))
								&& c.compareToPlayable(t.getPlayedCard(startingPlayer.next.data))
								&& c.compareToPlayable(t.getPlayedCard(startingPlayer.next.next.data)))) {
					c.setPlayable(true);
				}
			}
			break;
		}

		// if no card is playable, set all cards playable
		if (this.getHand().getCards().stream().allMatch(x -> !x.isPlayable())) {
			this.getHand().getCards().forEach(x -> x.setPlayable(true));
		}

	}

	public void addPlayListener(PlayListener playListener) {
		this.playListeners.add(playListener);
	}

	private Trick getLastTrick() {
		return (Trick) ((LinkedList<Trick>) this.getRound().getTricks()).getLast();
	}
}