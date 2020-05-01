package herb.client.ui.botSelectioner;

import herb.client.Main;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;

public class BotController extends Controller<BotModel, BotView> {

	public BotController(BotModel model, BotView view) {
		super(model, view);
		
		view.getCancelButton().setOnAction(e -> getBackLobbyView());
		
//		view.getOkButton().setOnAction(e -> getNewBot());
		
	}
	
	private void getBackLobbyView() {
		this.view.stop();
	}

//	private void getNewBot() {
//		this.view.stop();
//	}

}
