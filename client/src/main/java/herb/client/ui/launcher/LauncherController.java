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

		// join a lobby
		view.getJoinButton().setOnAction(e -> joinLobby());

		// got to lobby create view
		view.getCreateButton().setOnAction(e -> getLobbyCreaterView());

		/**
		 * refresh lobby
		 */
		view.getRefreshButton().setOnAction(e -> model.refreshLobbyList());

		/**
		 * refresh highscore
		 */
		view.getRefreshButton().setOnAction(e -> model.refreshHighscoreList());

		/**
		 * join a lobby
		 */
		view.getJoinButton().disableProperty()
				.bind(view.lobbyRoomCenter.getSelectionModel().selectedItemProperty().isNull());

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
		Main.getMainProgram().getLobbyCreater().start();
	}

	private void getBackLoginView() {
		try {
			model.logout();
		} catch (ExceptionBase e) {
		}
		view.stop();
		serviceLocator.getLogger().info("Logout");
		Main.getMainProgram().getLoginView().start();
	}

	// gets the actual Lobby-selection in the model
	private void getSelection() {
		if (view.getLobbyRoomCenter().getSelectionModel().getSelectedItem() != null) {
			this.model.setTempSelectedLobby(view.getLobbyRoomCenter().getSelectionModel().getSelectedItem());
		}
		;
	}

	// sets the actual Lobby-selection after the Refresh // TODO debugging => set
	// the Selection correctly
	private void setSelection() {
		view.getLobbyRoomCenter().getSelectionModel().select(this.model.getTempSelectedLobby());
	}
}
