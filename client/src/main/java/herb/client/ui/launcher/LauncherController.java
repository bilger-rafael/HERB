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
	private ServiceLocator serviceLocator;

	public LauncherController(LauncherModel model, LauncherView view) {
		super(model, view);

		// action for joinButton
		view.getJoinButton().setOnAction(e -> joinLobby());

		// action for createButton
		view.getCreateButton().setOnAction(e -> getLobbyCreaterView());

		// action for refreshButton
		view.getRefreshButton().setOnAction(e -> {
			refreshLobbyList();
			refreshHighscoreList();
		});

		// if selection is empty, joinButton is disabled
		view.getJoinButton().disableProperty()
				.bind(view.lobbyRoomCenter.getSelectionModel().selectedItemProperty().isNull());

		// if selection in lobby-list is made, createButton is disabled
		view.getCreateButton().disableProperty()
				.bind(view.lobbyRoomCenter.getSelectionModel().selectedItemProperty().isNotNull());

		// action for logoutItem
		view.getLogoutMenuItem().setOnAction(e -> getBackLoginView());

		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("LauncherController initialized");

	}

	// methode for joinButton
	private void joinLobby() {
		Lobby lobby = view.lobbyRoomCenter.getSelectionModel().getSelectedItem();
		try {
			// if selection is not empty, join lobby and add player
			lobby.addPlayer(Datastore.getInstance().getMainPlayer());
			view.getMessage().setVisible(false);
			Main.getMainProgram().getLobbyView(lobby).start();
			serviceLocator.getLogger().info("Es konnte erfolgreich der Lobby beigetreten werden.");
		} catch (ExceptionBase e) {
			view.showError();
			view.getMessage().setVisible(true);
			Main.getMainProgram().getLauncher().start();
			serviceLocator.getLogger().info("Lobby ist voll.");
		}

	}

	// methode for createLobbyButton
	private void getLobbyCreaterView() {
		Main.getMainProgram().getLobbyCreater().start();

	}

	// methode for logoutButton
	private void getBackLoginView() {
		try {
			model.logout();
		} catch (ExceptionBase e) {
			view.showError();
		}
		// in every case do the logout
		view.stop();
		serviceLocator.getLogger().info("Logout");
		Main.getMainProgram().getLoginView().start();
		serviceLocator.getLogger().info("Erfolgreich ausgeloggt.");
	}

	private void refreshLobbyList() {
		try {
			model.refreshLobbyList();
		} catch (Exception e) {
			view.showErrorRefresh();

		}
	}

	private void refreshHighscoreList() {
		try {
			model.refreshHighscoreList();
		} catch (Exception e) {
			view.showErrorRefresh();

		}
	}
}
