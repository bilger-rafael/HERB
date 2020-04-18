package herb.client.ui.game;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import herb.client.ressources.Card;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Controller;
import javafx.event.EventHandler;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.Event;
import javafx.scene.shape.Rectangle;


public class GameController extends Controller<GameModel, GameView> {

	private Card playedCard;
	private int playedCardIndex;
	private ArrayList<Card> reducedArray; 
	
	public GameController(GameModel model, GameView view) {
		super(model, view);
		
		ListChangeListener<Card> changeListener = new ListChangeListener<Card>() {
			public void onChanged(Change<? extends Card> c) {
				view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
			}
		};
			
		model.getTrickCards().addListener(changeListener);
				
		// Event when card is chosen
		view.getRects().get(0).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>1) 
        	view.getRects().get(1).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>2) 
        	view.getRects().get(2).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>3) 
        	view.getRects().get(3).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>4) 
        	view.getRects().get(4).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>5) 
        	view.getRects().get(5).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>6) 
        	view.getRects().get(6).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>7) 
        	view.getRects().get(7).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>8) 
        	view.getRects().get(8).setOnMouseClicked(e -> forwardPlayedCard(e));
	}
	
	// Roesti - identify clicked Card
	// TODO check if playable
	public void forwardPlayedCard(MouseEvent e){
		
		System.out.println();
		System.out.println("It happened");
		System.out.println();
		
		Rectangle recti = (Rectangle) e.getSource();
		
		if ((recti).getFill().equals(view.getRects().get(0).getFill())) {
			playedCard = model.getCurrentCards().get(0);
		}
		
		if (view.getRects().size()>1) {
		if ((recti).getFill().equals(view.getRects().get(1).getFill())) {
			playedCard = model.getCurrentCards().get(1);
		}
	}
	if (view.getRects().size()>2) {
		if ((recti).getFill().equals(view.getRects().get(2).getFill())) {
			playedCard = model.getCurrentCards().get(2);
		}
	}
	if (view.getRects().size()>3) {
		if ((recti).getFill().equals(view.getRects().get(3).getFill())) {
			playedCard = model.getCurrentCards().get(3);
		}
	}
	if (view.getRects().size()>4) {
		if ((recti).getFill().equals(view.getRects().get(4).getFill())) {
			playedCard = model.getCurrentCards().get(4);
		}
	}
	if (view.getRects().size()>5) {
		if ((recti).getFill().equals(view.getRects().get(5).getFill())) {
			playedCard = model.getCurrentCards().get(5);
		}
	}
	if (view.getRects().size()>6) {
		if ((recti).getFill().equals(view.getRects().get(6).getFill())) {
			playedCard = model.getCurrentCards().get(6);
		}
	}
	if (view.getRects().size()>7) {
		if ((recti).getFill().equals(view.getRects().get(7).getFill())) {
			playedCard = model.getCurrentCards().get(7);
		}	
	}
	if (view.getRects().size()>8) {
		if ((recti).getFill().equals(view.getRects().get(8).getFill())) {
			playedCard = model.getCurrentCards().get(8);
		}}	
		
		System.out.println();	
		System.out.println(playedCard.getSuit().toStringFr()+" "+playedCard.getRank().toStringDE());
		playedCardIndex = model.getCurrentCards().indexOf(playedCard); 
		System.out.println("Array-Index: "+ playedCardIndex);
		
		model.playCard(playedCard);
	}		
	
}
