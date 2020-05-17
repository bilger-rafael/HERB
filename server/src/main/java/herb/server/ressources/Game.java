package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;

//Etter
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
		while (r.rematchDecisions.size() < 4 && !r.rematchDecisions.containsValue(false)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		if (r.rematchDecisions.containsValue(false))
			endGame();
		else
			playRound();
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
		this.getLobby().removeAllPlayer();
		this.setOver(true);
	}

}
