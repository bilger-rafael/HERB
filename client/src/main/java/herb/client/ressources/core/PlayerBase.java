package herb.client.ressources.core;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class PlayerBase {
	private final String username;
	private final String password;
	private RoundBase round;
	protected HandBase hand;
	
	@JsonCreator
	public PlayerBase(String username) {
		this.username = username;
		this.password = "";
	}
	
	public PlayerBase(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public abstract void play(CardBase card);

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setRound(RoundBase round) {
		this.round = round;
	}

	public abstract void addCardtoHand(CardBase card);
	
	public abstract boolean PlayerNoCards();
	
	public abstract void clearHand();
	
	public abstract void sortHand();
	
	public abstract CardBase[] getPlayableCards();

	public RoundBase getRound() {
		return round;
	}

}
