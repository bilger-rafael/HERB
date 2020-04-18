package herb.client.ui.lobby;

import herb.client.Main;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;
import javafx.collections.ListChangeListener;

public class LobbyController extends Controller<LobbyModel, LobbyView> {
	private ListChangeListener<Player> changeListener;

	public LobbyController(LobbyModel model, LobbyView view) {
		super(model, view);

		// enter the game directly - finally waiting for 4 players
		view.getSkipButton().setOnAction(e -> enterGame());

		view.getCancelButton().setOnAction(e -> getBackLauncherView());

		changeListener = new ListChangeListener<Player>() {
			public void onChanged(Change<? extends Player> c) {
				if (model.getLobby().isFull()) {
					model.getPlayers().removeListener(changeListener);
					enterGame();
				}
			}
		};

		model.getPlayers().addListener(changeListener);

	}

	private void getBackLauncherView() {
		try {
			this.model.getLobby().removePlayer(Datastore.getInstance().getMainPlayer());
		} catch (ExceptionBase e) {
			// TODO if exception occurs, DONT LEAVE, bc game is already started
			return;
		}
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
