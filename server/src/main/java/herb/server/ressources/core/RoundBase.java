package herb.server.ressources.core;

import java.util.HashMap;
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
	private Integer[] scores;
	

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

	public Integer[] getScores() {
		return scores;
	}

	public void setScores(Integer[] scores) {
		this.scores = scores;
	}
	

	
}
