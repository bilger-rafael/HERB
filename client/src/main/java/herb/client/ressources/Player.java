package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ressources.core.HandBase;
import herb.client.ressources.core.PlayerBase;
import herb.client.ressources.core.Trump;
import herb.client.rest.RestClient;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.annotation.JsonCreator;

import herb.client.ressources.Player;

public class Player extends PlayerBase<Hand, Round> {

	// default constructor for json deserialization
	public Player() {
		super("", "");
	}
	
	public Player(String username, String authToken) {
		super(username, authToken);
	}
	
	@Override
	public String toString() {
		return this.getUsername();
	}

	@Override
	public void play(CardBase card) throws ExceptionBase {
		try {
			RestClient.getClient()
					.post()
					.uri(uriBuilder -> uriBuilder.path("/Player({username})/play")
		    	   		  				         .build(this.getUsername()))
					.body(BodyInserters.fromValue(card))
					.retrieve()
					.bodyToMono(String.class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new PlayerException();
		} catch (WebClientException e) {
			throw new PlayerException();
		}
	}

	@Override
	public void chooseTrump(Trump trump) throws ExceptionBase {
		try {
			RestClient.getClient()
					.post()
					.uri(uriBuilder -> uriBuilder.path("/Player({username})/chooseTrump")
		    	   		  				         .build(this.getUsername()))
					.body(BodyInserters.fromValue(trump))
					.retrieve()
					.bodyToMono(String.class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new PlayerException();
		} catch (WebClientException e) {
			throw new PlayerException();
		}
	}
	
	@Override
	public void logout() throws ExceptionBase {
		try {
			RestClient.getClient()
						.post()
						.uri("/logout")
						.body(BodyInserters.fromValue(this))
						.retrieve()
						.bodyToMono(String.class)
						.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new LoginException();
		} catch (WebClientException e) {
			throw new LoginException();
		}
	}

	@Override
	public void demandRematch(Boolean rematch) throws ExceptionBase {
		try {
			RestClient.getClient()
					.post()
					.uri(uriBuilder -> uriBuilder.path("/Player({username})/demandRematch")
		    	   		  				         .build(this.getUsername()))
					.body(BodyInserters.fromValue(rematch))
					.retrieve()
					.bodyToMono(String.class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new PlayerException();
		} catch (WebClientException e) {
			throw new PlayerException();
		}
	}

}
