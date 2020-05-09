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

		view.getCancelButton().setOnAction(e -> getBackLauncherView());
		view.getBotsButton().setOnAction(e -> createViewBot());
//		view.getBotsButton().setOnAction(e -> getNewBot());

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

//	private void getNewBot() {
//		try {
//			this.model.getLobby().addBot(false);
//		} catch (ExceptionBase e) {
//			// TODO show error message
//			return;
//		}
//	}

	private void createViewBot() {
		Main.getMainProgram().getBotView(this.model.getLobby()).start();
		
	}

}
