package herb.client.ressources;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
					.bodyToMono(Player.class)
					.block();
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new LoginException();
		} catch (WebClientException e) {
			throw new LoginException();
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
		} catch (WebClientResponseException e) {
			//TODO check e.getStatusCode() and raise specific error
			throw new LoginException();
		} catch (WebClientException e) {
			throw new LoginException();
		}
	}

}
