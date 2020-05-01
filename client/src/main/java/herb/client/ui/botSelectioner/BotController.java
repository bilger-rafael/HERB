package herb.client.ui.botSelectioner;

import herb.client.Main;
import herb.client.ressources.Lobby;
import herb.client.ui.core.Controller;
import herb.client.ui.lobby.LobbyModel;

public class BotController extends Controller<BotModel, BotView> {
	
	Lobby lobby;
	
	public BotController(BotModel model, BotView view) {
		super(model, view);
		
		view.getCancelButton().setOnAction(e -> getBackLobbyView());		
		
	}
	
	private void getBackLobbyView() {
		this.view.stop();
	}

}
