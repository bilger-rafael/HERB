package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.GameBase;
import herb.server.ressources.core.LobbyBase;
import herb.server.ressources.core.PlayerBase;

public class Lobby extends LobbyBase {
	private int index;
	
	public Lobby(String name) {
		super(UUID.randomUUID(), name);
	}

	@Override //Etter Spiel starten
	public GameBase startGame() {
		Game g = new Game(this.players);
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
			if (this.players[i] == p) {
				this.players[i] = null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception => Meldung auf UI
		}
	}

}
