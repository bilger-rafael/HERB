package herb.client.ui.launcher;

import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;

public class LauncherModel extends Model{
	private Lobby[] lobbys;

    public LauncherModel() {
    	super();
    	refreshLobbyList();
    	
    	//TODO refresh via button click or all 5 min
    }
    
    public void refreshLobbyList() {
    	try {
			this.lobbys = Lobby.readLobbyList();
		} catch (ExceptionBase e) {
			// TODO show error message
			e.printStackTrace();
		}
    }

	public Lobby[] getLobbys() {
		return lobbys;
	}
    
}
