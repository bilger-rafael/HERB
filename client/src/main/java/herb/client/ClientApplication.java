package herb.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {

		//SpringApplication.run(ClientApplication.class, args);
		
		//WebClient client = WebClient.create("http://localhost:8080");

		final String uri = "http://127.0.0.1:8080";
		RestTemplate restTemplate = new RestTemplate();

		Player player = restTemplate.getForObject(uri.concat("/player"), Player.class);

		System.out.println(player.getName() + player.getRank());

	}

}
