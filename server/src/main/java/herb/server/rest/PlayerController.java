package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import herb.server.ressources.Player;
import herb.server.ressources.core.PlayerBase;

@RestController
public class PlayerController {
	
	//TODO implement JWT Token (https://ertan-toker.de/spring-boot-spring-security-jwt-token/)
	
	@GetMapping("/player/{id}")
	public PlayerBase get(@PathVariable String id) {
		return (PlayerBase)new Player(id,"");
		/*
		return (PlayerBase)Player.login(username,password);
		//TEST: http://127.0.0.1:8080/login?username=%22Rafael%22&password=%22Test%22
		 */
	}
	
}