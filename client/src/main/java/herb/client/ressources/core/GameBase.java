package herb.client.ressources.core;

import java.util.ArrayList;
import java.util.UUID;

public abstract class GameBase<Player extends PlayerBase> {
	private final UUID uuid;
	protected Player[] players;


	protected ArrayList<RoundBase> rounds = new ArrayList<RoundBase>();
	
	public GameBase(UUID uuid, Player[] players) {
		this.uuid = uuid;
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
}
