package herb.server.ressources.core;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Bilger/Etter
public abstract class TrickBase<Player extends PlayerBase, Card extends CardBase> {

	@JsonIgnoreProperties({ "round", "hand" })
	private final Player[] players;
	protected Card[] playedCards;
	@JsonIgnoreProperties({ "round", "hand" })
	private final Player startingPlayer;
	@JsonIgnoreProperties({ "round", "hand" })
	private Player currentPlayer;
	@JsonIgnore
	protected Player winningPlayer;

	// protected Map<Player, CardBase> playedCards = new HashMap<Player,
	// CardBase>();

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

	public CardBase[] getPlayedCards() {
		return playedCards;
	}

	@JsonIgnore
	public Card getPlayedCard(Player p) {
		for (int i = 0; i < this.getPlayers().length; i++) {
			if (this.players[i].equals(p))
				return this.playedCards[i];
		}
		return null;
	}

}
