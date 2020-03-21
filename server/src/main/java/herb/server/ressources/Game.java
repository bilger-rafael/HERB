package herb.server.ressources;

import java.util.ArrayList;
import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Game extends GameBase{
	protected PlayerBase[] players = new PlayerBase[4];
	protected ArrayList<RoundBase> rounds = new ArrayList<RoundBase>();

	public Game(PlayerBase[] players) {
		super(UUID.randomUUID(), players);
		startRound();
	}

	@Override
	public RoundBase startRound() {
		Round r = new Round(this.players);
		this.rounds.add(r);
		for(int i =0; i<this.players.length;i++) {
			this.players[i].setCurrentRound(r);	
		}
		
		return r;
	}



}
