package herb.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import herb.server.ressources.HighScore;
import herb.server.ressources.core.ExceptionBase;

//Bilger
@RestController
public class HighScoreController {

	@GetMapping("/HighScoreList")
	public HighScore[] getHighScoreList() throws ExceptionBase {
		return HighScore.readHighScoreList().toArray(new HighScore[0]);
	}

}