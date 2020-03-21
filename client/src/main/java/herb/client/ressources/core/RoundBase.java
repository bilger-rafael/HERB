package herb.client.ressources.core;

import java.util.Map;

//Etter
public abstract class RoundBase {
	protected TrickBase[] tricks;
	protected Trump currentTrump;
	protected PlayerBase[] players = new PlayerBase[4];
	protected Map<PlayerBase,Integer> actualScores;
	
	public RoundBase (PlayerBase[] players) {
		this.players=players;
	}
	
	public abstract TrickBase getCurrentTrick();
	
	protected abstract void genTrump();
	
	public abstract Trump getCurrentTrump();
	
	protected abstract void addTrickScore(PlayerBase winner);
	
	public abstract Map<PlayerBase, Integer> getScoreTable();
	
}
