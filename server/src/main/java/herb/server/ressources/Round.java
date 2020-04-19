package herb.server.ressources;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import herb.server.DataStore_Repository;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase<Player, Trick> implements Runnable{
	private DeckOfCards deck;

	public Round(Player[] players) {
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
			this.getTricks().add(new Trick(this.getPlayers(), this.getCurrentStartingPlayer()));
			// Spielen
			Trick trick = (Trick) this.getTricks().getLast();
			Player winner = (Player) trick.playTrick();
			// Punkte auswerten
			// addTrickScore(winner);
			// set winner as starting player
			this.setCurrentStartingPlayer(winner);
		}
	}
	
	//Gibt eine Map mit den Spielern und den Spielständen zurück
	private Map<Player, Integer> endRound() {
		// Aktuelle Runde für Spieler entfernen
		for (int i = 0; i < this.getPlayers().length; i++) {
			this.getPlayers()[i].setRound(null);
		}
		
		Map<Player, Integer> temp = getScoreTable();
		return temp;
		
		//TODO kill thread
	}

	private Trump randomTrump() {
		Random rand = new Random();
		int i = rand.nextInt((6));
		return Trump.values()[i];
	}

	@Override
	protected void addTrickScore(Player winner) {
		Integer additionalScore = this.getTricks().getLast().getTrickPoints();
		Integer oldScore = getActualScores().get(winner);
		getActualScores().put(winner, (additionalScore + oldScore));
	}

	@Override
	public Map<Player, Integer> getScoreTable() {
		Map<Player, Integer> sortedTable = new TreeMap<>();
		getActualScores().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEachOrdered(x -> sortedTable.put(x.getKey(), x.getValue()));

		return sortedTable;

	}

}
