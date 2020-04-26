package herb.server.ressources;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import herb.server.DataStore_Repository;
import herb.server.ressources.Trick.PlayerNode;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase<Player, Trick> implements Runnable {
	private DeckOfCards deck;

	public Round(Player[] players) {
		super(players);
		this.setTrump(randomTrump());
		this.deck = new DeckOfCards(this.getTrump());

		this.setCurrentStartingPlayer(this.getPlayers()[new Random().nextInt(4)]);

		Thread t = new Thread(this);
		// t.setDaemon(true);
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

		// wait for startingPlayer to choose trump
		while (this.getTrump() == null) {
			try {
				Thread.sleep(1000);
				// this.wait(); //is it possible to wait on Object Round?
			} catch (InterruptedException e) {
			}
		}

	}

	private void playTricks() {
		// Tricks spielen
		while (!this.getPlayers()[0].PlayerNoCards()) {
			// neuer Trick erstellen
			Trick trick = new Trick(this.getPlayers(), this.getCurrentStartingPlayer());
			this.getTricks().add(trick);
			// Spielen
			Player winner = trick.playTrick();
			// set winner as starting player
			this.setCurrentStartingPlayer(winner);
		}
	}

	private void endRound() {
		// Aktuelle Runde für Spieler entfernen
		// for (int i = 0; i < this.getPlayers().length; i++) {
		// this.getPlayers()[i].setRound(null);
		// }

		this.setScores(determinScores());
		determinRoundWinner();

		// TODO kill thread
	}

	private Trump randomTrump() {
		Random rand = new Random();
		int i = rand.nextInt((6));
		return Trump.values()[i];
	}
	
	public void determinRoundWinner() {
		int playerposition = 0;
		int score = 0;
		// meiste Punkte ermitteln
		for (int i = 0; i < this.getScores().length; i++) {
			if(this.getScores()[i]>score) {
				score = this.getScores()[i];
				playerposition = i;
			}
		}
		// Auf DB schreiben
		DataStore_Repository.getDB().addPlayertoHighScore(this.getPlayers()[playerposition].toString(), score);
		
		//Falls Gleichstand besteht
		for (int i = 0; i < this.getScores().length; i++) {
			if(this.getScores()[i]==score) {
				DataStore_Repository.getDB().addPlayertoHighScore(this.getPlayers()[i].toString(), score);
			}
		}
		
	}
	
	//Bilger
	private Integer[] determinScores() {
		Integer[] scores = new Integer[4];
		for (int i = 0; i < this.getPlayers().length; i++) {
			Player p = this.getPlayers()[i];
			scores[i] = this.getTricks().stream().mapToInt(x -> x.getPlayedCard(p).getPoints()).sum();
			// add 5 points for winning last trick
			if (p.equals(this.getTricks().getLast().getWinner()))
				scores[i] += 5;
		}

		return scores;
	}

}
