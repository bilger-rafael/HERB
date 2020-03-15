package herb.server.ressources;

import java.util.UUID;

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
	
	public static PlayerBase login(String username, String password) {
		//TODO save player instance 
		return new Player(username, UUID.randomUUID().toString());
	}

}
