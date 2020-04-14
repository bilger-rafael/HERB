package herb.client.ressources;

import java.util.Map;

import herb.client.ressources.core.PlayerBase;
import herb.client.ressources.core.RoundBase;
import herb.client.ressources.core.TrickBase;
import herb.client.ressources.core.Trump;

public class Round extends RoundBase<Player, Trick>{

	// default constructor for json deserialization
	public Round() {
		super(null);
	}
	
	public Round(Player[] players) {
		super(players);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addTrickScore(Player winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Player, Integer> getScoreTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
