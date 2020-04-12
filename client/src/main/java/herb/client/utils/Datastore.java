package herb.client.utils;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import herb.client.ressources.LoginException;
import herb.client.ressources.Player;
import herb.client.rest.RestClient;

//Bilger
public class Datastore {
	
	private static Datastore datastore;
	private Player mainPlayer;
	
	private Datastore() {
		
	}
    
    public static Datastore getInstance() {
        if (datastore == null)
        	datastore = new Datastore();
        return datastore;
    }

	public Player getMainPlayer() {
		//refresh player
		try {
			return (Player) RestClient.getClient()
					.get()
					.uri(uriBuilder -> uriBuilder.path("/Player({name})")
		  				                         .build(mainPlayer.getUsername()))
					.retrieve()
					.bodyToMono(Player.class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
		}
		
		return mainPlayer;
	}

	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

}
