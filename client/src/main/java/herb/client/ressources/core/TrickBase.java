package herb.client.ressources.core;

import java.util.Map;

public abstract class TrickBase {

	protected class PlayerNode {
		public PlayerBase data;
		public PlayerNode next;

		public PlayerNode(PlayerBase players) {
			this.data = players;
		}
	}

	private final PlayerBase[] players;
	private final PlayerBase startingPlayer;
	protected PlayerNode currentPlayer;
	protected Map<PlayerBase, CardBase> playedCards;
	protected PlayerBase winningPlayer;

	public TrickBase(PlayerBase[] players, PlayerBase startingPlayer) {
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
	
	public PlayerBase[] getPlayers() {
		return players;
	}
	
	public PlayerBase getStartingPlayer() {
		return startingPlayer;
	}

	public abstract PlayerBase getWinner();

	public abstract PlayerBase getNextPlayer(PlayerBase p);

	public abstract PlayerBase getPrivousPlayer(PlayerBase p);

	protected abstract PlayerBase setNextCurrentPlayer();

	public abstract void addCardtoTrick(CardBase c);

	protected abstract void clearTrick();

	public abstract int getTrickPoints();

	public abstract PlayerBase getStaringPlayer();

	public abstract Map<PlayerBase, CardBase> getPlayedCards();

	public abstract CardBase getPlayedCard(PlayerBase p);

}
