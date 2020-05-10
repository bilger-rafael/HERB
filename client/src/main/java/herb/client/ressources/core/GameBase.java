package herb.client.ressources.core;

import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class GameBase<Lobby extends LobbyBase, Player extends PlayerBase> {
	private final UUID uuid;
	@JsonIgnoreProperties({ "runningGame", "players" })
	private final Lobby lobby;
	protected Player[] players;
	protected ArrayList<RoundBase> rounds = new ArrayList<RoundBase>();
	
	public GameBase(UUID uuid, Lobby lobby, Player[] players) {
		this.uuid = uuid;
		this.lobby = lobby;
		this.players = players;
	}
	
	public abstract RoundBase startRound();

	public UUID getUuid() {
		return uuid;
	}
	
	public Player[] getPlayers() {
		return players;
	}

	public ArrayList<RoundBase> getRounds() {
		return rounds;
	}

	public Lobby getLobby() {
		return lobby;
	}
}
