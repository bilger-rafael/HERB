package herb.server.ressources.core;

import java.util.LinkedList;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Etter
public abstract class RoundBase {
	private LinkedList<TrickBase> tricks = new LinkedList<TrickBase>();
	private Trump trump;
	private PlayerBase[] players = new PlayerBase[4];
	private Map<PlayerBase,Integer> actualScores;
	
	public Map<PlayerBase, Integer> getActualScores() {
		return actualScores;
	}

	public void setActualScores(Map<PlayerBase, Integer> actualScores) {
		this.actualScores = actualScores;
	}

	public RoundBase (PlayerBase[] players) {
		this.players=players;
	}
	
	public void setPlayers(PlayerBase[] players) {
		this.players = players;
	}

	private PlayerBase currentStartingPlayer;

	public void setCurrentStartingPlayer(PlayerBase currentStartingPlayer) {
		this.currentStartingPlayer = currentStartingPlayer;
	}


	protected abstract void addTrickScore(PlayerBase winner);
	
	@JsonIgnore
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
