package herb.client.ui.launcher;

import herb.client.Main;
import herb.client.ui.core.Controller;

public class LauncherController extends Controller<LauncherModel, LauncherView> {

	public LauncherController(LauncherModel model, LauncherView view) {
		super(model, view);
		
		// join a lobby
		view.getJoinButton().setOnAction(e -> joinLobby());
		
		//Skip for Daniela
		view.getSkipButton().setOnAction(e -> getGameView());
		
		
		// got to lobby create view
		view.getCreateButton().setOnAction(e -> getLobbyCreaterView());
		
		/**
		 * refresh the launcher
		 */
		
		view.getRefreshButton().setOnAction(e -> model.refreshLobbyList());
	
	}
	
	// join a lobby
	private void joinLobby() {
		// choose lobby TODO
		if (view.lobbyRoomCenter.getSelectionModel().isEmpty()) return;
		String lobbyName = view.lobbyRoomCenter.getSelectionModel().getSelectedItem();
		Main.getMainProgram().getLobbyView(lobbyName).start();

		}
	
	//got to Lobby create view
	private void getLobbyCreaterView() {
		this.view.stop();
		Main.getMainProgram().getLobbyCreater().start();
	}
	

	//Skip for Daniela
	private void getGameView() {
		this.view.stop();
		Main.getMainProgram().getGameView().start();
	}
			
}
