package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import herb.server.ressources.Player;
import herb.server.ressources.core.PlayerBase;

@RestController
public class LoginController {
	
	@GetMapping("/login")
	public PlayerBase login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
		return (PlayerBase)Player.login(username,password);
	}
	
}