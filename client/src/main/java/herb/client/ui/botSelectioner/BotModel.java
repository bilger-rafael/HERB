package herb.client.ui.botSelectioner;
import herb.client.ressources.Lobby;
import herb.client.ui.core.Model;
import herb.client.utils.ServiceLocator;

public class BotModel extends Model {
	private ServiceLocator serviceLocator;
	Lobby lobby;


	public BotModel(Lobby lobby) {
		super();
		this.lobby = lobby;
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("BotModel initialized");
	}

	
	public Lobby getLobby() {
		return lobby;
	}
}
