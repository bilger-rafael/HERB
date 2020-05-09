package herb.client.ui.game;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import herb.client.Main;
import herb.client.ressources.Card;
import herb.client.ressources.Hand;
import herb.client.ressources.Player;
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.shape.Rectangle;
import javafx.beans.property.ObjectProperty; 
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;


public class GameController extends Controller<GameModel, GameView> {

	private Card playedCard;
	private Trump chosenTrump;
	private int playedCardIndex;
	
	public GameController(GameModel model, GameView view) {
		super(model, view);
		
		ListChangeListener<Card> trickListener = new ListChangeListener<Card>() {
			public void onChanged(Change<? extends Card> c) {
				view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));	
			}
		};
		
		ListChangeListener<Player> myTurnListener = new ListChangeListener<Player>() {
			public void onChanged(Change<? extends Player> p) {
				
				try {
				if(model.getTrickNumber() == 9 && model.getTrickCards().size() == 4) {
					view.updatePointPane(model.getScores());
					//listener stoppen
					}
				}
				catch (Exception e){
					return;
				}
						
				int i = model.getCurrentPlayers().size();
				Player pl = model.getCurrentPlayers().get(i-1);
				view.updatePlayables(pl);
				view.setStartingPlayer();	
				if(pl.equals(model.getPlayers().get(0))) {
					view.setTurn();
				}
			}
		};
				
		ListChangeListener<Trump> trumpListener = new ListChangeListener<Trump>() {
			public void onChanged(Change<? extends Trump> tr) {
				if(model.getTrump().size() > 0) {
					int round = model.getTrump().size();
					chosenTrump = model.getTrump().get(round-1);
					view.updateTrumpInfo(chosenTrump);
				}
			}
		};
		
		model.getTrickCards().addListener(trickListener);
		model.getCurrentPlayers().addListener(myTurnListener);
		model.getStartingPlayers().addListener(myTurnListener);
		model.getTrump().addListener(trumpListener);
		
		if (view.getTrumpChoice().size() >0) 
			view.getTrumpChoice().get(0).setOnMouseClicked(e -> forwardTrump(e));
		if (view.getTrumpChoice().size()>1) 
        	view.getTrumpChoice().get(1).setOnMouseClicked(e -> forwardTrump(e));
        if (view.getTrumpChoice().size()>2) 
        	view.getTrumpChoice().get(2).setOnMouseClicked(e -> forwardTrump(e));
        if (view.getTrumpChoice().size()>3) 
        	view.getTrumpChoice().get(3).setOnMouseClicked(e -> forwardTrump(e));
        if (view.getTrumpChoice().size()>4) 
        	view.getTrumpChoice().get(4).setOnMouseClicked(e -> forwardTrump(e));
        if (view.getTrumpChoice().size()>5) 
        	view.getTrumpChoice().get(5).setOnMouseClicked(e -> forwardTrump(e));
			
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
        
        view.getRevancheButton().setOnAction(e -> startRevanche());
        view.getRevancheButton().setOnAction(e -> quitGame());
        
	}

	// Roesti - identify clicked Card and play if isPlayable
	public void forwardPlayedCard(MouseEvent e){
		
		Rectangle recti = (Rectangle) e.getSource();
		playedCard = model.getMyCards().get(view.getRects().indexOf(recti));	
		
		if (playedCard.isPlayable()) {
		model.playCard(playedCard);
		}
		view.updateImagePatterns();	
	}
	
	public void forwardTrump(MouseEvent f) {	
		// analog oben für jeden Trumpf ein Rectangle. 
		Rectangle recti = (Rectangle) f.getSource();

		chosenTrump = Trump.values()[view.getTrumpChoice().indexOf(recti)];	

		model.setTrump(chosenTrump);
		view.changeTopOfStackPane();
	}
	
	public void quitGame() {
		// TODO send signal to server and go back to launcher
		goToLauncher();
	}

	private void startRevanche() {
		// TODO send signal to server and start a new Round
		enterGame();
	}
	
	private void goToLauncher() {
		this.view.stop();
		Main.getMainProgram().getLauncher().start();
	}
	private void enterGame() {
		this.view.stop();
		Main.getMainProgram().getGameView().start();
	}
}
