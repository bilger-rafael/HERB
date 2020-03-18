package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.HandBase;
import herb.server.ressources.core.PlayerBase;

//Etter
public class Player extends PlayerBase{
	private HandBase hand;
	

	public Player(String username, String authToken) {
		super(username, authToken);
		this.hand = new Hand();
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

	@Override
	public void addCardtoHand(CardBase card) {
		this.hand.addCard(card);
		
	}
	
	public void clearHand() {
		this.hand.clearCards();
	}


}
