package herb.client.ressources.core;

import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class GameBase<Lobby extends LobbyBase, Round extends RoundBase, Player extends PlayerBase> {
	private final UUID uuid;
	private boolean over;

	@JsonIgnoreProperties({ "runningGame", "players" })
	private final Lobby lobby;
	private Player[] players;
	protected Integer[] scores;

	@JsonIgnore
	protected ArrayList<Round> rounds = new ArrayList<Round>();

	public GameBase(UUID uuid, Lobby lobby, Player[] players) {
		this.uuid = uuid;
		this.lobby = lobby;
		this.players = players;
	}
	
	public abstract Round startRound();

	public UUID getUuid() {
		return uuid;
	}
	
	public Player[] getPlayers() {
		return players;
	}

	public ArrayList<Round> getRounds() {
		return rounds;
	}

	public Lobby getLobby() {
		return lobby;
	}
	
	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}
	
	public Integer[] getScores() {
		return scores;
	}
}
