package herb.client.ressources;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import herb.client.ressources.core.ExceptionBase;
import herb.client.ressources.core.GameBase;
import herb.client.ressources.core.LobbyBase;
import herb.client.ressources.core.PlayerBase;
import herb.client.rest.RestClient;

public class Lobby extends LobbyBase<Player> {
	
	// default constructor for json deserialization
	public Lobby() {
		super("");
	}
	
	@Override
	public String toString() {
		int number = 0;
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i] != null) number++;
		}
		
		return this.getName() + "(" + number + "/4)";
	}
	
	public Lobby(String name) {
		super(name);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addPlayer(Player player) throws ExceptionBase {
		try {
			RestClient.getClient()
					.post()
					.uri(uriBuilder -> uriBuilder.path("/Lobby({name})/join")
		    	   		  				         .build(this.getName()))
					.body(BodyInserters.fromValue(player))
					.retrieve()
					.bodyToMono(String.class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new LobbyException();
		} catch (WebClientException e) {
			throw new LobbyException();
		}
	}

	@Override
	public void removePlayer(Player player) throws ExceptionBase {
		// TODO implement (copy addPlayer and change join to leave
	}
	
	public static Lobby createLobby(String name) throws ExceptionBase {
		Lobby lobby = new Lobby(name);
		try {
			lobby = RestClient.getClient()
					.post()
					.uri("/Lobby")
					.body(BodyInserters.fromValue(lobby))
					.retrieve()
					.bodyToMono(Lobby.class)
					.block();
			return lobby;
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new LobbyException();
		} catch (WebClientException e) {
			throw new LobbyException();
		}
	}
	
	public static Lobby readLobby(String name) throws ExceptionBase {
		try {
			return (Lobby) RestClient.getClient()
					 .get()
					 .uri(uriBuilder -> uriBuilder.path("/Lobby({name})")
		    				  				      .build(name))
					.retrieve()
					.bodyToMono(Lobby.class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new LobbyException();
		} catch (WebClientException e) {
			throw new LobbyException();
		}
	}

	public static Lobby[] readLobbyList() throws ExceptionBase {
		try {
			return (Lobby[]) RestClient.getClient()
					.get()
					.uri("/LobbyList")
					.retrieve()
					.bodyToMono(Lobby[].class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new LobbyException();
		} catch (WebClientException e) {
			throw new LobbyException();
		}
	}

}
