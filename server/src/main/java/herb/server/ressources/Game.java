package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;

//Etter
public class Game extends GameBase<Lobby, Player>{

	public Game(Lobby lobby, Player[] players) {
		super(UUID.randomUUID(), lobby, players);
		startRound();
		
	}

	@Override
	public RoundBase startRound() {
		Round r = new Round(this);
		this.rounds.add(r);		
		return r;
	}
	
}
