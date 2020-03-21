package herb.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import herb.server.ressources.Player;
import herb.server.ressources.PlayerAlreadyExistsException;
import herb.server.ressources.PlayerLoginFailedException;
import herb.server.ressources.PlayerNotFoundException;
import herb.server.ressources.core.PlayerBase;

@RestController
public class AuthenticationController {

	// TODO implement JWT Token
	// (https://ertan-toker.de/spring-boot-spring-security-jwt-token/)

	@PostMapping("/login")
	public PlayerBase login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		try {
			return (PlayerBase) Player.login(username, password);
		} catch (PlayerNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
		} catch (PlayerLoginFailedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
		}

	}

	@PostMapping("/register")
	public PlayerBase register(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		
		try {
			return (PlayerBase) Player.register(username, password);
		} catch (PlayerAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}

	}

}