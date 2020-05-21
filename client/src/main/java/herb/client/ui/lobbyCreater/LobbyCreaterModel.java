package herb.client.ui.lobbyCreater;

import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
//Herren
public class LobbyCreaterModel extends Model {

	public LobbyCreaterModel() {
		super();
	}

	// create a new lobby
	public void createLobby(String text) throws ExceptionBase {
		Lobby lobby;
		lobby = Lobby.createLobby(text);

	}
}
