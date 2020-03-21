package herb.server.ressources.core;

public abstract class PlayerBase {
	private final String username;
	private final String authToken;
	private RoundBase round;
	protected HandBase hand;
	
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
	
	public abstract void sortHand();
	
	public abstract PlayerBase getCurrentStartingPlayer();
	
	public abstract CardBase[] getPlayableCards();

	public RoundBase getRound() {
		return round;
	}

	public void setRound(RoundBase round) {
		this.round = round;
	}

}
