package herb.server.ressources.core;

public abstract class PlayerBase {
	private final String username;
	private final String authToken;
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

}
