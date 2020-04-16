package herb.client.ui.lobby;

import herb.client.Main;
import herb.client.ressources.Player;
import herb.client.ui.core.Controller;
import javafx.collections.ListChangeListener;

public class LobbyController extends Controller<LobbyModel, LobbyView> {

	public LobbyController(LobbyModel model, LobbyView view) {
		super(model, view);
		
		// enter the game directly - finally waiting for 4 players
		view.getSkipButton().setOnAction(e -> enterGame());
		
		view.getCancelButton().setOnAction(e -> getBackLauncherView());
		
		model.getPlayers().addListener((ListChangeListener.Change<? extends Player> c) -> {
			if(this.model.getLobby().isFull()) enterGame();
		});
		
	}
	
	private void getBackLauncherView() {
			this.view.stop();
			Main.getMainProgram().getLauncher().start();
	}

	// enter the game - must be programmed differently in the end
	private void enterGame() {
		// automatically, when 4 players chose that lobby TODO
		Main.getMainProgram().getGameView().start();
		this.view.stop();
	}

}
