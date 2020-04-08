package herb.client.ui.game;

import herb.client.ressources.Card;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Controller;
import javafx.event.EventHandler;

public class GameController extends Controller<GameModel, GameView> {

	public GameController(GameModel model, GameView view) {
		super(model, view);

		// Event when card is chosen
//        view.getPlayedCard().setOnMouseClicked(e -> forwardPlayedCard());
	}
	
	
	public void forwardPlayedCard() {
		//TODO forward Card-Info to Server
		System.out.println("It happened");
	}
	

	

	
	
}
