package herb.client.ui.lobby;

import herb.client.ressources.Lobby;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LobbyModel extends Model {
	private Lobby lobby;
//	protected String name;


	private ObservableList<Player> players = FXCollections.observableArrayList();
	private ObservableList<Lobby> lobbys = FXCollections.observableArrayList();



	public LobbyModel(Lobby lobby) {
		super();
		this.lobby = lobby;
//		this.name = name;
		startLobbyUpdater();
	}

	private void refreshLobby() {
		try {
			lobby = Lobby.readLobby(lobby.getName());
		} catch (ExceptionBase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.players.clear();
		this.players.addAll(lobby.getPlayers());
	}

	private void startLobbyUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> refreshLobby());
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
	public Lobby getLobby() {
		return lobby;
	}
	
	public ObservableList<Lobby> getLobbys() {
		return lobbys;
	}
	
	public ObservableList<Player> getPlayers() {
		return players;
	}

}
