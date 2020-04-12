package herb.client.ui.launcher;

import herb.client.Main;
import herb.client.ui.core.Controller;

public class LauncherController extends Controller<LauncherModel, LauncherView> {

	public LauncherController(LauncherModel model, LauncherView view) {
		super(model, view);
		
		// join a lobby
		view.getJoinButton().setOnAction(e -> joinLobby());
		
	
	}
	
	// join a lobby
	private void joinLobby() {
		// choose lobby TODO
		if (view.lobbyRoomCenter.getSelectionModel().isEmpty()) return;
		String lobbyName = view.lobbyRoomCenter.getSelectionModel().getSelectedItem();
		Main.getMainProgram().getLobbyView(lobbyName).start();

		}
			
}
