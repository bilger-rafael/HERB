package herb.client.rest;

import org.springframework.web.reactive.function.client.WebClient;

import herb.client.utils.ServiceLocator;

public class RestClient {
	private static WebClient client;
	
	public static WebClient getClient() {
		if (client == null) {
			String url = ServiceLocator.getInstance().getConfiguration().getOption("rootURL");
			client = WebClient.create(url);
		}
		return client;
	}

}
