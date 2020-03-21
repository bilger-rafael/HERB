package herb.server.ressources;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase {
	private DeckOfCards deck;

	public Round(PlayerBase[] players) {
		super(players);
		this.setTrump(randomTrump());
		this.deck = new DeckOfCards(this.getTrump());

		startRound();

		// set random player as starting player
		PlayerBase startingPlayer = this.players[0];

		// Tricks spielen
		while (!this.players[0].PlayerNoCards()) {
			// neuer Trick erstellen
			this.tricks.add(new Trick(this.players, startingPlayer));
			// Spielen
			Trick trick = (Trick) this.tricks.getLast();
			PlayerBase winner = trick.playTrick();
			// Punkte auswerten
			addTrickScore(winner);
			// set winner as starting player
			startingPlayer = winner;
		}

		endRound();
	}

	private void startRound() {
		// Aktuelle Runde für Spieler setzten
		for (int i = 0; i < this.players.length; i++) {
			((Player) this.players[i]).setRound(this);
		}

		// Hände leeren
		for (int i = 0; i < this.players.length; i++) {
			this.players[i].clearHand();
		}

		// Karten verteilen
		while (this.deck.getCardsRemaining() >= this.players.length) {
			for (int i = 0; i < this.players.length; i++) {
				this.players[i].addCardtoHand(this.deck.dealCard());
			}
		}

		// Hände sortieren
		for (int i = 0; i < this.players.length; i++) {
			this.players[i].sortHand();
		}
	}

	private void endRound() {
		// Aktuelle Runde für Spieler entfernen
		for (int i = 0; i < this.players.length; i++) {
			((Player) this.players[i]).setRound(null);
		}

		getScoreTable();
	}

	private Trump randomTrump() {
		Random rand = new Random();
		int i = rand.nextInt((6));
		return Trump.values()[i];
	}

	@Override
	protected void addTrickScore(PlayerBase winner) {
		Integer additionalScore = this.tricks.getLast().getTrickPoints();
		Integer oldScore = actualScores.get(winner);
		actualScores.put(winner, (additionalScore + oldScore));
	}

	@Override
	public Map<PlayerBase, Integer> getScoreTable() {
		Map<PlayerBase, Integer> sortedTable = new TreeMap<>();
		actualScores.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEachOrdered(x -> sortedTable.put(x.getKey(), x.getValue()));

		return sortedTable;

	}

}
