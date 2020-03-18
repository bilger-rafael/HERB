package herb.server.ressources.core;

import java.util.Map;

public abstract class TrickBase {
	protected PlayerBase[] players;
	private Map<PlayerBase,CardBase> playedCards;
	
	public abstract PlayerBase getWinner();
	public abstract PlayerBase getNextPlayer();
	public abstract void addCardtoTrick(CardBase c);
	protected abstract void clearTrick();
}
