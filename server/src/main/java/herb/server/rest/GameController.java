package herb.server.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import herb.server.Datastore;
import herb.server.ressources.Game;
import herb.server.ressources.core.RoundBase;

@RestController
public class GameController {
	
	@PostMapping("/Game({id})/startRound")
	public RoundBase startRound(@PathVariable String id) {
		//TODO check if Game with id exists and read it instead of create a new one
		
		Game g = Datastore.getInstance().games.get(id);
		
		if(g == null) return null; //TODO return http 4xx oder 5xx status
		
		return g.startRound();
	}
	
}