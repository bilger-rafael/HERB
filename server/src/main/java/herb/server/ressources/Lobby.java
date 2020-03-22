package herb.server.ressources;

import com.fasterxml.jackson.annotation.JsonCreator;

import herb.server.Datastore;
import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.LobbyBase;
import herb.server.ressources.core.PlayerBase;

public class Lobby extends LobbyBase {
	private int index;
	
	@JsonCreator
	public Lobby(String name) {
		super(name);
	}

	@Override //Etter Spiel starten
	public GameBase startGame() {
		Game g = new Game(this.players);
		return g;
	}
	
	//Etter Spieler dem Array hinzufügen
	public void addPlayer(PlayerBase p) {
		// TODO prüfen ob player nicht bereits in lobby
		try { 
			int i = 0;
			boolean found = false;
				// TODO check if lobby already full, otherwise this will end in a endless loop
				while(!found) {
					if(this.players[i]== null) {
						this.players[i] = p;
						found = true;
						if(i==3) 
						{startGame();
							} else {
						}
						i++;
					}
				}	
		} catch (Exception e) {
			// TODO: handle exception => Meldung auf UI
		}
	}
	
	// Etter Spieler verlässt die Lobby
	public void removePlayer(PlayerBase p) {
		try { 
			for (int i=0; i<this.players.length; i++) {
			if (this.players[i].equals(p)) {
				this.players[i] = null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception => Meldung auf UI
		}
	}
	
	public static Lobby createLobby(String name) throws LobbyAlreadyExistsException {
		if (Datastore.getInstance().lobbys.containsKey(name))
			throw new LobbyAlreadyExistsException();

		// TODO check name, if needed

		Lobby lobby = new Lobby(name);

		Datastore.getInstance().lobbys.put(name, lobby);

		return lobby;
	}
	
	public static Lobby readLobby(String name) throws LobbyNotFoundException {
		Lobby lobby = Datastore.getInstance().lobbys.get(name);

		if (lobby == null)
			throw new LobbyNotFoundException();

		return lobby;
	}

	public static Lobby[] readLobbyList() {
		return (Lobby[]) Datastore.getInstance().lobbys.values().toArray(new Lobby[0]);
	}
	

}
