package herb.client.ui.lobbyCreater;

import herb.client.Main;
import herb.client.ui.core.Controller;

public class LobbyCreaterController extends Controller<LobbyCreaterModel, LobbyCreaterView>{
	public LobbyCreaterController(LobbyCreaterModel model, LobbyCreaterView view) {
		super(model, view);
		
		// TODO implementation
		
		// Create Lobby
		view.getOkButton().setOnAction(e -> createLobby());
		
		
	}
	
	private void createLobby() {
		//TODO logic implementation for Lobby creation
		model.createLobby(view.getTextField().getText());
		
		//TODO if created back to Launcher
		this.view.stop();
		Main.getMainProgram().getLauncher().start();
	}

}
