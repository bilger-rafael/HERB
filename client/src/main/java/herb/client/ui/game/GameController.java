package herb.client.ui.game;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import herb.client.ressources.Card;
import herb.client.ressources.Hand;
import herb.client.ressources.Trick;
import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.HandBase;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.TrickBase;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;
import javafx.event.EventHandler;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.scene.shape.Rectangle;


public class GameController extends Controller<GameModel, GameView> {

	private Card playedCard;
	private int playedCardIndex;
	
	public GameController(GameModel model, GameView view) {
		super(model, view);
		
		ListChangeListener<Card> changeListener = new ListChangeListener<Card>() {
			public void onChanged(Change<? extends Card> c) {
				view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
				view.updatePlayables();
				
				if ((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()) == null) {
					view.updatePointPane();
				}
			}
		};
		
		model.getTrickCards().addListener(changeListener);

//		ListChangeListener<Card> changeListenerPlayables = new ListChangeListener<Card>() {
//			public void onChanged(Change<? extends Card> c) {
//				view.updatePlayables();
//			}
//		};
//		model.getPlayables().addListener(changeListenerPlayables);	

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
	
	// Roesti - identify clicked Card and play if isPlayable
	public void forwardPlayedCard(MouseEvent e){
		
		Rectangle recti = (Rectangle) e.getSource();
		if ((recti).getFill().equals(view.getRects().get(0).getFill())) {
			playedCard = model.getMyCards().get(0);
		}
		
		if (view.getRects().size()>1) {
			if ((recti).getFill().equals(view.getRects().get(1).getFill())) {
				playedCard = model.getMyCards().get(1);
			}
		}
		if (view.getRects().size()>2) {
			if ((recti).getFill().equals(view.getRects().get(2).getFill())) {
				playedCard = model.getMyCards().get(2);
			}
		}
		if (view.getRects().size()>3) {
			if ((recti).getFill().equals(view.getRects().get(3).getFill())) {
				playedCard = model.getMyCards().get(3);
			}
		}
		if (view.getRects().size()>4) {
			if ((recti).getFill().equals(view.getRects().get(4).getFill())) {
				playedCard = model.getMyCards().get(4);
			}
		}
		if (view.getRects().size()>5) {
			if ((recti).getFill().equals(view.getRects().get(5).getFill())) {
				playedCard = model.getMyCards().get(5);
			}
		}
		if (view.getRects().size()>6) {
			if ((recti).getFill().equals(view.getRects().get(6).getFill())) {
				playedCard = model.getMyCards().get(6);
			}
		}
		if (view.getRects().size()>7) {
			if ((recti).getFill().equals(view.getRects().get(7).getFill())) {
				playedCard = model.getMyCards().get(7);
			}	
		}
		if (view.getRects().size()>8) {
			if ((recti).getFill().equals(view.getRects().get(8).getFill())) {
				playedCard = model.getMyCards().get(8);
			}
		}	
		
		if (playedCard.isPlayable()) {
		model.playCard(playedCard);
		}
		view.updateImagePatterns();	
	}		
}
