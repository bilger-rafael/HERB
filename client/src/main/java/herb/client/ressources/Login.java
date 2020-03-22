package herb.client.ressources;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientException;

import herb.client.ressources.core.ExceptionBase;
import herb.client.ressources.core.LoginBase;
import herb.client.ressources.core.PlayerBase;
import herb.client.rest.RestClient;

public class Login extends LoginBase {

	public Login(String username, String password) {
		super(username, password);
	}

	@Override
	public PlayerBase login() throws ExceptionBase {
		try {
			return (Player) RestClient.getClient()
					.post()
					.uri("/login")
					.body(BodyInserters.fromValue(this))
					.retrieve()
					//.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ExceptionBase()))
					.bodyToMono(Player.class)
					.block();
		} catch (WebClientException e) {
			throw new ExceptionBase();
		}
	}

	@Override
	public void register() throws ExceptionBase {
		try {
			RestClient.getClient()
						.post()
						.uri("/register")
						.body(BodyInserters.fromValue(this))
						.retrieve()
						.bodyToMono(String.class)
						.block();
		} catch (WebClientException e) {
			throw new ExceptionBase();
		}
	}

}
