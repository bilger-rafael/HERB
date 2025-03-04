package herb.client.ui.game;

import herb.client.Main;
import herb.client.ressources.Card;
import herb.client.ressources.Player;
import herb.client.ressources.Trick;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Controller;
import herb.client.utils.Datastore;
import herb.client.utils.ServiceLocator;
import javafx.collections.ListChangeListener;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.stream.Collectors;


//  roesti
public class GameController extends Controller<GameModel, GameView> {

	private Card playedCard;
	private Trump chosenTrump;
	private ServiceLocator serviceLocator;
	private Trick trick; 
	private Thread tu;
	private volatile boolean currentStop = false;
	
	public GameController(GameModel model, GameView view) {
		super(model, view);

		startPlayablesUpdater();
		
		serviceLocator = ServiceLocator.getInstance();
		serviceLocator.getLogger().info("GameController initialized");

		// trickListener updates the trickCards in view
		ListChangeListener<Card> trickListener = new ListChangeListener<Card>() {
			public void onChanged(Change<? extends Card> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						ArrayList<Card> filter = (ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList());
						view.updateTrick(filter);	
			}}}
		};
		//  myTurnListener updates the starting player in view
		//  updates the playable cards and the label 'it's your turn' of MainPlayer when his turn comes
		//  shows the points after the last trick
		ListChangeListener<Player> myTurnListener = new ListChangeListener<Player>() {
			public void onChanged(Change<? extends Player> p) {
						
				if (model.getTrickNumber() == 9 && model.getTrickCards().size() == 4) {
					view.updatePointPane(model.getScores());
					model.setStopThread();
					setCurrentStopThread();
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
			}}}}
		};
		//  trumpListener waits for chosen trump and is then stopped
		ListChangeListener<Trump> trumpListener = new ListChangeListener<Trump>() {
			public void onChanged(Change<? extends Trump> tr) {
				if (model.getTrump().size() > 0) {
					int round = model.getTrump().size();
					chosenTrump = model.getTrump().get(round - 1);
					view.updateTrumpInfo(chosenTrump);
					model.setStopTrumpThread();
			}}
		};
		model.getTrickCards().addListener(trickListener);
		model.getCurrentPlayers().addListener(myTurnListener);
		model.getStartingPlayers().addListener(myTurnListener);
		model.getTrump().addListener(trumpListener);

		// handling trump choice of MainPlayer
		view.getTrumpChoice().get(0).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(1).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(2).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(3).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(4).setOnMouseClicked(e -> forwardTrump(e));
		view.getTrumpChoice().get(5).setOnMouseClicked(e -> forwardTrump(e));

		// handling played card of MainPlayer
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

		// handling end of a round - user choices 'revanche' or 'quit'
		view.getRevancheButton().setOnAction(e -> startRevanche());
		view.getQuitButton().setOnAction(e -> quitGame());

		// change card set if changed via menu option
		view.getFrenchOption().setOnAction(e -> changeCardSet2French());
		view.getGermanOption().setOnAction(e -> changeCardSet2German());
	}

	// identify clicked Card and play if isPlayable, update MainPlayers cards
	public void forwardPlayedCard(MouseEvent e) {
		try {
			Rectangle recti = (Rectangle) e.getSource();
			int index = ((view.getRects().indexOf(recti) / 2 - view.getStartingPosition() / 2));
			playedCard = model.getMyCards().get((index));
		}
		catch(Exception error) {
			view.showErrorMessage();
			view.getMessageLabel().setVisible(true);
			serviceLocator.getLogger().info("wrong choice - playCard");
		}
		if (playedCard.isPlayable()) {
			playCard(playedCard);
			view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
		}
		view.updateImagePatterns();
				
		// reduce other players cards
		view.updateRightPlayer();
		view.updateOppoPlayer();
		view.updateLeftPlayer();
	}
	
	//  send played card to server
	public Card playCard(Card playedCard) {
		try {
			Datastore.getInstance().getMainPlayer().play(playedCard);
		} catch (ExceptionBase e) {
			view.showErrorMessage();
			view.getMessageLabel().setVisible(true);
			serviceLocator.getLogger().info("connection problems- playCard");
			e.printStackTrace();
		}
		return playedCard;
	}

	//  identify chosen trump, show trump in view and put trumpChoice panel to back
	public void forwardTrump(MouseEvent f) {
		Rectangle recti = (Rectangle) f.getSource();
		chosenTrump = Trump.values()[view.getTrumpChoice().indexOf(recti)];
		setTrump(chosenTrump);
		view.changeTopOfStackPane();
	}
	
	//  send chosen trump to server
	public Trump setTrump(Trump chosenTrump) {
		try {
			Datastore.getInstance().getMainPlayer().chooseTrump(chosenTrump);
		} catch (ExceptionBase e) {
			view.showErrorMessage();
			view.getMessageLabel().setVisible(true);
			serviceLocator.getLogger().info("connection problems- trump");
			e.printStackTrace();
		}
		return chosenTrump;
	}

	//  update ImagePatterns of cards and shown trump image - frenchSet
	public void changeCardSet2French() {
		model.setCardSet("Fr");
		view.updateImagePatterns();
		view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
		view.updateTrumpOptions();
		view.updateLeftPlayer();
		view.updateOppoPlayer();
		view.updateRightPlayer();
		view.updateTrumpInfo(chosenTrump);
	}

	//  update ImagePatterns of cards and shown trump image - germanSet
	public void changeCardSet2German() {
		model.setCardSet("De");
		view.updateImagePatterns();
		view.updateTrick((ArrayList<Card>) model.getTrickCards().stream().collect(Collectors.toList()));
		view.updateTrumpOptions();
		view.updateLeftPlayer();
		view.updateOppoPlayer();
		view.updateRightPlayer();
		view.updateTrumpInfo(chosenTrump);
	}

	// open launcher
	public void quitGame() {
		demandRematch(false);
		goToLauncher();
	}

	// set up next round in same lobby 
	private void startRevanche() {
		demandRematch(true);
		model.startRematchUpdater();		
		model.getRematch().addListener((obs, oldVal, newVal) -> {
			model.stopRematchUpdater();
			enterGame();
		});
		view.setRevancheLabel();
		// When Revanche is pressed, the button should not be pressable anymore
		view.getRevancheButton().disableProperty();
	}

	private void goToLauncher() {
		this.view.stop();
		Main.getMainProgram().getLauncher().start();
	}

	// start next round in same lobby
	private void enterGame() {
		try {
			Thread.sleep(5000);
		}
		catch(Exception e) { 
		}
		this.view.stop();
		Main.getMainProgram().clearGameView();
		Main.getMainProgram().getGameView().start();
	}
	
	public void demandRematch(Boolean rematch) {
		try {
			Datastore.getInstance().getMainPlayer().demandRematch(rematch);
			view.getMessageLabelS().setVisible(false);
		} catch (ExceptionBase e) {
			view.showErrorMessageS();
			view.getMessageLabelS().setVisible(true);
			serviceLocator.getLogger().info("connection lost- gameOver");
			e.printStackTrace();
		}
	}
	
	// updates current player to know when playable cards must be updated and label 'it's your turn' should be set
	public void refreshCurrentPlayer() {
		try {
			trick = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast();
			model.setTrickNumber(Datastore.getInstance().getMainPlayer().getRound().getTricks().size());
			if (model.getCurrentPlayers().isEmpty() || trick.getCurrentPlayer() != model.getCurrentPlayers().get(model.getCurrentPlayers().size() - 1)) {
				model.getCurrentPlayers().add(trick.getCurrentPlayer());
				model.getStartingPlayers().add(trick.getStartingPlayer());
				}
			view.getMessageLabelS().setVisible(false);
			} catch (Exception e) {
			// if trump is not yet defined, no message
			if(Datastore.getInstance().getMainPlayer().getRound().getTrump() != null) {
				view.showErrorMessageS();
				view.getMessageLabelS().setVisible(true);
				serviceLocator.getLogger().info("connection lost- gameInterrupted");
			}
		}
	}
	
	// start Thread PlayablesUpdater for current playable cards
	private void startPlayablesUpdater() {
		Runnable ru = new Runnable() {
			@Override
			public void run() {
				while (!currentStop) {
					Platform.runLater(() -> refreshCurrentPlayer());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
			}}}
		};
		tu = new Thread(ru);
		tu.setDaemon(true);
		tu.start();
	}
	
	public void setCurrentStopThread() {
		this.currentStop = true;
	}
}
