package herb.server.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import herb.server.ressources.Login;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.PlayerBase;

//Bilger
@RestController
public class AuthenticationController {

	// TODO implement JWT Token
	// (https://ertan-toker.de/spring-boot-spring-security-jwt-token/

	@PostMapping("/login")
	public PlayerBase login(@RequestBody Login login) throws ExceptionBase {
		return login.login();
	}

	@PostMapping("/register")
	public void register(@RequestBody Login login) throws ExceptionBase {
		login.register();
	}

}