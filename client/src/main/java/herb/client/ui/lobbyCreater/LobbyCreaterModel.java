package herb.client.ui.lobbyCreater;

import herb.client.ressources.Lobby;
import herb.client.ui.core.Model;

public class LobbyCreaterModel extends Model{

    public LobbyCreaterModel() {
    	super();
    }

	public void createLobby(String text) {
		Lobby lobby = new Lobby(text);
		System.out.println(lobby.getName());
		// TODO Auto-generated method stub
		
	}
}
