package herb.client.ressources.core;

import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Etter
public abstract class RoundBase<Game extends GameBase, Player extends PlayerBase, Trick extends TrickBase> {
	private LinkedList<Trick> tricks = new LinkedList<Trick>();
	private Trump trump;
	@JsonIgnoreProperties({ "players", "rounds" })
	private Game game;
	@JsonIgnoreProperties({ "round", "hand" })
	private Player[] players;
	private Integer[] scores;
	@JsonIgnoreProperties({ "round", "hand" })
	private Player currentStartingPlayer;
	private Boolean rematch;

	public RoundBase(Game game, Player[] players) {
		this.game = game;
		this.players = players;
	}

	public Game getGame() {
		return game;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public void setCurrentStartingPlayer(Player currentStartingPlayer) {
		this.currentStartingPlayer = currentStartingPlayer;
	}
	
	public Player getCurrentStartingPlayer() {
		return currentStartingPlayer;
	}

	public LinkedList<Trick> getTricks() {
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

	public Integer[] getScores() {
		return scores;
	}

	public void setScores(Integer[] scores) {
		this.scores = scores;
	}

	public Boolean getRematch() {
		return rematch;
	}

	public void setRematch(Boolean rematch) {
		this.rematch = rematch;
	}
}
