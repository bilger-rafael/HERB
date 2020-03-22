package herb.client.ressources;

import java.util.UUID;

import herb.client.ressources.core.GameBase;
import herb.client.ressources.core.LobbyBase;
import herb.client.ressources.core.PlayerBase;
import herb.client.rest.RestClient;

public class Lobby extends LobbyBase {

	public Lobby(UUID uuid, String name) {
		super(uuid, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GameBase startGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlayer(PlayerBase p) {
		
		/*
		 * example to set query params in URL:
		 * 
		return (Player) RestClient.getClient()
				 .post()
				 .uri(uriBuilder -> uriBuilder.path("/login")
						    				  .queryParam("username", this.getUsername())
						    				  .queryParam("password", this.getPassword())
						    				  .build())
				 .retrieve()
				 .bodyToMono(Player.class)
				 .block();
		*/
		
		
	}

	@Override
	public void removePlayer(PlayerBase p) {
		// TODO Auto-generated method stub
		
	}

}
