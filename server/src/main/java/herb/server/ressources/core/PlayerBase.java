package herb.server.ressources.core;

import herb.server.ressources.Round;

public abstract class PlayerBase {
	private final String username;
	private final String authToken;
	private Round r;
	private HandBase hand;
	
	public PlayerBase(String username, String authToken) {
		this.username = username;
		this.authToken = authToken;
	}
	
	public abstract void play(CardBase card);

	public String getUsername() {
		return username;
	}

	public String getAuthToken() {
		return authToken;
	}

	public abstract void addCardtoHand(CardBase card);
	
	public abstract boolean PlayerNoCards();
	
	public abstract void clearHand();
	
	public abstract void setCurrentRound(Round r);

}
