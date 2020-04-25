package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import herb.server.Datastore;
import herb.server.ressources.Game;
import herb.server.ressources.GameNotFoundException;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.RoundBase;

//Bilger
@RestController
public class GameController {

	@GetMapping("/Game({id})")
	public GameBase getGame(@PathVariable String id) throws ExceptionBase {
		Game g = Datastore.getInstance().games.get(id);

		if (g == null)
			throw new GameNotFoundException();

		return g;
	}

	@PostMapping("/Game({id})/startRound")
	public RoundBase startRound(@PathVariable String id) throws ExceptionBase {
		return getGame(id).startRound();
	}

}