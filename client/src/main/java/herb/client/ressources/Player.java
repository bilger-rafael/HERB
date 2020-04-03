package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.HandBase;
import herb.client.ressources.core.PlayerBase;
import com.fasterxml.jackson.annotation.JsonCreator;

import herb.client.ressources.Player;

public class Player extends PlayerBase {

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

	@Override
	public void addCardtoHand(CardBase card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean PlayerNoCards() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clearHand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortHand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CardBase[] determinPlayableCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HandBase returnHand() {
		// TODO Auto-generated method stub
		return null;
	}

}
