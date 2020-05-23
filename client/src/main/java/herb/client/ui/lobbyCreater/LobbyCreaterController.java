package herb.client.ui.lobbyCreater;

import herb.client.Main;
import herb.client.ui.core.Controller;
import herb.client.utils.ServiceLocator;

//Herren
public class LobbyCreaterController extends Controller<LobbyCreaterModel, LobbyCreaterView> {

	public LobbyCreaterController(LobbyCreaterModel model, LobbyCreaterView view) {
		super(model, view);

		// action for okButton
		view.getOkButton().setOnAction(e -> createLobby());
		// if textField is empty okButton is disabled
		view.getOkButton().disableProperty().bind(view.getTextField().textProperty().isEmpty());
		// action for cancelButton
		view.getCancelButton().setOnAction(e -> getBackLauncherView());

	}

	// methode for okButton
	private void createLobby() {
		try {
			// if lobby name does not exist, create a new lobby
			model.createLobby(view.getTextField().getText());
			this.view.resetTextField();
			view.getMessage().setVisible(false);
			this.view.stop();
		} catch (Exception e) {
			// if lobby does already exist throw exception
			view.showError();
			view.getMessage().setVisible(true);
		}

	}

	// methode for cancelButton
	private void getBackLauncherView() {
		this.view.stop();
		this.view.resetTextField();
		view.getMessage().setVisible(false);
		Main.getMainProgram().getLauncher().start();
	}

}
