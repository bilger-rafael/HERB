package herb.client.ui.game;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import herb.client.ressources.Card;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Controller;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.shape.Rectangle;


public class GameController extends Controller<GameModel, GameView> {

	private Card playedCard;
	private int playedCardIndex;
	
	public GameController(GameModel model, GameView view) {
		super(model, view);
		
		// Event when card is chosen
        view.getPlayedCard1().setOnMouseClicked(e -> forwardPlayedCard(e));
        if (model.getMyCards().size()>1) view.getPlayedCard2().setOnMouseClicked(e -> forwardPlayedCard(e));
        if (model.getMyCards().size()>2) {view.getPlayedCard3().setOnMouseClicked(e -> forwardPlayedCard(e));}
        if (model.getMyCards().size()>3) {view.getPlayedCard4().setOnMouseClicked(e -> forwardPlayedCard(e));}
        if (model.getMyCards().size()>4) {view.getPlayedCard5().setOnMouseClicked(e -> forwardPlayedCard(e));}
        if (model.getMyCards().size()>5) view.getPlayedCard6().setOnMouseClicked(e -> forwardPlayedCard(e));
        if (model.getMyCards().size()>6) view.getPlayedCard7().setOnMouseClicked(e -> forwardPlayedCard(e));
        if (model.getMyCards().size()>7) view.getPlayedCard8().setOnMouseClicked(e -> forwardPlayedCard(e));
        if (model.getMyCards().size()>8) view.getPlayedCard9().setOnMouseClicked(e -> forwardPlayedCard(e));
	
		// Simulation
		view.getSimulationButton().setOnAction(e -> simulate());
	}
	
	public void simulate() {
		
		ArrayList<Card> reducedArray = new ArrayList();
	//	reducedArray = model.setMyCards(this.playedCardIndex);
		reducedArray = model.setMyCards(playedCard);
		
		String writeCardsOut = "Array-Karten...";
		for (int i = 0; i < 9; i++) {
			writeCardsOut += reducedArray.get(i).getSuit();
			writeCardsOut += reducedArray.get(i).getRank();	
			}
		System.out.println(writeCardsOut);
	//	updateMyCards();
	//	updateTrick();
	}
	
	public Card forwardPlayedCard(MouseEvent mouseEvent){
		//TODO forward Card-Info to Server
		System.out.println("It happened");
		
		Rectangle recti = (Rectangle) mouseEvent.getSource();
					
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
		
		Rank r1 = Rank.Six;
		Suit s1 = Suit.Hearts;
		Trump t1 = Trump.TopsDown;
		Card c1 = new Card(s1, r1, t1);

		playedCardIndex = model.getCurrentCards().indexOf(c1); 
		System.out.println("Array-Index: "+ playedCardIndex);
				
		return playedCard;
	}		
	
}
