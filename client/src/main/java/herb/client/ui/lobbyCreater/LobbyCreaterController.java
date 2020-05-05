package herb.client.ui.lobbyCreater;

import herb.client.Main;
import herb.client.ui.core.Controller;

public class LobbyCreaterController extends Controller<LobbyCreaterModel, LobbyCreaterView>{
	public LobbyCreaterController(LobbyCreaterModel model, LobbyCreaterView view) {
		super(model, view);
		
		// TODO implementation
		
		// Create Lobby
		view.getOkButton().setOnAction(e -> createLobby());
		
		view.getOkButton().disableProperty().bind(view.getTextField().textProperty().isEmpty());
		
		view.getCancelButton().setOnAction(e -> getBackLauncherView());
		
	}
	
	
	private void createLobby() {
		//TODO logic implementation for Lobby creation
		model.createLobby(view.getTextField().getText());
		
		//TODO if created back to Launcher
		this.view.stop();
		this.view.resetTextField();
		Main.getMainProgram().getLauncher().start();
	}
	
	private void getBackLauncherView() {
		this.view.stop();
		this.view.resetTextField();
		Main.getMainProgram().getLauncher().start();
	}

}
