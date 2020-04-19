package herb.client.ui.launcher;

import herb.client.Main;
import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;

public class LauncherController extends Controller<LauncherModel, LauncherView> {

	public LauncherController(LauncherModel model, LauncherView view) {
		super(model, view);

		// join a lobby
		view.getJoinButton().setOnAction(e -> joinLobby());


		// got to lobby create view
		view.getCreateButton().setOnAction(e -> getLobbyCreaterView());

		/**
		 * refresh the launcher
		 */

		view.getRefreshButton().setOnAction(e -> model.refreshLobbyList());
		
		view.getJoinButton().disableProperty().bind(view.lobbyRoomCenter.getSelectionModel().selectedItemProperty().isNull());

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


}
