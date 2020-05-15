package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;

//Etter
public class Game extends GameBase<Lobby, Player> implements Runnable{

	public Game(Lobby lobby, Player[] players) {
		super(UUID.randomUUID(), lobby, players);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public RoundBase startRound() {
		Round r = new Round(this);
		this.rounds.add(r);		
		return r;
	}
	
	public void endRound() {
		//TODO wait for decision of users (next round or quit)
		/*
		while (!this.trumpChoosen) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		*/
	}

	@Override
	public void run() {
		startRound();
	}
	
}
