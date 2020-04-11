package herb.client.ui.launcher;

import chat.commonClasses.Client;
import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;

public class LauncherModel extends Model{
	private Lobby[] lobbys;

    public LauncherModel() {
    	super();
    	refreshLobbyList();
    }
    
    public void refreshLobbyList() {
    	try {
			this.lobbys = Lobby.readLobbyList();
		} catch (ExceptionBase e) {
			// TODO show error message
			e.printStackTrace();
		}
    }
    
    // Create thread to update Lobby periodically
	private void startLobbyUpdater() {
	Runnable r = new Runnable() {
		boolean b = false;
		@Override
		public void run() {
			while (b) {
				refreshLobbyList();
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					}
				}
			}
		};
	}

	public Lobby[] getLobbys() {
		return lobbys;
	}
    
}
