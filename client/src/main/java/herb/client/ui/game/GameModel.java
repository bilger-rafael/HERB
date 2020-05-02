package herb.client.ui.game;

import java.util.ArrayList;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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
	private ArrayList<Player> players;
	private ArrayList<Card> currentCards;
	private ObservableList<Card> trickCards = FXCollections.observableArrayList();
	private int position;
	private Trick trick;
	private Player startingPlayer, currentPlayer;
	private Player[] pServerOrder;
	private ObservableList<Player> playableCurrents = FXCollections.observableArrayList();
	private ObservableList<Trump> trumps = FXCollections.observableArrayList();

	public GameModel() {
		super();

		startTrumpUpdater();
		startTrickUpdater();
		startPlayablesUpdater();
	}

	// Bilger - Input von Server
	// Roesti - store MainPlayer in Pole-position
	public ArrayList<Player> getLobbyPlayers() {
		pServerOrder = Datastore.getInstance().getMainPlayer().getRound().getPlayers();
		players = new ArrayList<Player>(Arrays.asList(pServerOrder));

		LinkedList<Player> plys = new LinkedList();
		for (Player p : players)
			plys.add(p);

		players.clear();
		Player p0 = Datastore.getInstance().getMainPlayer();
		this.position = plys.indexOf(p0);

		ListIterator<Player> iter = plys.listIterator(position);
		while (iter.hasNext()) {
			Player p = iter.next();
			players.add(p);
		}
		switch (position) {
		case 0:
			break;
		case 1:
			players.add(plys.get(0));
			break;
		case 2:
			players.add(plys.get(0));
			players.add(plys.get(1));
			break;
		case 3:
			players.add(plys.get(0));
			players.add(plys.get(1));
			players.add(plys.get(2));
		}	
		return players;
	}

	// Bilger - cards from MainPlayer from server (already sorted)
	public ArrayList<Card> getMyCards() {
		currentCards = new ArrayList();
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

	public Trump setTrump(Trump chosenTrump) {
		try {
			Datastore.getInstance().getMainPlayer().chooseTrump(chosenTrump);
		} catch (ExceptionBase e) {
			// TODO show error message
			e.printStackTrace();
		}
		return chosenTrump;
	}

	// Roesti - trick array on server corresponds to the player order
	// goal: to put the cards in a array, starting with the card of the current
	// starting player (of this trick)
	public void refreshTrickCards() {
		if(Datastore.getInstance().getMainPlayer().getRound().getTricks().isEmpty())
			return;
		
		trick = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast();
		this.startingPlayer = trick.getStartingPlayer();
		this.currentPlayer = trick.getCurrentPlayer();
		Card[] cards = (Card[]) trick.getPlayedCards();

		ArrayList<Card> tmp = new ArrayList();
		tmp.addAll((Arrays.asList(cards).stream().filter(c -> c != null).collect(Collectors.toList())));

		trickCards.clear();

		int l = tmp.size();
		switch (l) {
		case 1:
			trickCards.add(tmp.get(0));
			break;
		case 2:
			if (startingPlayer.equals(pServerOrder[3])) {
				trickCards.add(tmp.get(1));
				trickCards.add(tmp.get(0));
			} else {
				trickCards.add(tmp.get(0));
				trickCards.add(tmp.get(1));
			}
			break;
		case 3:
			if (startingPlayer.equals(pServerOrder[0]) || startingPlayer.equals(pServerOrder[1])) {
				trickCards.add(tmp.get(0));
				trickCards.add(tmp.get(1));
				trickCards.add(tmp.get(2));
			}
			if (startingPlayer.equals(pServerOrder[3])) {
				trickCards.add(tmp.get(2));
				trickCards.add(tmp.get(0));
				trickCards.add(tmp.get(1));
			}
			if (startingPlayer.equals(pServerOrder[2])) {
				trickCards.add(tmp.get(1));
				trickCards.add(tmp.get(2));
				trickCards.add(tmp.get(0));
			}
			break;
		case 4:
			if (startingPlayer.equals(pServerOrder[0])) {
				trickCards.add(tmp.get(0));
				trickCards.add(tmp.get(1));
				trickCards.add(tmp.get(2));
				trickCards.add(tmp.get(3));
			}
			if (startingPlayer.equals(pServerOrder[1])) {
				trickCards.add(tmp.get(1));
				trickCards.add(tmp.get(2));
				trickCards.add(tmp.get(3));
				trickCards.add(tmp.get(0));
			}
			if (startingPlayer.equals(pServerOrder[2])) {
				trickCards.add(tmp.get(2));
				trickCards.add(tmp.get(3));
				trickCards.add(tmp.get(0));
				trickCards.add(tmp.get(1));
			}
			if (startingPlayer.equals(pServerOrder[3])) {
				trickCards.add(tmp.get(3));
				trickCards.add(tmp.get(0));
				trickCards.add(tmp.get(1));
				trickCards.add(tmp.get(2));
			}
			break;
		}
	}

	public void refreshPlayables() {
		if(Datastore.getInstance().getMainPlayer().getRound().getTricks().isEmpty())
			return;
		
		trick = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast();
		playableCurrents.add(trick.getCurrentPlayer());
	}

	public ObservableList<Trump> refreshTrump() {
		trumps.add(Datastore.getInstance().getMainPlayer().getRound().getTrump());
		return trumps;
	}

	public ObservableList<Card> getTrickCards() {
		return trickCards;
	}

	public ObservableList<Player> getCurrentPlayers() {
		return playableCurrents;
	}

	public ObservableList<Trump> getTrump() {
		return trumps;
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

	private void startPlayablesUpdater() {
		Runnable ru = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> refreshPlayables());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		Thread tu = new Thread(ru);
		tu.setDaemon(true);
		tu.start();
	}

	private void startTrumpUpdater() {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> refreshTrump());
					System.out.println("Updater is working");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		Thread tru = new Thread(run);
		tru.setDaemon(true);
		tru.start();
	}

	public Player getStartingPlayer() {
		return this.startingPlayer;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public SimpleObjectProperty<Player> getCurrentPlayerProperty() {
		SimpleObjectProperty<Player> curr = new SimpleObjectProperty<>(getCurrentPlayer());
		return curr;
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}
}
