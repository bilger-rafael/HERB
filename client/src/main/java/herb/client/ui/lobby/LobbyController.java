package herb.client.ui.lobby;

import herb.client.Main;
import herb.client.ressources.Lobby;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;
import herb.client.utils.ServiceLocator;
import javafx.collections.ListChangeListener;

//Herren
public class LobbyController extends Controller<LobbyModel, LobbyView> {
	private ServiceLocator serviceLocator;
	private ListChangeListener<Player> changeListener;

	public LobbyController(LobbyModel model, LobbyView view) {
		super(model, view);
		// action for cancelButton
		view.getCancelButton().setOnAction(e -> getBackLauncherView());
		// action for botButton
		view.getBotsButton().setOnAction(e -> createViewBot());
		// update the amount of players in a lobby
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("LobbyController controller initialized");
		
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

	// methode for cancelButton
	private void getBackLauncherView() {
		try {
			// if lobby contains less then 4 players, player can leave the lobby
			this.model.getLobby().removePlayer(Datastore.getInstance().getMainPlayer());
			view.getMessage().setVisible(false);
		} catch (ExceptionBase e) {
			// if lobby contains 4 players it is not possible anymore
			view.getMessage().setVisible(true);
			return;
		}
		this.view.stop();
		Main.getMainProgram().getLauncher().start();
	}

	// enter the game
	private void enterGame() {
		// automatically, when 4 players chose that lobby
		Main.getMainProgram().getGameView().start();
		view.getMessageRefresh().setVisible(false);
		view.getMessage().setVisible(false);
		this.view.stop();
	}

	// methode for botButton
	private void createViewBot() {
		view.getMessage().setVisible(false);
		Main.getMainProgram().getBotView(this.model.getLobby()).start();

	}

	private void refreshLobby() {
		try {
			model.refreshLobby();
			view.getMessageRefresh().setVisible(false);
		} catch (ExceptionBase e) {
			view.showErrorRefresh();
			view.getMessageRefresh().setVisible(true);
			e.printStackTrace();
		}
	}

}
