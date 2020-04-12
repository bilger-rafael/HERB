package herb.server.ressources.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class LobbyBase <Player extends PlayerBase> {
	private final String name;
	@JsonIgnoreProperties({ "round" })
	protected Player[] players;
	protected GameBase runningGame;

	public LobbyBase(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Player[] getPlayers() {
		return players;
	}

	public abstract void startGame();

	// Etter
	public abstract void addPlayer(Player player) throws ExceptionBase;

	// Etter
	public abstract void removePlayer(Player player) throws ExceptionBase;
}
