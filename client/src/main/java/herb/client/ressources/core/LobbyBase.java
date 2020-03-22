package herb.client.ressources.core;

public abstract class LobbyBase {
	private final String name;
	protected PlayerBase[] players = new PlayerBase[4];

	public LobbyBase(String name) {
		this.name = name;
		this.players = new PlayerBase[4];
	}

	public String getName() {
		return name;
	}

	public PlayerBase[] getPlayers() {
		return players;
	}

	public abstract GameBase startGame();

	// Etter
	public abstract void addPlayer(PlayerBase p);

	// Etter
	public abstract void removePlayer(PlayerBase p);
}
