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
import javafx.scene.control.MultipleSelectionModel;
//Herren
public class LauncherModel extends Model {
	ServiceLocator serviceLocator;
	private ObservableList<Lobby> lobbys = FXCollections.observableArrayList();
	private ObservableList<HighScore> highScore = FXCollections.observableArrayList();
	private Lobby tempSelectedLobby;
	


	public LauncherModel() {
		super();
		startLobbyUpdater();
		startHighScoreUpdater();
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("LauncherModel initialized");
	}

	//refresh amount lobbys in the list
	public void refreshLobbyList() throws ExceptionBase {
			this.lobbys.clear();
			this.lobbys.addAll(Lobby.readLobbyList());

	}
	
	// Create thread to update Lobby periodically
	private void startLobbyUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> {
						try {
							refreshLobbyList();
						} catch (ExceptionBase e1) {
							e1.printStackTrace();
						}
					});
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
	//refresh amount lobbys in the list
	public void refreshHighscoreList() throws ExceptionBase{
			this.highScore.clear();
			this.highScore.addAll(HighScore.readHighScoreList());
	}
	
	// Create thread to update highscore periodically
	private void startHighScoreUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> {
						try {
							refreshHighscoreList();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
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
	//getter

	public ObservableList<Lobby> getLobbys() {
		return lobbys;
	}
	
	public ObservableList<HighScore> getHighScore(){
		return highScore;
	}

	public void logout() throws ExceptionBase {
		Datastore.getInstance().getMainPlayer().logout();
	}
}
