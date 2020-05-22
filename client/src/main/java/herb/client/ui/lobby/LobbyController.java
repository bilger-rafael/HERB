package herb.client.ui.lobby;

import herb.client.Main;
import herb.client.ressources.Lobby;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;
import javafx.collections.ListChangeListener;

//Herren
public class LobbyController extends Controller<LobbyModel, LobbyView> {
	private ListChangeListener<Player> changeListener;

	public LobbyController(LobbyModel model, LobbyView view) {
		super(model, view);
		//action for cancelButton
		view.getCancelButton().setOnAction(e -> getBackLauncherView());
		//action for botButton
		view.getBotsButton().setOnAction(e -> createViewBot());
		//update the amount of players in a lobby
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
	//methode for cancelButton
	private void getBackLauncherView() {
		try {
			//if lobby contains less then 4 players, player can leave the lobby
			this.model.getLobby().removePlayer(Datastore.getInstance().getMainPlayer());
		} catch (ExceptionBase e) {
			//if lobby contains 4 players it is not possible anymore
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
		this.view.stop();
	}
	//methode for botButton
	private void createViewBot() {
		Main.getMainProgram().getBotView(this.model.getLobby()).start();

	}
	
	private void refreshLobby() {
		try {
			model.refreshLobby();
		} catch (ExceptionBase e) {
			view.showError();
			e.printStackTrace();
		}
	}

}
