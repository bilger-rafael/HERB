package herb.server.ressources;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase implements Runnable{
	private DeckOfCards deck;

	public Round(PlayerBase[] players) {
		super(players);
		this.setTrump(randomTrump());
		this.deck = new DeckOfCards(this.getTrump());
		// set player 0 as starting player
		this.setCurrentStartingPlayer(this.getPlayers()[0]);
		
		Thread t = new Thread(this);
		//t.setDaemon(true);
		t.start();
	}
	
	@Override
	public void run() {		
		startRound();
		
		playTricks();

		endRound();
	}

	private void startRound() {
		// Aktuelle Runde für Spieler setzten
		for (int i = 0; i < this.getPlayers().length; i++) {
			((Player) this.getPlayers()[i]).setRound(this);
		}

		// Hände leeren
		for (int i = 0; i < this.getPlayers().length; i++) {
			this.getPlayers()[i].clearHand();
		}

		// Karten verteilen
		while (this.deck.getCardsRemaining() >= this.getPlayers().length) {
			for (int i = 0; i < this.getPlayers().length; i++) {
				this.getPlayers()[i].addCardtoHand(this.deck.dealCard());
			}
		}

		// Hände sortieren
		for (int i = 0; i < this.getPlayers().length; i++) {
			this.getPlayers()[i].sortHand();
		}
	}

	private void playTricks() {
		// Tricks spielen
		while (!this.getPlayers()[0].PlayerNoCards()) {
			// neuer Trick erstellen
			this.tricks.add(new Trick(this.getPlayers(), this.getCurrentStartingPlayer()));
			// Spielen
			Trick trick = (Trick) this.tricks.getLast();
			PlayerBase winner = trick.playTrick();
			// Punkte auswerten
			addTrickScore(winner);
			// set winner as starting player
			this.setCurrentStartingPlayer(winner);
		}
	}

	private void endRound() {
		// Aktuelle Runde für Spieler entfernen
		for (int i = 0; i < this.getPlayers().length; i++) {
			((Player) this.getPlayers()[i]).setRound(null);
		}

		getScoreTable();
		
		//TODO kill thread
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
