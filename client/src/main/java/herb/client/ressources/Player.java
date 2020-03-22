package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.PlayerBase;
import herb.client.ressources.core.RoundBase;
import herb.client.rest.RestClient;

import java.util.Map;

import herb.client.ressources.Player;

public class Player extends PlayerBase {

	public Player(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
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
	public CardBase[] getPlayableCards() {
		// TODO Auto-generated method stub
		return null;
	}


}
