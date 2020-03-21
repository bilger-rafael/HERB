package herb.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import herb.server.ressources.Lobby;
import herb.server.ressources.LobbyAlreadyExistsException;
import herb.server.ressources.LobbyNotFoundException;
import herb.server.ressources.Player;
import herb.server.ressources.PlayerAlreadyExistsException;
import herb.server.ressources.core.LobbyBase;
import herb.server.ressources.core.PlayerBase;

@RestController
public class LobbyController {
	
	@GetMapping("/Lobby({name})")
	public LobbyBase getLobby(@PathVariable String name) {
		try {
			return Lobby.readLobby(name);
		} catch (LobbyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping("/LobbySet")
	public LobbyBase[] getLobbyList() {
		return Lobby.readLobbyList();
	}
	
	@PostMapping("/Lobby")
	public LobbyBase createLobby(@RequestBody Lobby lobby) {
		try {
			return Lobby.createLobby(lobby.getName());
		} catch (LobbyAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		}
	}
	
	@PostMapping("/Lobby({name})/join")
	public void joinLobby(@PathVariable String name, @RequestBody Player player) {
		getLobby(name).addPlayer(player);
	}
	
	@PostMapping("/Lobby({name})/leave")
	public void leaveLobby(@PathVariable String name, @RequestBody Player player) {
		getLobby(name).removePlayer(player);
	}
	
}