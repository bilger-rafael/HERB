package herb.client.ui.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import herb.client.ressources.Card;
import herb.client.ressources.Player;
import herb.client.ressources.Trick;
import herb.client.ressources.core.ExceptionBase;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Model;
import herb.client.utils.Datastore;
import herb.client.utils.ServiceLocator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameModel extends Model {
	private Player[] plys;
	private ArrayList<Card> currentCards;
	private ObservableList<Card> trickCards = FXCollections.observableArrayList();

	public GameModel() {
		super();
		startTrickUpdater();
	}
	
	public ObservableList<Card> getTrickCards() {
		return trickCards;
	}
	
	// Bilger - Input von Server
	// Roesti - TODO - check, that MainPlayer always stored in players[0] on the
	// Client - then it's easy for the gui.
	public ArrayList<Player> getLobbyPlayers() {
		Player[] tmp = Datastore.getInstance().getMainPlayer().getRound().getPlayers();
		
		ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(tmp));
						
		return (ArrayList<Player>) Stream.concat( players.stream().filter(p -> p.equals(Datastore.getInstance().getMainPlayer())),
					   							  players.stream().filter(p -> !p.equals(Datastore.getInstance().getMainPlayer()))
					                            ).collect(Collectors.toList());
	}

	// Bilger - cards from MainPlayer from server (already sorted)
	// Roesti - TODO - new Thread for updated server input
	public ArrayList<Card> getMyCards() {
		return currentCards = Datastore.getInstance().getMainPlayer().getHand().getCards();
	}

	public Card playCard(Card playedCard) {
		try {
			Datastore.getInstance().getMainPlayer().play(playedCard);
		} catch (ExceptionBase e) {
			// TODO show error message
			e.printStackTrace();
		}
		return playedCard;
	}

	// temporary
	public ArrayList<Card> getCurrentCards() {
		return this.currentCards;
	}

	// Bilger - TODO Server input
	// Roesti - ArrayList only for testing
	
	
	
	public void refreshTrickCards() {
		Trick trick = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast();
		Card[] cards = (Card[]) trick.getPlayedCards();
		trickCards.clear();
		trickCards.addAll((Arrays.asList(cards).stream().filter(c -> c != null).collect(Collectors.toList())));
	}

	private void startTrickUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> refreshTrickCards());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		Thread t = new Thread(r);
		t.setDaemon(true);
		t.start();
	}
}
