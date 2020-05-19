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

// roesti
public class GameController extends Controller<GameModel, GameView> {

	private Card playedCard;
	private Trump chosenTrump;
	private int playedCardIndex;

	public GameController(GameModel model, GameView view) {
		super(model, view);

		// trick-listener, nextPlayer-listener and trump listener
		ListChangeListener<Card> trickListener = new ListChangeListener<Card>() {
			public void onChanged(Change<? extends Card> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));	
						
//						ArrayList<Card> localTrick = new ArrayList<>();
//						localTrick.add(model.getLocalTrick()[model.getTrickNumber()][0]);
//						localTrick.add(model.getLocalTrick()[model.getTrickNumber()][1]);
//						localTrick.add(model.getLocalTrick()[model.getTrickNumber()][2]);
//						localTrick.add(model.getLocalTrick()[model.getTrickNumber()][3]);
//						view.updateTrick(localTrick);			
						
					//	 try alternative solution
//					view.setStartingPlayer();
//						
//					int trickStatus = model.getTrickCards().size();
//					ArrayList<Card> cards = new ArrayList<>();
//					cards = (ArrayList<Card>) model.getBetterTrickCards().stream().collect(Collectors.toList());						
//					
//					if (model.getStartingPlayer().equals(model.getPlayers().get(0))) {
//							if(trickStatus == 1)  view.updateTMain(cards.get(0));		
//							if(trickStatus == 2)  view.updateTRight(cards.get(1));
//							if(trickStatus == 3)  view.updateTOppo(cards.get(2));
//							if(trickStatus == 4)  view.updateTLeft(cards.get(3));
//							}
//						
//						if (model.getStartingPlayer().equals(model.getPlayers().get(1))) {
//							if(trickStatus == 4)  view.updateTMain(cards.get(3));		
//							if(trickStatus == 1)  view.updateTRight(cards.get(0));
//							if(trickStatus == 2)  view.updateTOppo(cards.get(1));
//							if(trickStatus == 3)  view.updateTLeft(cards.get(2));
//							}
//						
//						if (model.getStartingPlayer().equals(model.getPlayers().get(2))) {
//							if(trickStatus == 3)  view.updateTMain(cards.get(2));		
//							if(trickStatus == 4)  view.updateTRight(cards.get(3));
//							if(trickStatus == 1)  view.updateTOppo(cards.get(0));
//							if(trickStatus == 2)  view.updateTLeft(cards.get(1));
//							}
//						
//						if (model.getStartingPlayer().equals(model.getPlayers().get(3))) {
//							if(trickStatus == 2)  view.updateTMain(cards.get(1));		
//							if(trickStatus == 3)  view.updateTRight(cards.get(2));
//							if(trickStatus == 4)  view.updateTOppo(cards.get(3));
//							if(trickStatus == 1)  view.updateTLeft(cards.get(0));
//							}
//						if(trickStatus == 4) {
//							view.clearTrick();
//						}				
//						System.out.println("StartingPlayer " + model.getStartingPlayer());
//						String sysout = new String();
//						for (Card m : model.getLocalTrick()[model.getTrickNumber()])
//							sysout += m;
					//	System.out.println("localTrick-Array "+ sysout);
//						for (int j = 0; j < model.getBetterTrickCards().size(); j++) {
//							System.out.println("betterTrickCards: " + model.getBetterTrickCards().get(j).getRank().toString()
//									+ model.getBetterTrickCards().get(j).getSuit().toString());
//						}
//						System.out.println();
					}
				}
			}
		};
		ListChangeListener<Player> myTurnListener = new ListChangeListener<Player>() {
			public void onChanged(Change<? extends Player> p) {
				if (model.getTrickNumber() == 9 && model.getTrickCards().size() == 4) {
					// stop all Listeners
					System.out.println("passed here");
					view.updatePointPane(model.getScores());
					model.setStopThread();
					view.cleanings();
				}
				while (p.next()) {
					int i = model.getCurrentPlayers().size();
					Player pl = model.getCurrentPlayers().get(i - 1);
					if (p.wasAdded()) {
						view.updatePlayables(pl);
						view.setStartingPlayer();
						if (pl.equals(model.getPlayers().get(0))) {
							view.setTurn();
						}
					}
				}
			}
		};
		ListChangeListener<Trump> trumpListener = new ListChangeListener<Trump>() {
			public void onChanged(Change<? extends Trump> tr) {
				if (model.getTrump().size() > 0) {
					int round = model.getTrump().size();
					chosenTrump = model.getTrump().get(round - 1);
					view.updateTrumpInfo(chosenTrump);
					model.setStopTrumpThread();
				}
			}
		};
		model.getTrickCards().addListener(trickListener);
		model.getCurrentPlayers().addListener(myTurnListener);
		model.getStartingPlayers().addListener(myTurnListener);
		model.getTrump().addListener(trumpListener);

		// handling trump choice
		view.getTrumpChoice().get(0).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(1).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(2).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(3).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(4).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(5).setOnMouseClicked(e -> forwardTrump(e));

		// handling played card
		// TODO - check: remove last rectangle every time a card was played?
		view.getRects().get(0).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 1)
			view.getRects().get(1).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 2)
			view.getRects().get(2).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 3)
			view.getRects().get(3).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 4)
			view.getRects().get(4).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 5)
			view.getRects().get(5).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 6)
			view.getRects().get(6).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 7)
			view.getRects().get(7).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 8)
			view.getRects().get(8).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 9)
			view.getRects().get(9).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 10)
			view.getRects().get(10).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 11)
			view.getRects().get(11).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 12)
			view.getRects().get(12).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 13)
			view.getRects().get(13).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 14)
			view.getRects().get(14).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 15)
			view.getRects().get(15).setOnMouseClicked(e -> forwardPlayedCard(e));
		if (view.getRects().size() > 16)
			view.getRects().get(16).setOnMouseClicked(e -> forwardPlayedCard(e));

		// handling end of a round
		view.getRevancheButton().setOnAction(e -> startRevanche());
		view.getQuitButton().setOnAction(e -> quitGame());

		// change card set
		view.getFrenchOption().setOnAction(e -> changeCardSet2French());
		view.getGermanOption().setOnAction(e -> changeCardSet2German());
	}

	// identify clicked Card and play if isPlayable
	public void forwardPlayedCard(MouseEvent e) {

		Rectangle recti = (Rectangle) e.getSource();
		int index = ((view.getRects().indexOf(recti) / 2 - view.getStartingPosition() / 2));
		playedCard = model.getMyCards().get((index));

		if (playedCard.isPlayable()) {
			model.playCard(playedCard);
		}
		view.updateImagePatterns();

		// reduce other players cards
		// TODO - check and correct
		view.updateRightPlayer();
		view.updateOppoPlayer();
		view.updateLeftPlayer();
	}

	public void forwardTrump(MouseEvent f) {
		Rectangle recti = (Rectangle) f.getSource();
		chosenTrump = Trump.values()[view.getTrumpChoice().indexOf(recti)];
		model.setTrump(chosenTrump);
		view.changeTopOfStackPane();
	}

	public void changeCardSet2French() {
		model.setCardSet("Fr");
		view.updateImagePatterns();
		view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
		view.updateTrumpOptions();
	}

	public void changeCardSet2German() {
		model.setCardSet("De");
		view.updateImagePatterns();
		view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
		view.updateTrumpOptions();
	}

	public void quitGame() {
		model.demandRematch(false);
		goToLauncher();
	}

	private void startRevanche() {
		model.demandRematch(true);
		model.startRematchUpdater();
		model.getRematch().addListener((obs, oldVal, newVal) -> {
			model.stopRematchUpdater();
			enterGame();
		});

		// TODO When Revanche is pressed, the button should not be pressable anymore
		// (maybe show Text that we have to wait until the other players decided)
	}

	private void goToLauncher() {
		this.view.stop();
		Main.getMainProgram().getLauncher().start();
	}

	private void enterGame() {
		this.view.stop();
		// TODO either create view again from scratch or reset view
		this.view = null;
		Main.getMainProgram().getGameView().start();
	}
}
