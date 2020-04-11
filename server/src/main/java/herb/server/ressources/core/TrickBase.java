package herb.server.ressources.core;

import java.util.Map;

//Bilger/Etter
public abstract class TrickBase <Player extends PlayerBase> {

	protected class PlayerNode {
		public Player data;
		public PlayerNode next;

		public PlayerNode(Player players) {
			this.data = players;
		}
	}

	private final Player[] players;
	private final Player startingPlayer;
	private PlayerNode currentPlayer;
	
	
	public PlayerNode getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PlayerNode currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getWinningPlayer() {
		return winningPlayer;
	}

	public void setWinningPlayer(Player winningPlayer) {
		this.winningPlayer = winningPlayer;
	}

	public void setPlayedCards(Map<Player, CardBase> playedCards) {
		this.playedCards = playedCards;
	}

	protected Map<Player, CardBase> playedCards;
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
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Player getStartingPlayer() {
		return startingPlayer;
	}

	public abstract Player getWinner();

	public abstract Player getNextPlayer(Player p);

	public abstract Player getPrivousPlayer(Player p);

	public abstract void addCardtoTrick(CardBase c);

	protected abstract void clearTrick();

	public abstract int getTrickPoints();

	public abstract Player getStaringPlayer();

	public abstract Map<Player, CardBase> getPlayedCards();

	public abstract CardBase getPlayedCard(Player p);

}
