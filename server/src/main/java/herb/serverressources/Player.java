package herb.serverressources;

import herb.serverressources.core.CardBase;
import herb.serverressources.core.PlayerBase;

public class Player extends PlayerBase{

	public Player(String username, String authToken) {
		super(username, authToken);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(CardBase card) {
		// TODO Auto-generated method stub
		
	}
	
	public static Player login(String username, String password) {
		return new Player(username, "TOKEN1");
	}

}
