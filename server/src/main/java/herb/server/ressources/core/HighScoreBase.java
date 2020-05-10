package herb.server.ressources.core;

//Bilger
public abstract class HighScoreBase {
	private final String username;
	private final Integer score;
	
	public HighScoreBase(String username, Integer score) {
		super();
		this.username = username;
		this.score = score;
	}

	public String getUsername() {
		return username;
	}

	public Integer getScore() {
		return score;
	}

}
