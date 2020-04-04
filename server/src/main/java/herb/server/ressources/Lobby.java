package herb.server.ressources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

import herb.server.Datastore;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.LobbyBase;
import herb.server.ressources.core.PlayerBase;

public class Lobby extends LobbyBase {
	private int index;

	@JsonCreator
	public Lobby(String name) {
		super(name);
	}

	@Override // Etter Spiel starten
	public void startGame() {
		runningGame = new Game(this.players);
	}

	// Etter Spieler dem Array hinzufügen
	public void addPlayer(PlayerBase player) throws ExceptionBase {
		if (isFull())
			throw new ServerErrorException();
		
		//TODO check if player is already in lobby

		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i] == null) {
				this.players[i] = player;
				break;
			}
		}

		if (isFull())
			startGame();
	}

	// Etter Spieler verlässt die Lobby
	public void removePlayer(PlayerBase player) throws ExceptionBase {
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i].equals(player)) {
				this.players[i] = null;
			}
		}
	}

	public boolean isFull() {
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i] == null) {
				return false;
			}
		}

		return true;
	}

	public static Lobby createLobby(String name) throws ExceptionBase {
		if (Datastore.getInstance().lobbys.containsKey(name))
			throw new LobbyAlreadyExistsException();

		// TODO check name, if needed

		Lobby lobby = new Lobby(name);

		Datastore.getInstance().lobbys.put(name, lobby);

		return lobby;
	}

	public static Lobby readLobby(String name) throws ExceptionBase {
		Lobby lobby = Datastore.getInstance().lobbys.get(name);

		if (lobby == null)
			throw new LobbyNotFoundException();

		return lobby;
	}

	public static Lobby[] readLobbyList() {
		return (Lobby[]) Datastore.getInstance().lobbys.values().toArray(new Lobby[0]);
	}

}
