package herb.serverressources.core;

import java.util.Map;

public abstract class TrickBase {

	private Map<PlayerBase,CardBase> playedCards;
	
	public abstract PlayerBase getWinner();
	public abstract PlayerBase getNextPlayer();
}
