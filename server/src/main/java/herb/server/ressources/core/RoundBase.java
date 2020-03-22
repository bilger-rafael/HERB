package herb.server.ressources.core;

import java.util.LinkedList;
import java.util.Map;

import herb.server.ressources.Player;

//Etter
public abstract class RoundBase {
	protected LinkedList<TrickBase> tricks = new LinkedList<TrickBase>();
	private Trump trump;
	protected PlayerBase[] players = new PlayerBase[4];
	protected PlayerBase currentStartingPlayer;


	protected Map<PlayerBase,Integer> actualScores;
	
	public RoundBase (PlayerBase[] players) {
		this.players=players;
	}
	
	protected abstract void addTrickScore(PlayerBase winner);
	
	public abstract Map<PlayerBase, Integer> getScoreTable();
	
	public LinkedList<TrickBase> getTricks(){
		return tricks;
	}
	
	public Trump getTrump() {
		return trump;
	}

	public void setTrump(Trump trump) {
		this.trump = trump;
	}
	
	public PlayerBase[] getPlayers() {
		return players;
	}
	
	public PlayerBase getCurrentStartingPlayer() {
		return currentStartingPlayer;
	}
	
}
