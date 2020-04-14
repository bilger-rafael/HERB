package herb.client.ressources.core;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Bilger/Etter
public abstract class TrickBase <Player extends PlayerBase> {
	
	@JsonIgnoreProperties({ "round", "hand" })
	private final Player[] players;
	@JsonIgnoreProperties({ "round", "hand" })
	private final Player startingPlayer;
	@JsonIgnoreProperties({ "round", "hand" })
	private Player currentPlayer;
	@JsonIgnore
	protected Player winningPlayer;
	@JsonIgnore
	protected Map<Player, CardBase> playedCards = new HashMap<Player, CardBase>();
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player p) {
		this.currentPlayer = p;
	}

	@JsonIgnore
	public Player getWinningPlayer() {
		return winningPlayer;
	}

	public void setWinningPlayer(Player winningPlayer) {
		this.winningPlayer = winningPlayer;
	}

	public void setPlayedCards(Map<Player, CardBase> playedCards) {
		this.playedCards = playedCards;
	}

	public TrickBase(Player[] players, Player startingPlayer) {
		this.players = players;
		this.startingPlayer = this.currentPlayer = startingPlayer;
	}
	
	@JsonIgnore
	public Player[] getPlayers() {
		return players;
	}
	
	@JsonIgnore
	public Player getStartingPlayer() {
		return startingPlayer;
	}
	
	public abstract Player determinNextPlayer();

}
