package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.PlayerBase;
import herb.client.rest.RestClient;
import herb.client.ressources.Player;

public class Player extends PlayerBase {

	public Player(String username, String authToken) {
		super(username, authToken);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(CardBase card) {
		// TODO Auto-generated method stub

	}
	
	public static PlayerBase login(String username, String password) {
		Player p = RestClient.getClient()
							 .post()
							 .uri(uriBuilder -> uriBuilder.path("/login")
									    				  .queryParam("username", username)
									    				  .queryParam("password", password)
									    				  .build())
							 .retrieve()
							 .bodyToMono(Player.class)
							 .block();

		return p;
	}

	@Override
	public void addCardtoHand(CardBase card) {
		// TODO Auto-generated method stub
		
	}
}
