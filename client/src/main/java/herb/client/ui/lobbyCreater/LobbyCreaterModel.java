package herb.client.ui.lobbyCreater;

import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import herb.client.utils.ServiceLocator;

//Herren
public class LobbyCreaterModel extends Model {
	ServiceLocator serviceLocator;

	public LobbyCreaterModel() {
		super();

		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("LobbyCreater model initialized");
	}

	// create a new lobby
	public void createLobby(String text) throws ExceptionBase {
		Lobby lobby;
		lobby = Lobby.createLobby(text);

	}
}
