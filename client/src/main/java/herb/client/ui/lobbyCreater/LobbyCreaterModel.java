package herb.client.ui.lobbyCreater;

import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;

public class LobbyCreaterModel extends Model{

    public LobbyCreaterModel() {
    	super();
    }

	public void createLobby(String text) {
		Lobby lobby;
		try {
			lobby = Lobby.createLobby(text);
			System.out.println(lobby.getName());
		} catch (ExceptionBase e) {
			// TODO show error message
			e.printStackTrace();
		}
	}
}
