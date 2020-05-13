package herb.client.ui.launcher;

import herb.client.ressources.HighScore;
import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import herb.client.utils.Datastore;
import herb.client.utils.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LauncherModel extends Model {
	private ObservableList<Lobby> lobbys = FXCollections.observableArrayList();
	private ObservableList<HighScore> highScore = FXCollections.observableArrayList();
	Lobby tempSelectedLobby;
	


	public LauncherModel() {
		super();
		refreshLobbyList();
		refreshHighscoreList();
		startLobbyUpdater();
		startHighScoreUpdater();
		
	}

	
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
	
	public void refreshHighscoreList() {
		try {
			this.highScore.clear();
			this.highScore.addAll(HighScore.readHighScoreList());
		}catch(ExceptionBase e) {
			// TODO show error message
			e.printStackTrace();
		}
	}
	
	// Create thread to update Lobby periodically
	private void startHighScoreUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> refreshHighscoreList());
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
	

	public ObservableList<Lobby> getLobbys() {
		return lobbys;
	}
	
	public ObservableList<HighScore> getHighScore(){
		return highScore;
	}

	
	public Lobby getTempSelectedLobby() {
		return tempSelectedLobby;
	}

	public void setTempSelectedLobby(Lobby tempSelectedLobby) {
		this.tempSelectedLobby = tempSelectedLobby;
	}
	
	public void logout() throws ExceptionBase {
		Datastore.getInstance().getMainPlayer().logout();
	}
}
