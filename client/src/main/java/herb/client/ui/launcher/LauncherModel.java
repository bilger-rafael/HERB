package herb.client.ui.launcher;

import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import herb.client.utils.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LauncherModel extends Model{
	private Lobby[] lobbys;
    ObservableList<Lobby> lobbysName = FXCollections.observableArrayList();
	ServiceLocator servicelocator;


    public LauncherModel() {
    	super();
    	refreshLobbyList();
    	startLobbyUpdater();
	    ObservableList<Lobby> lobbys = FXCollections.observableArrayList();
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
		boolean b = true;
		@Override
		public void run() {
			Thread t = new Thread ();
			t.setDaemon(true);
			t.start();
			while (b) {
				refreshLobbyList();
				servicelocator.getLogger().info("Launcher refreshed");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					}
				}
			}
		};
	}

	public Lobby[] getLobbys() {
		return lobbys;
	}
	
	public ObservableList<Lobby> getLobbyName() {
		return lobbysName;
	}
    
}
