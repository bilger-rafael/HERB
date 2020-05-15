package herb.client.ui.lobbyCreater;

import herb.client.Main;
import herb.client.ui.core.Controller;
import herb.client.utils.ServiceLocator;

public class LobbyCreaterController extends Controller<LobbyCreaterModel, LobbyCreaterView>{
	ServiceLocator serviceLocator;
	public LobbyCreaterController(LobbyCreaterModel model, LobbyCreaterView view) {
		super(model, view);
	
		// Create Lobby
		view.getOkButton().setOnAction(e -> createLobby());
		
		view.getOkButton().disableProperty().bind(view.getTextField().textProperty().isEmpty());
		
		view.getCancelButton().setOnAction(e -> getBackLauncherView());
		
	}
	
	
	private void createLobby() {
		try {
		model.createLobby(view.getTextField().getText());
		this.view.resetTextField();
		this.view.stop();
		}catch(Exception e) {
			view.showError();
			view.getMessage().setVisible(true);
			//			serviceLocator.getLogger().info("Lobby existiert bereits.");
		}
		
	}
	
	private void getBackLauncherView() {
		this.view.stop();
		this.view.resetTextField();
		view.getMessage().setVisible(false);
		Main.getMainProgram().getLauncher().start();
	}

}
