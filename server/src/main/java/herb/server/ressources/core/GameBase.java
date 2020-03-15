package herb.server.ressources.core;

import java.util.ArrayList;
import java.util.UUID;

public abstract class GameBase {
	private final UUID uuid;
	protected PlayerBase[] players = new PlayerBase[4];
	protected ArrayList<RoundBase> rounds = new ArrayList<RoundBase>();
	
	public GameBase(UUID uuid) {
		this.uuid = uuid;
	}
	
	public abstract RoundBase startRound();

	public UUID getUuid() {
		return uuid;
	}
}
