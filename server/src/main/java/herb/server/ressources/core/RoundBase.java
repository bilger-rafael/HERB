package herb.server.ressources.core;

import java.util.LinkedList;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Etter
public abstract class RoundBase <Player extends PlayerBase, Trick extends TrickBase> {
	private LinkedList<Trick> tricks = new LinkedList<Trick>();
	private Trump trump;
	@JsonIgnoreProperties({ "round", "hand" })
	private Player[] players;
	private Map<Player,Integer> actualScores;
	
	@JsonIgnore
	public Map<Player, Integer> getActualScores() {
		return actualScores;
	}

	public void setActualScores(Map<Player, Integer> actualScores) {
		this.actualScores = actualScores;
	}

	public RoundBase (Player[] players) {
		this.players=players;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	private Player currentStartingPlayer;

	public void setCurrentStartingPlayer(Player currentStartingPlayer) {
		this.currentStartingPlayer = currentStartingPlayer;
	}


	protected abstract void addTrickScore(Player winner);
	
	@JsonIgnore
	public abstract Map<Player, Integer> getScoreTable();
	
	public LinkedList<Trick> getTricks(){
		return tricks;
	}
	
	public Trump getTrump() {
		return trump;
	}

	public void setTrump(Trump trump) {
		this.trump = trump;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Player getCurrentStartingPlayer() {
		return currentStartingPlayer;
	}
	
}
