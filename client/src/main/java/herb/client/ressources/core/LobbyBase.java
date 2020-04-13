package herb.client.ressources.core;

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
	
	public boolean isFull() {
		if(this.players == null) return false;
		
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i] == null) {
				return false;
			}
		}

		return true;
	}

	public abstract void startGame();

	// Etter
	public abstract void addPlayer(Player player) throws ExceptionBase;

	// Etter
	public abstract void removePlayer(Player player) throws ExceptionBase;
}
