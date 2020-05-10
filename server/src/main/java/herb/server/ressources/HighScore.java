package herb.server.ressources;

import java.util.ArrayList;
import java.util.Map;

import herb.server.DataStore_Repository;
import herb.server.ressources.core.HighScoreBase;

//Bilger
public class HighScore extends HighScoreBase {

	public HighScore(String username, Integer score) {
		super(username, score);
	}

	public static ArrayList<HighScore> readHighScoreList() {
		ArrayList<HighScore> highScores = new ArrayList<HighScore>();

		for (Map.Entry me : DataStore_Repository.getDB().showHighScores().entrySet()) {
			highScores.add(new HighScore((String) me.getKey(), (Integer) me.getValue()));
		}
		
		highScores.sort((s1, s2) -> s2.getScore().compareTo(s1.getScore()));
		
		return highScores;
	}

}