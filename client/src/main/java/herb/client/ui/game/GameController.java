package herb.client.ui.game;

import javafx.scene.input.MouseEvent;
import java.io.IOException;

import herb.client.ressources.Card;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Controller;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.shape.Rectangle;


public class GameController extends Controller<GameModel, GameView> {

	Card playedCard;
	
	public GameController(GameModel model, GameView view) {
		super(model, view);

		// Simulation
		view.getSimulationButton().setOnAction(e -> simulate());
		
		// Event when card is chosen
        view.getPlayedCard1().setOnMouseClicked(e -> forwardPlayedCard(e));
		view.getPlayedCard2().setOnMouseClicked(e -> forwardPlayedCard(e));	
		view.getPlayedCard3().setOnMouseClicked(e -> forwardPlayedCard(e));
		view.getPlayedCard4().setOnMouseClicked(e -> forwardPlayedCard(e));
		view.getPlayedCard5().setOnMouseClicked(e -> forwardPlayedCard(e));
		view.getPlayedCard6().setOnMouseClicked(e -> forwardPlayedCard(e));
		view.getPlayedCard7().setOnMouseClicked(e -> forwardPlayedCard(e));
		view.getPlayedCard8().setOnMouseClicked(e -> forwardPlayedCard(e));
		view.getPlayedCard9().setOnMouseClicked(e -> forwardPlayedCard(e));
	}
	
	public void simulate() {
	//	updateMyCards();
	//	updateTrick();
	}
	
	public Card forwardPlayedCard(MouseEvent mouseEvent){
		//TODO forward Card-Info to Server
		System.out.println("It happened");
		
		Rectangle recti = (Rectangle) mouseEvent.getSource();
				
//		System.out.println((recti).getFill());
//		System.out.println(view.getPlayedCard1().getFill());
		
		if ((recti).getFill().equals(view.getPlayedCard1().getFill())) {
			playedCard = view.getCardAreas().get(0);
		}
		if ((recti).getFill().equals(view.getPlayedCard2().getFill())) {
			playedCard = view.getCardAreas().get(1);
		}
		if ((recti).getFill().equals(view.getPlayedCard3().getFill())) {
			playedCard = view.getCardAreas().get(2);
		}
		if ((recti).getFill().equals(view.getPlayedCard4().getFill())) {
			playedCard = view.getCardAreas().get(3);
		}
		if ((recti).getFill().equals(view.getPlayedCard5().getFill())) {
			playedCard = view.getCardAreas().get(4);
		}
		if ((recti).getFill().equals(view.getPlayedCard6().getFill())) {
			playedCard = view.getCardAreas().get(5);
		}
		if ((recti).getFill().equals(view.getPlayedCard7().getFill())) {
			playedCard = view.getCardAreas().get(6);
		}
		if ((recti).getFill().equals(view.getPlayedCard8().getFill())) {
			playedCard = view.getCardAreas().get(7);
		}	
		if ((recti).getFill().equals(view.getPlayedCard9().getFill())) {
			playedCard = view.getCardAreas().get(8);
		}	
			
		System.out.println(playedCard.getSuit().toStringFr()+" "+playedCard.getRank().toStringDE());
		
		return playedCard;
	}		
	
}
