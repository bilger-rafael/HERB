package herb.client.ressources;

import java.util.UUID;

import herb.client.ressources.core.GameBase;
import herb.client.ressources.core.RoundBase;
import herb.client.rest.RestClient;

public class Game extends GameBase<Player>{

	public Game(UUID uuid, Player[] players) {
		super(uuid, players);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoundBase startRound() {
		Round r = RestClient.getClient()
							.post()
							.uri("/Game(" + this.getUuid() + ")/startRound")
							.retrieve()
							.bodyToMono(Round.class)
							.block();

		return r;
	}

}
