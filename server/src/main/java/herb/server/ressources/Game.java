package herb.server.ressources;

import java.util.ArrayList;
import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;

//Etter
public class Game extends GameBase{
	protected PlayerBase[] players = new PlayerBase[4];
	protected ArrayList<RoundBase> rounds = new ArrayList<RoundBase>();

	public Game(UUID uuid, PlayerBase[] players) {
		super(uuid, players);
		startRound();
	}

	@Override
	public RoundBase startRound() {
		Round r = new Round(this.players);
		this.rounds.add(r);
		return r;
	}

}
