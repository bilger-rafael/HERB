package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import herb.server.Datastore;
import herb.server.ressources.GameNotFoundException;
import herb.server.ressources.Lobby;
import herb.server.ressources.Player;
import herb.server.ressources.PlayerNotFoundException;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.LobbyBase;

@RestController
public class LobbyController {

	@GetMapping("/Lobby({name})")
	public LobbyBase getLobby(@PathVariable String name) throws ExceptionBase {
		return Lobby.readLobby(name);
	}

	@GetMapping("/LobbyList")
	public LobbyBase[] getLobbyList() {
		return Lobby.readLobbyList();
	}

	@PostMapping("/Lobby")
	public LobbyBase createLobby(@RequestBody Lobby lobby) throws ExceptionBase {
		return Lobby.createLobby(lobby.getName());
	}

	@PostMapping("/Lobby({name})/join")
	public void joinLobby(@PathVariable String name, @RequestBody Player player) throws ExceptionBase {
		//player has to be logged in to join lobby
		Player p = Datastore.getInstance().players.get(player.getUsername());
		if (p == null)
			throw new PlayerNotFoundException();
		getLobby(name).addPlayer(p);
	}

	@PostMapping("/Lobby({name})/leave")
	public void leaveLobby(@PathVariable String name, @RequestBody Player player) throws ExceptionBase {
		getLobby(name).removePlayer(player);
	}

}