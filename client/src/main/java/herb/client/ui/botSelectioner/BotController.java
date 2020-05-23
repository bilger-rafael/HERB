package herb.client.ui.botSelectioner;

import herb.client.Main;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;

//Anahi
public class BotController extends Controller<BotModel, BotView> {

	public BotController(BotModel model, BotView view) {
		super(model, view);

		// action for cancelButton
		view.getCancelButton().setOnAction(e -> getBackLobbyView());
		// action for okButton
		view.getOkButton().setOnAction(e -> startSelectedBot());
		// if no radioButton is selected then okButton is disabled
		view.getOkButton().disableProperty().bind(view.getToggleGroup().selectedToggleProperty().isNull());

	}
	//mehtode for cancelButton
	private void getBackLobbyView() {
		view.getMessage().setVisible(false);
		this.view.stop();
		Main.getMainProgram().clearBotView();
	}
	//mehtode for okButton
	private void startSelectedBot() {
		if (view.getEasyRadioButton().isSelected()) {
			getEasyBot();
		}
		if (view.getHeavyRadioButton().isSelected()) {
			getAdvancedBot();
		}
	}
	//if easy bot is selected
	private void getEasyBot() {
		try {
			this.model.getLobby().addBot(false);
			view.getMessage().setVisible(false);
		} catch (ExceptionBase e) {
			view.getMessage().setVisible(true);
		}
		getBackLobbyView();
	}
	//if advanced bot is selected
	private void getAdvancedBot() {
		try {
			this.model.getLobby().addBot(true);
			view.getMessage().setVisible(false);
		} catch (Exception e) {
			view.getMessage().setVisible(true);
		}
		getBackLobbyView();
	}

}
