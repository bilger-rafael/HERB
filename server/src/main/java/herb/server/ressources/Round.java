package herb.server.ressources;

import java.util.HashMap;
import java.util.Random;

import herb.server.DataStore_Repository;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase<Game, Player, Trick> {
	public HashMap<String, Boolean> rematchDecisions = new HashMap<String, Boolean>();
	private DeckOfCards deck;
	private boolean trumpChoosen;

	public Round(Game game) {
		super(game, game.getPlayers());
		this.deck = new DeckOfCards();
		this.setCurrentStartingPlayer(this.getPlayers()[new Random().nextInt(4)]);

		/*
		 * Thread t = new Thread(this); t.start();
		 */
	}

	/*
	 * @Override public void run() { startRound();
	 * 
	 * playTricks();
	 * 
	 * endRound(); }
	 */

	public void playRound() {
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

		// Bilger wait for startingPlayer to choose trump
		this.getCurrentStartingPlayer().setTrumpListener(() -> {
			this.trumpChoosen = true;
		});

		while (!this.trumpChoosen) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		// Trumpf setzen bei den Karten
		for (int i = 0; i < this.getPlayers().length; i++) {
			for (Card c : this.getPlayers()[i].getHand().getCards()) {
				c.setTrump(this.getTrump());
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
			// Winner als StartingPlayer setzen
			this.setCurrentStartingPlayer(winner);
		}
	}

	private void endRound() {
		for (int i = 0; i < this.getPlayers().length; i++) {
			Player p = this.getPlayers()[i];
			if (p == null)
				continue;
			p.setRematchListener((Boolean rematch) -> {
				this.rematchDecisions.put(p.getUsername(), rematch);
			});
		}

		this.setScores(determinScores());

		determinRoundWinner();

		for (int i = 0; i < this.getScores().length; i++) {
			DataStore_Repository.getDB().addPlayertoHighScore(this.getPlayers()[i].toString(), this.getScores()[i]);
		}
	}

	// Random Trump wählen
	private Trump randomTrump() {
		Random rand = new Random();
		int i = rand.nextInt((6));
		return Trump.values()[i];
	}

	// Resultate ermitteln
	public void determinRoundWinner() {
		int playerposition = 0;
		int score = 0;
		// meiste Punkte ermitteln
		for (int i = 0; i < this.getScores().length; i++) {
			if (this.getScores()[i] > score) {
				score = this.getScores()[i];
				playerposition = i;
			}
		}
	}

	// Bilger
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
