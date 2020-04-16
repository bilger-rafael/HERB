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
	private ArrayList<Card> reducedArray; 
	
	public GameController(GameModel model, GameView view) {
		super(model, view);
		
		reducedArray = new ArrayList();
		
		// Event when card is chosen
		view.getRects().get(0).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(1).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(2).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(3).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(4).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(5).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(6).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(7).setOnMouseClicked(e -> forwardPlayedCard(e));
        view.getRects().get(8).setOnMouseClicked(e -> forwardPlayedCard(e));	
//        if (model.getCurrentCards().size()>1) view.getRects().get(1).setOnMouseClicked(e -> forwardPlayedCard(e));
//        if (model.getCurrentCards().size()>2) view.getRects().get(2).setOnMouseClicked(e -> forwardPlayedCard(e));
//        if (model.getCurrentCards().size()>3) view.getRects().get(3).setOnMouseClicked(e -> forwardPlayedCard(e));
//        if (model.getCurrentCards().size()>4) view.getRects().get(4).setOnMouseClicked(e -> forwardPlayedCard(e));
//        if (model.getCurrentCards().size()>5) view.getRects().get(5).setOnMouseClicked(e -> forwardPlayedCard(e));
//        if (model.getCurrentCards().size()>6) view.getRects().get(6).setOnMouseClicked(e -> forwardPlayedCard(e));
//        if (model.getCurrentCards().size()>7) view.getRects().get(7).setOnMouseClicked(e -> forwardPlayedCard(e));
//        if (model.getCurrentCards().size()>8) view.getRects().get(8).setOnMouseClicked(e -> forwardPlayedCard(e));
	
		// Simulation
		view.getSimulationButton().setOnAction(e -> simulate());
	}
	
	// Roesti - only for testing
	public void simulate() {
			
		reducedArray = model.setMyCards(playedCard);
		
		String writeCardsOut = "Meine Karten...";
		for (int i = 0; i < reducedArray.size(); i++) {
			writeCardsOut += reducedArray.get(i).getSuit();
			writeCardsOut += reducedArray.get(i).getRank();	
			}
		System.out.println();
		System.out.println(writeCardsOut);
		System.out.println();
		view.updateMyCards(reducedArray);
		
		ArrayList<Card> trickAdded = new ArrayList();
		trickAdded = model.setTrick(playedCard);
		
		String writeCardsOutT = "Trick-Karten...";
		for (int i = 0; i < trickAdded.size(); i++) {
			writeCardsOutT += trickAdded.get(i).getSuit();
			writeCardsOutT += trickAdded.get(i).getRank();	
			}
		System.out.println();
		System.out.println(writeCardsOutT);
		System.out.println();
		
		view.updateTrick(trickAdded);
	}
	
	// Roesti - identify clicked Card
	// TODO check if playable
	public void forwardPlayedCard(MouseEvent mouseEvent){
		
		System.out.println();
		System.out.println("It happened");
		System.out.println();
		
		Rectangle recti = (Rectangle) mouseEvent.getSource();
		if ((recti).getFill().equals(view.getRects().get(0).getFill())) {
			playedCard = view.getCardAreas().get(0);
		}
		if ((recti).getFill().equals(view.getRects().get(1).getFill())) {
			playedCard = view.getCardAreas().get(1);
		}
		if ((recti).getFill().equals(view.getRects().get(2).getFill())) {
			playedCard = view.getCardAreas().get(2);
		}
		if ((recti).getFill().equals(view.getRects().get(3).getFill())) {
			playedCard = view.getCardAreas().get(3);
		}
		if ((recti).getFill().equals(view.getRects().get(4).getFill())) {
			playedCard = view.getCardAreas().get(4);
		}
		if ((recti).getFill().equals(view.getRects().get(5).getFill())) {
			playedCard = view.getCardAreas().get(5);
		}
		if ((recti).getFill().equals(view.getRects().get(6).getFill())) {
			playedCard = view.getCardAreas().get(6);
		}
		if ((recti).getFill().equals(view.getRects().get(7).getFill())) {
			playedCard = view.getCardAreas().get(7);
		}	
		if ((recti).getFill().equals(view.getRects().get(8).getFill())) {
			playedCard = view.getCardAreas().get(8);
		}	
		
		System.out.println();	
		System.out.println(playedCard.getSuit().toStringFr()+" "+playedCard.getRank().toStringDE());
		System.out.println();
		
		playedCardIndex = model.getCurrentCards().indexOf(playedCard); 
		System.out.println("Array-Index: "+ playedCardIndex);
	
		model.playCard(playedCard);
	}		
	
}
