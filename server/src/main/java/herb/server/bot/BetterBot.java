package herb.server.bot;

import java.util.ArrayList;
import java.util.Random;

import herb.server.ressources.Card;

public class BetterBot extends BotBase {

	// Sucht die Karte aus die gespielt werden soll
	@Override
	public Card determinBestCard() {
		Card bestCard = null;
		Random rand = new Random();
		int randInt;
		this.updatePlayableHand(super.getRound().getTricks().getLast());

		// Logik des Bots
		switch (this.getNumberPlayedCards()) {
		// Startspieler Random ausspielen
		case (0):
			randInt = rand.nextInt(this.getHand().getCards().size());
			bestCard = this.getHand().getCards().get(randInt);
			break;
		// 2.Spieler, 30% versuch zu stechen und 70% verwerfen oder Stich mehr als 10
		// Punkte
		case (1):
			randInt = rand.nextInt(1);
			if (randInt >= 0.3 || this.getRound().getTricks().getLast().getTrickPoints() > 10) {
				if (returnLowCostPlayableCard() != null) {
					bestCard = returnLowCostPlayableCard();
				} else {
					bestCard = returnLowLoserPlayableCard();
				}
			} else {
				bestCard = returnLowLoserPlayableCard();
			}
			break;

		// 3. Spieler, 50% versuch zu stechen und 50% verwerfen oder Stich mehr als 10
		// Punkte
		case (2):
			randInt = rand.nextInt(1);
			if (randInt >= 0.5 || this.getRound().getTricks().getLast().getTrickPoints() > 10) {
				if (returnLowCostPlayableCard() != null) {
					bestCard = returnLowCostPlayableCard();
				} else {
					bestCard = returnLowLoserPlayableCard();
				}
			} else {
				bestCard = returnLowLoserPlayableCard();
			}
			break;

		// Versuch zu stechen, falls Stich mehr als 10 Punkte
		case (3):
			if (this.getRound().getTricks().getLast().getTrickPoints() > 10) {
				if (returnLowCostPlayableCard() != null) {
					bestCard = returnLowCostPlayableCard();
				} else {
					bestCard = returnLowLoserPlayableCard();
				}
			}
			break;

		}

		if (this.equals(this.getRound().getCurrentStartingPlayer())) {

			// TODO or dont do ifelse

		} else {

			returnLowLoserPlayableCard();
			bestCard = returnLowCostPlayableCard();

		}
		return bestCard;
	}

	// Gibt die tiefste spielbare Karte zurück, die gewinnt
	private Card returnLowCostPlayableCard() {
		ArrayList<Card> winnerCards = returnWinnerHandCards();
		Card temp = null;

		int i = 100;
		for (Card c : winnerCards) {
			if (c.getGameValue() < i) {
				i = c.getGameValue();
				temp = c;
			}
		}
		return temp;
	}

	// Gibt die tiefste spielbare Karte zurück, die verliert (Abwerfen)
	public Card returnLowLoserPlayableCard() {
		ArrayList<Card> loserCards = returnLoserHandCards();
		Card temp = null;

		int i = 100;
		for (Card c : loserCards) {
			if (c.getGameValue() < i) {
				i = c.getGameValue();
				temp = c;
			}
		}
		return temp;
	}

	// Alle Spielbaren Karten auswerten, welche gewinnen würden gegen Trick
	private ArrayList<Card> returnWinnerHandCards() {
		ArrayList<Card> winnerHandCards = new ArrayList<>();
		for (Card c : this.getPlayableCards()) {
			Card[] playedCardsFromTrick = (herb.server.ressources.Card[]) this.getRound().getTricks().getLast()
					.getPlayedCards();
			// Alle Karten vom Trick
			Boolean winAgainstTrick = false;
			for (Card cT : playedCardsFromTrick) {
				if (c.compareToWinnerCard(cT)) {
					// Diese Karte gewinnt
					winAgainstTrick = true;
				} else {
					// nichts
				}
			}
			if (winAgainstTrick) {
				winnerHandCards.add(c);
			}
		}
		return winnerHandCards;
	}

	// Alle Spielbaren Karten auswerten, welche verlieren würden gegen Trick
	private ArrayList<Card> returnLoserHandCards() {
		ArrayList<Card> loserHandCards = new ArrayList<>();
		for (Card c : this.getPlayableCards()) {
			Card[] playedCardsFromTrick = (herb.server.ressources.Card[]) this.getRound().getTricks().getLast()
					.getPlayedCards();
			// Alle Karten vom Trick
			Boolean winAgainstTrick = false;
			for (Card cT : playedCardsFromTrick) {
				if (c.compareToWinnerCard(cT)) {
					// Diese Karte gewinnt
					winAgainstTrick = true;
				} else {
					// nichts
				}
			}
			if (!winAgainstTrick) {
				loserHandCards.add(c);
			}
		}
		return loserHandCards;
	}

}
