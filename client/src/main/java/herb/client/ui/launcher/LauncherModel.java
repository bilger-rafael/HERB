package herb.client.ui.launcher;

import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import herb.client.utils.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LauncherModel extends Model {
	private ObservableList<Lobby> lobbys = FXCollections.observableArrayList();
	Lobby tempSelectedLobby;
	


	public LauncherModel() {
		super();
	//	refreshLobbyList();
	//	startLobbyUpdater();
	}

	/*
	public void refreshLobbyList() {
		try {
			this.lobbys.clear();
			this.lobbys.addAll(Lobby.readLobbyList());
			
		} catch (ExceptionBase e) {
			// TODO show error message
			e.printStackTrace();
		}
	}

	// Create thread to update Lobby periodically
	private void startLobbyUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					
					Platform.runLater(() -> refreshLobbyList());
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		Thread t = new Thread(r);
		t.setDaemon(true);
		t.start();

	}
	*/

	public ObservableList<Lobby> getLobbys() {
		return lobbys;
	}

	
	public Lobby getTempSelectedLobby() {
		return tempSelectedLobby;
	}

	public void setTempSelectedLobby(Lobby tempSelectedLobby) {
		this.tempSelectedLobby = tempSelectedLobby;
	}
}
