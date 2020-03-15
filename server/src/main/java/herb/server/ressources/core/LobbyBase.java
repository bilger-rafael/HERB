package herb.server.ressources.core;

public abstract class LobbyBase {
	private final String name;
	protected PlayerBase[] players = new PlayerBase[4];
	
	public LobbyBase(String name) {
		this.name = name;
	}
	
	public abstract GameBase startGame();

	public String getName() {
		return name;
	}
}
