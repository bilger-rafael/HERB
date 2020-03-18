package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.RoundBase;

//Etter
public class Game extends GameBase{

	public Game(UUID uuid) {
		super(uuid);
		startRound();
	}

	@Override
	public RoundBase startRound() {
		Round r = new Round();
		this.rounds.add(r);
		return r;
	}

}
