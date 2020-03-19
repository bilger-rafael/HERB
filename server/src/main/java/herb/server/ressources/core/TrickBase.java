package herb.server.ressources.core;

import java.util.Map;

public abstract class TrickBase {
	protected PlayerBase[] players;
	protected Map<PlayerBase,CardBase> playedCards;
	protected PlayerBase currentplayer;
	protected PlayerBase startingPlayer;
	
	public TrickBase(PlayerBase[] players, PlayerBase startingPlayer) {
		this.players = players;
		this.currentplayer= startingPlayer;
		this.startingPlayer = startingPlayer;
	}
	
	public abstract PlayerBase getWinner();
	public abstract PlayerBase getNextPlayer();
	public abstract void addCardtoTrick(CardBase c);
	protected abstract void clearTrick();
	protected abstract int getTrickPoints();
}
