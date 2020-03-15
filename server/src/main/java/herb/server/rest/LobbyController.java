package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import herb.server.ressources.core.LobbyBase;

@RestController
public class LobbyController {
	
	@GetMapping("/LobbySet")
	public LobbyBase[] getLobbyList() {
		return null; //TODO implement
	}
	
	@PostMapping("/Lobby")
	public void createLobby() {
		//TODO implement
	}
	
	@PostMapping("/Lobby({name})/join")
	public void joinLobby(@PathVariable String name) {
		//TODO implement
	}
	
}