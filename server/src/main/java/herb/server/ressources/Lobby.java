package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.LobbyBase;
import herb.server.ressources.core.PlayerBase;

public class Lobby extends LobbyBase {
	private UUID uuid;
	protected PlayerBase[] players;
	private int index;

	//TODO @Raphael, weisst du wie das mit der UUID funktioniert?
	public Lobby(String name) {
		super(name);
		this.uuid= new UUID(0, 0);
		this.players = new PlayerBase[4];
	}

	@Override //Etter Spiel starten
	public GameBase startGame() {
		Game g = new Game(this.uuid, this.players);
		return g;
	}
	
	//Etter Spieler dem Array hinzufügen
	public void addPlayer(PlayerBase p) {
		try { 
			int i = 0;
			boolean found = false;
				while(!found) {
					if(this.players[i]== null) {
						this.players[i] = p;
						found = true;
					} else {
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
			if (this.players[i] == p) {
				this.players[i] = null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception => Meldung auf UI
		}
	}

}
