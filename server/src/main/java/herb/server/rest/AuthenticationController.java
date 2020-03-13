package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import herb.server.ressources.Player;
import herb.server.ressources.core.PlayerBase;

@RestController
public class AuthenticationController {
	
	//TODO implement JWT Token (https://ertan-toker.de/spring-boot-spring-security-jwt-token/)
	
	@GetMapping("/login")
	public PlayerBase login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
		return (PlayerBase)Player.login(username,password);
		//TEST: http://127.0.0.1:8080/login?username=%22Rafael%22&password=%22Test%22
	}
	
}