package herb.client.ui.botSelectioner;
import herb.client.ressources.Lobby;
import herb.client.ui.core.Model;

public class BotModel extends Model {
	Lobby lobby;


	public BotModel(Lobby lobby) {
		super();
		this.lobby = lobby;
	}

	
	public Lobby getLobby() {
		return lobby;
	}
}
