package herb.client.ressources.core;

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
	@JsonIgnoreProperties({ "round", "hand" })
	protected Player winningPlayer;

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player p) {
		this.currentPlayer = p;
	}

	public Player getWinningPlayer() {
		return winningPlayer;
	}

	public void setWinningPlayer(Player winningPlayer) {
		this.winningPlayer = winningPlayer;
	}

	public TrickBase(Player[] players, Player startingPlayer) {
		this.players = players;
		this.startingPlayer = startingPlayer;
	}

	@JsonIgnore
	public Player[] getPlayers() {
		return players;
	}

	public Player getStartingPlayer() {
		return startingPlayer;
	}

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
