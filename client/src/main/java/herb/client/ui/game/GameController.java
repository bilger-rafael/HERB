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
				while(c.next()) {
					if (c.wasAdded()) {
				view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));	
				//System.out.println(c.toString() + "is played");			
				}
				}
				}
				
		};
		
		ListChangeListener<Player> myTurnListener = new ListChangeListener<Player>() {
			public void onChanged(Change<? extends Player> p) {
				
				if(model.getTrickNumber() == 9 && model.getTrickCards().size() == 4) {
					//stop all Listeners
					System.out.println("passed here");
					view.updatePointPane(model.getScores());
					model.setStopThread();
					view.removeTurn();
				}
				
				while(p.next()){
					
					int i = model.getCurrentPlayers().size();
					Player pl = model.getCurrentPlayers().get(i-1);
					
					if (p.wasAdded()) {
						System.out.println(p.getAddedSubList().toString() + "is next Player.");
						
						view.updatePlayables(pl);
						view.setStartingPlayer();	
						
						System.out.println("CurrentPlayer is: "+ pl.getUsername().toString());
						System.out.println("StartingPlayer is: "+ model.getStartingPlayer().getUsername().toString());
						System.out.println();
						
						}
					
						if(p.wasUpdated()) {

							System.out.println(p.getAddedSubList().toString() + "is next updated Player.");

							if(pl.equals(model.getPlayers().get(0))) {
								view.setTurn();
							}
						}
				
					}
				}
		};
				
		ListChangeListener<Trump> trumpListener = new ListChangeListener<Trump>() {
			public void onChanged(Change<? extends Trump> tr) {
				if(model.getTrump().size() > 0) {
					int round = model.getTrump().size();
					chosenTrump = model.getTrump().get(round-1);
					view.updateTrumpInfo(chosenTrump);
					model.setStopTrumpThread();
				}
			}
		};
		
		model.getTrickCards().addListener(trickListener);
		model.getCurrentPlayers().addListener(myTurnListener);
	//	model.getStartingPlayers().addListener(myTurnListener);
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
        if (view.getRects().size()>9) 
        	view.getRects().get(9).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>10) 
        	view.getRects().get(10).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>11) 
        	view.getRects().get(11).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>12) 
        	view.getRects().get(12).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>13) 
        	view.getRects().get(13).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>14) 
        	view.getRects().get(14).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>15) 
        	view.getRects().get(15).setOnMouseClicked(e -> forwardPlayedCard(e));
        if (view.getRects().size()>16) 
        	view.getRects().get(16).setOnMouseClicked(e -> forwardPlayedCard(e));
        
        view.getRevancheButton().setOnAction(e -> startRevanche());
        view.getQuitButton().setOnAction(e -> quitGame());
        
        view.getFrenchOption().setOnAction(e -> changeCardSet2French());
        view.getGermanOption().setOnAction(e -> changeCardSet2German());
	}

	// Roesti - identify clicked Card and play if isPlayable
	public void forwardPlayedCard(MouseEvent e){
		
		Rectangle recti = (Rectangle) e.getSource();
		int index = ((view.getRects().indexOf(recti)/2-view.getStartingPosition()/2));
		playedCard = model.getMyCards().get((index));	
		
		if (playedCard.isPlayable()) {
		model.playCard(playedCard);
		}
		view.updateImagePatterns();	
		
		//check
		view.updateRightPlayer();
		view.updateOppoPlayer();
		view.updateLeftPlayer();
		
		
	}
	
	public void forwardTrump(MouseEvent f) {	
		// analog oben für jeden Trumpf ein Rectangle. 
		Rectangle recti = (Rectangle) f.getSource();

		chosenTrump = Trump.values()[view.getTrumpChoice().indexOf(recti)];	

		model.setTrump(chosenTrump);
		view.changeTopOfStackPane();
	}
	
	public void changeCardSet2French() {
		model.setCardSet("Fr");
		view.updateImagePatterns();
		view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
	}
	public void changeCardSet2German() {
		model.setCardSet("De");
		view.updateImagePatterns();
		view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
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
