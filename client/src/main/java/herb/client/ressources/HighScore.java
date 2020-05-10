package herb.client.ressources;

import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import herb.client.ressources.core.ExceptionBase;
import herb.client.ressources.core.HighScoreBase;
import herb.client.rest.RestClient;

//Bilger
public class HighScore extends HighScoreBase {
	
	// default constructor for json deserialization
	public HighScore() {
		super("", 0);
	}

	public HighScore(String username, Integer score) {
		super(username, score);
	}

	public static HighScore[] readHighScoreList() throws ExceptionBase {
			try {
				return (HighScore[]) RestClient.getClient()
						.get()
						.uri("/HighScoreList")
						.retrieve()
						.bodyToMono(HighScore[].class)
						.block();
			} catch (WebClientResponseException e) {
				//TODO check e.getStatusCode() and raise specific error
				throw new HighScoreException();
			} catch (WebClientException e) {
				throw new HighScoreException();
			}
	}
	
}