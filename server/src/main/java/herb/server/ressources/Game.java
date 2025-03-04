package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;

//Bilger
public class Game extends GameBase<Lobby, Round, Player> implements Runnable {

	public Game(Lobby lobby, Player[] players) {
		super(UUID.randomUUID(), lobby, players);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public Round startRound() {
		Round r = new Round(this);
		this.rounds.add(r);
		return r;
	}

	
	public void endRound(Round r) {
		// add up scores
		if (this.scores == null)
			this.scores = new Integer[this.getPlayers().length];
		for (int i = 0; i < this.getPlayers().length; i++) {
			if (this.scores[i] == null)
				this.scores[i] = 0;
			this.scores[i] += r.getScores()[i];
		}

		// wait for rematch decisions
		while (r.rematchDecisions.size() < this.getPlayers().length && !r.rematchDecisions.containsValue(false)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		if (r.rematchDecisions.containsValue(false)) {
			r.setRematch(false);
			endGame();
		} else {
			r.setRematch(true);
			// make sure, the client can use the players current round to determin if rematch is set
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			playRound();
		}
	}

	public void playRound() {
		Round r = startRound();
		r.playRound();
		endRound(r);
	}

	@Override
	public void run() {
		playRound();
	}

	public void endGame() {
		// remove round from player
		for (int i = 0; i < this.getPlayers().length; i++) {
			if (this.getPlayers()[i] != null)
				this.getPlayers()[i].setRound(null);
		}

		this.getLobby().removeAllPlayer();
		this.setOver(true);
	}

}
