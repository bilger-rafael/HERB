package herb.client.ressources.core;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Bilger/Etter
public abstract class TrickBase <Player extends PlayerBase> {

	protected class PlayerNode {
		@JsonIgnoreProperties({ "round", "hand" })
		public Player data;
		public PlayerNode next;

		public PlayerNode(Player players) {
			this.data = players;
		}
	}
	
	@JsonIgnoreProperties({ "round", "hand" })
	private final Player[] players;
	@JsonIgnoreProperties({ "round", "hand" })
	private final Player startingPlayer;
	@JsonIgnoreProperties({ "next" })
	private PlayerNode currentPlayer;
	
	
	public PlayerNode getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PlayerNode currentPlayer) {
		this.currentPlayer = currentPlayer;
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
	
	@JsonIgnore
	protected Map<Player, CardBase> playedCards;
	@JsonIgnore
	protected Player winningPlayer;

	public TrickBase(Player[] players, Player startingPlayer) {
		this.players = players;
		this.startingPlayer = startingPlayer;

		PlayerNode playerNodeNew;
		PlayerNode playerNodePrevious = null;
		for (int i = 0; i < this.players.length; i++) {
			playerNodeNew = new PlayerNode(this.players[i]);

			if (this.players[i].equals(startingPlayer)) {
				currentPlayer = playerNodeNew;
			}

			if (playerNodePrevious != null) {
				playerNodePrevious.next = playerNodeNew;
			}

			playerNodePrevious = playerNodeNew;
		}
	}
	
	@JsonIgnore
	public Player[] getPlayers() {
		return players;
	}
	
	@JsonIgnore
	public Player getStartingPlayer() {
		return startingPlayer;
	}

	@JsonIgnore
	public abstract Player getWinner();

	@JsonIgnore
	public abstract Player getNextPlayer(Player p);

	@JsonIgnore
	public abstract Player getPrivousPlayer(Player p);

	public abstract void addCardtoTrick(CardBase c);

	protected abstract void clearTrick();

	@JsonIgnore
	public abstract int getTrickPoints();

	@JsonIgnore
	public abstract Player getStaringPlayer();

	@JsonIgnore
	public abstract Map<Player, CardBase> getPlayedCards();

	@JsonIgnore
	public abstract CardBase getPlayedCard(Player p);

}
