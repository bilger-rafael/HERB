package herb.server.ressources.core;

import java.util.Map;



public abstract class TrickBase {
	protected PlayerBase[] players;
	protected Map<PlayerBase,CardBase> playedCards;
	protected PlayerBase currentPlayer;
	protected PlayerBase startingPlayer;
	protected PlayerBase winningPlayer;
	
	public TrickBase(PlayerBase[] players, PlayerBase startingPlayer) {
		this.players = players;
		this.currentPlayer= startingPlayer;
		this.startingPlayer = startingPlayer;
	}
	
	public abstract PlayerBase getWinner();
	public abstract PlayerBase getNextPlayer();
	public abstract PlayerBase getPrivousPlayer();
	public abstract PlayerBase setNextCurrentPlayer();
	public abstract void addCardtoTrick(CardBase c);
	protected abstract void clearTrick();
	public abstract int getTrickPoints();
	public abstract PlayerBase getStaringPlayer();
}
