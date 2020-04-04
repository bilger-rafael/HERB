package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import herb.server.Datastore;
import herb.server.ressources.Player;
import herb.server.ressources.PlayerNotFoundException;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;

@RestController
public class PlayerController {

	@GetMapping("/Player({username})")
	public PlayerBase getPlayer(@PathVariable String username) throws ExceptionBase {
		Player p = Datastore.getInstance().players.get(username);

		if (p == null)
			throw new PlayerNotFoundException();

		return p;
	}

	@GetMapping("/Player({username})/Round")
	public RoundBase getPlayerRound(@PathVariable String username) throws ExceptionBase {
		return getPlayer(username).getRound();
	}
}