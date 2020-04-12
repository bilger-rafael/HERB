package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.HandBase;
import herb.client.ressources.core.PlayerBase;
import com.fasterxml.jackson.annotation.JsonCreator;

import herb.client.ressources.Player;

public class Player extends PlayerBase<Hand> {

	// default constructor for json deserialization
	public Player() {
		super("", "");
	}
	
	public Player(String username, String authToken) {
		super(username, authToken);
	}

	@Override
	public void play(CardBase card) {
		// TODO Auto-generated method stub
		
	}

}
