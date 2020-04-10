package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;

//Etter
public class Game extends GameBase{

	public Game(PlayerBase[] players) {
		super(UUID.randomUUID(), players);
		startRound();
		
	}

	@Override
	public RoundBase startRound() {
		Round r = new Round(this.players);
		this.rounds.add(r);		
		return r;
	}


	
}
