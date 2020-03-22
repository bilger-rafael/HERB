package herb.client.ressources;

import herb.client.ressources.core.GameBase;
import herb.client.ressources.core.LobbyBase;
import herb.client.ressources.core.PlayerBase;

public class Lobby extends LobbyBase {

	public Lobby(String name) {
		super(name);
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
