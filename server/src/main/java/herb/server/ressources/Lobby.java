package herb.server.ressources;

import com.fasterxml.jackson.annotation.JsonCreator;

import herb.server.Datastore;
import herb.server.bot.Bot;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.LobbyBase;

public class Lobby extends LobbyBase<Player> {

	@JsonCreator
	public Lobby(String name) {
		super(name);
		this.players = new Player[4];
	}

	@Override // Etter Spiel starten
	public void startGame() {
		runningGame = new Game(this, this.players);
	}

	// Etter Spieler dem Array hinzufügen
	public void addPlayer(Player player) throws ExceptionBase {
		if (isFull())
			throw new ServerErrorException();

		// check if player is already in the lobby
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i] != null) {
				if (player.equals(this.players[i]))
					throw new ServerErrorException();
			}
		}

		// add player to the lobby
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
	public void removePlayer(Player player) throws ExceptionBase {
		if (runningGame != null)
			throw new GameAlreadyStartedException();
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i] == null)
				continue;
			if (this.players[i].equals(player)) {
				this.players[i] = null;
			}
		}
	}

	public void addBot() throws ExceptionBase {
		Bot b = new Bot();
		
		// for test purposes, to allow get player 
		Datastore.getInstance().players.put(b.getUsername(), b);
		
		this.addPlayer(b);
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
