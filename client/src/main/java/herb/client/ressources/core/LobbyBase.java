package herb.client.ressources.core;

import java.util.UUID;

public abstract class LobbyBase {
	private final UUID uuid;
	private final String name;
	protected PlayerBase[] players = new PlayerBase[4];
	
	public LobbyBase(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		this.players = new PlayerBase[4];
	}
	
	public abstract GameBase startGame();

	public String getName() {
		return name;
	}
	
	//Etter
	public abstract void addPlayer(PlayerBase p);
	
	//Etter
	public abstract void removePlayer(PlayerBase p);
}
