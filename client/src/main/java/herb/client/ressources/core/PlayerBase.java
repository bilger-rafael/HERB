package herb.client.ressources.core;

public abstract class PlayerBase {
	private String username;
	private String authToken;
	private HandBase hand;
	
	public abstract void play(CardBase card);

}
