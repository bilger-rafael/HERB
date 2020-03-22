package herb.client.ressources;

import org.springframework.web.reactive.function.BodyInserters;

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
		return (Player) RestClient.getClient()
						 .post()
						 .uri("/login")
						 .body(BodyInserters.fromValue(this))
						 .retrieve()
						 .bodyToMono(Player.class)
						 .block();
	}

	@Override
	public void register() throws ExceptionBase {
		RestClient.getClient()
		 .post()
		 .uri("/register")
		 .body(BodyInserters.fromValue(this))
		 .retrieve()
		 .bodyToMono(String.class)
		 .block();
	}

}
