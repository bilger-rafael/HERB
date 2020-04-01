package herb.server.ressources.core;

public abstract class LobbyBase {
	private final String name;
	protected PlayerBase[] players = new PlayerBase[4];
	protected GameBase runningGame;

	protected LobbyBase(String name) {
		this.name = name;
		this.players = new PlayerBase[4];
	}

	public String getName() {
		return name;
	}

	public PlayerBase[] getPlayers() {
		return players;
	}

	public abstract void startGame();

	// Etter
	public abstract void addPlayer(PlayerBase player) throws ExceptionBase;

	// Etter
	public abstract void removePlayer(PlayerBase player) throws ExceptionBase;
}
