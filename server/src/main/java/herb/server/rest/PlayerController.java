package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import herb.server.Datastore;
import herb.server.ressources.Card;
import herb.server.ressources.Player;
import herb.server.ressources.PlayerNotFoundException;
import herb.server.ressources.ServerErrorException;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.Trump;

//Bilger
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

	@PostMapping("/Player({username})/play")
	public void play(@PathVariable String username, @RequestBody Card card) throws ExceptionBase {
		Player p = Datastore.getInstance().players.get(username);

		// check if its players turn
		if (!p.getRound().getTricks().getLast().getCurrentPlayer().equals(p))
			throw new ServerErrorException();

		Card c = p.getHand().getCards().stream().filter(x -> x.equals(card)).findFirst()
				.orElseThrow(() -> new ServerErrorException());

		p.play(c);
	}

	@PostMapping("/Player({username})/chooseTrump")
	public void chooseTrump(@PathVariable String username, @RequestBody Trump trump) throws ExceptionBase {
		Player p = Datastore.getInstance().players.get(username);

		// check if its players turn
		if (!p.getRound().getCurrentStartingPlayer().equals(p))
			throw new ServerErrorException();

		// check if trump is not set jet
		if (p.getRound().getTrump() != null)
			throw new ServerErrorException();

		p.chooseTrump(trump);
	}
}