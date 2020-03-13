package herb.server.ressources;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;

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
