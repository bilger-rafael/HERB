package herb.client.ui.launcher;

import herb.client.Main;
import herb.client.ressources.Lobby;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;
import herb.client.utils.ServiceLocator;
import javafx.application.Platform;

//Herren
public class LauncherController extends Controller<LauncherModel, LauncherView> {
	ServiceLocator serviceLocator;

	public LauncherController(LauncherModel model, LauncherView view) {
		super(model, view);

		//action for joinButton
		view.getJoinButton().setOnAction(e -> joinLobby());

		//action for createButton
		view.getCreateButton().setOnAction(e -> getLobbyCreaterView());

		//action for refreshButton
		view.getRefreshButton().setOnAction(e -> model.refreshLobbyList());
		
		//action for refreshButton
		view.getRefreshButton().setOnAction(e -> model.refreshHighscoreList());

		//if selection is empty, joinButton is disabled
		view.getJoinButton().disableProperty()
				.bind(view.lobbyRoomCenter.getSelectionModel().selectedItemProperty().isNull());

		//action for logoutItem
		view.getLogoutMenuItem().setOnAction(e -> getBackLoginView());

		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("Launcher controller initialized");

	}

	//methode for joinButton
	private void joinLobby() {
//		if (view.lobbyRoomCenter.getSelectionModel().isEmpty())
//			return;
		Lobby lobby = view.lobbyRoomCenter.getSelectionModel().getSelectedItem();
		try {
			//if selection is not empty, join lobby and add player
			lobby.addPlayer(Datastore.getInstance().getMainPlayer());
		} catch (ExceptionBase e) {
			// TODO show error message and DONT call new lobby view
			e.printStackTrace();
		}
		Main.getMainProgram().getLobbyView(lobby).start();

	}

	//methode for createLobbyButton
	private void getLobbyCreaterView() {
		Main.getMainProgram().getLobbyCreater().start();
	}
	//methode for logoutButton
	private void getBackLoginView() {
		try {
			model.logout();
		} catch (ExceptionBase e) {
			view.showError();
		}
		//in every case do the logout
		view.stop();
		serviceLocator.getLogger().info("Logout");
		Main.getMainProgram().getLoginView().start();
	}

}
