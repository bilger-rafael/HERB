package herb.client.ressources.core;

import java.util.Map;



public abstract class TrickBase {

	private Map<PlayerBase,CardBase> playedCards;
	
	public abstract PlayerBase getWinner();
	public abstract PlayerBase getNextPlayer();
	public abstract PlayerBase getPriousPlayer();
	public abstract PlayerBase setNextCurrentPlayer();
	public abstract void addCardtoTrick(PlayerBase p, CardBase c);
	protected abstract void clearTrick();
}
