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
import herb.server.ressources.core.CardBase;
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

	@PostMapping("/Player({username})/play")
	public void play(@PathVariable String username, @RequestBody Card card) throws ExceptionBase {
		
		//TODO check if its players turn and if card is in his hand
		
		CardBase[] cards = getPlayer(username).getHand().getCards();

		for (int i = 0; i < cards.length; i++) {
			if (cards[i].equals(card))
				getPlayer(username).play(cards[i]);
		}
	}
}