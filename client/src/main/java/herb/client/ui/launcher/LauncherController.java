package herb.client.ui.launcher;

import herb.client.Main;
import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;
import herb.client.utils.ServiceLocator;
import javafx.application.Platform;

public class LauncherController extends Controller<LauncherModel, LauncherView> {
	ServiceLocator serviceLocator;

	public LauncherController(LauncherModel model, LauncherView view) {
		super(model, view);
		
		refreshLobbyList();
		startLobbyUpdater();
		
		// join a lobby
		view.getJoinButton().setOnAction(e -> joinLobby());

		// got to lobby create view
		view.getCreateButton().setOnAction(e -> getLobbyCreaterView());

		/**
		 * refresh the launcher
		 */

		view.getRefreshButton().setOnAction(e -> refreshLobbyList());
		
		/**
		 * join a lobby
		 */		
		view.getJoinButton().disableProperty().bind(view.lobbyRoomCenter.getSelectionModel().selectedItemProperty().isNull());
		
		/**
		 * logout 
		 */
		view.getLogoutMenuItem().setOnAction(e -> getBackLoginView());
		
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Launcher controller initialized");

	}

	// join a lobby
	private void joinLobby() {
		if (view.lobbyRoomCenter.getSelectionModel().isEmpty())
			return;
		Lobby lobby = view.lobbyRoomCenter.getSelectionModel().getSelectedItem();
		try {
			lobby.addPlayer(Datastore.getInstance().getMainPlayer());
		} catch (ExceptionBase e) {
			// TODO show error message and DONT call new lobby view
			e.printStackTrace();
		}
		Main.getMainProgram().getLobbyView(lobby).start();

	}

	// got to Lobby create view
	private void getLobbyCreaterView() {
		//this.view.stop();
		Main.getMainProgram().getLobbyCreater().start();
	}
	
	private void getBackLoginView() {
		view.stop();
		serviceLocator.getLogger().info("Logout");
		Main.getMainProgram().getLoginView().start();
	}
	
	//gets the actual Lobby-selection in the model
	private void getSelection() {
		if(view.getLobbyRoomCenter().getSelectionModel().getSelectedItem()!=null) {
			this.model.setTempSelectedLobby(view.getLobbyRoomCenter().getSelectionModel().getSelectedItem());
		};
	}
	
	//sets the actual Lobby-selection after the Refresh // TODO debugging => set the Selection correctly
	private void setSelection() {
		view.getLobbyRoomCenter().getSelectionModel().select(this.model.getTempSelectedLobby());
	}

	public void refreshLobbyList() {
		try {
			getSelection();
			this.model.getLobbys().clear();
			this.model.getLobbys().addAll(Lobby.readLobbyList());
			setSelection();
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
}
