package herb.client.ui.lobby;

import java.util.Arrays;
import java.util.stream.Collectors;

import herb.client.ressources.Lobby;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Model;
import herb.client.utils.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Herren
public class LobbyModel extends Model {
	private ServiceLocator serviceLocator;
	private Lobby lobby;
	private ObservableList<Player> players = FXCollections.observableArrayList();

	public LobbyModel(Lobby lobby) {
		super();
		this.lobby = lobby;
		startLobbyUpdater();
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("LobbyModel initialized");
	}

	// refresh lobby
	public void refreshLobby() throws ExceptionBase {
		lobby = Lobby.readLobby(lobby.getName());

		// update amount of players by clearing and adding again
		this.players.clear();
		this.players
				.addAll(Arrays.asList(lobby.getPlayers()).stream().filter(x -> x != null).collect(Collectors.toList()));
	}

	// Create thread to update Lobby periodically
	private void startLobbyUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (!getLobby().isFull()) {
					Platform.runLater(() -> {
						try {
							refreshLobby();
						} catch (ExceptionBase e1) {
							e1.printStackTrace();
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		Thread t = new Thread(r);
		t.setDaemon(true);
		t.start();

	}

	// getter
	public Lobby getLobby() {
		return lobby;
	}

	public ObservableList<Player> getPlayers() {
		return players;
	}

}
