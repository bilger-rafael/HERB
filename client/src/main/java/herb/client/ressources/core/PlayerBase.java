package herb.client.ressources.core;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class PlayerBase {
	private final String username;
	private final String authToken;
	private RoundBase round;
	protected HandBase hand;
	
	@JsonCreator	
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
	
	public void setRound(RoundBase round) {
		this.round = round;
	}

	public abstract void addCardtoHand(CardBase card);
	
	public abstract boolean PlayerNoCards();
	
	public abstract void clearHand();
	
	public abstract void sortHand();
	
	public abstract CardBase[] determinPlayableCards();

	public RoundBase getRound() {
		return round;
	}

}
