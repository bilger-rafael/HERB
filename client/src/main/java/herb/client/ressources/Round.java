package herb.client.ressources;

import java.util.Map;

import herb.client.ressources.core.PlayerBase;
import herb.client.ressources.core.RoundBase;
import herb.client.ressources.core.TrickBase;

public class Round extends RoundBase{

	public Round(PlayerBase[] players) {
		super(players);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TrickBase getCurrentTrick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void genTrump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Trump getCurrentTrump() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addTrickScore(PlayerBase winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<PlayerBase, Integer> getScoreTable() {
		// TODO Auto-generated method stub
		return null;
	}


}
