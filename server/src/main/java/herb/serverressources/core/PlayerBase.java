package herb.serverressources.core;

public abstract class PlayerBase {
	private String username;
	private String authToken;
	private HandBase hand;
	
	public abstract void play(CardBase card);

}
