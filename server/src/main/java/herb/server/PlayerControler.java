package herb.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerControler {
	
	@GetMapping("/player")
	public Player player(@RequestParam(value = "name", defaultValue = "Rafael") String name) {
		return new Player(name, 1);
	}
	
}