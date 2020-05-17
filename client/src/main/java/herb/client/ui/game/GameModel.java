package herb.client.ui.game;

import static java.util.Locale.forLanguageTag;

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
import herb.client.ressources.Round;
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
	private Player[] playerServerOrder;
	private ArrayList<Player> players;
	private int playerServerPosition;
	private ArrayList<Card> currentCards;
	private Trick trick;
	private int trickIndex;
	private int trickNumber;
	private ObservableList<Card> trickCards = FXCollections.observableArrayList();
	private Player startingPlayer, currentPlayer;
	private ObservableList<Trump> trumps = FXCollections.observableArrayList();
	private ArrayList<Integer> scoresList;
	private String cardSet = "Fr";
	private Thread t, tu, tru;
	private volatile boolean stop = false;
	private volatile boolean trumpStop = false;
	private ObservableList<Player> currentPlayers = FXCollections.observableArrayList();
	private ObservableList<Player> startingPlayers = FXCollections.observableArrayList();

	
	public GameModel() {
		super();
		
		startTrumpUpdater();
		startTrickUpdater();
		startPlayablesUpdater();
	}

	// Bilger - Input von Server
	// roesti - store MainPlayer in Pole-position
	public ArrayList<Player> getLobbyPlayers() {
		playerServerOrder = Datastore.getInstance().getMainPlayer().getRound().getPlayers();
		players = new ArrayList<Player>(Arrays.asList(playerServerOrder));
		LinkedList<Player> plys = new LinkedList();
		for (Player p : players)
			plys.add(p);

		players.clear();
		Player p0 = Datastore.getInstance().getMainPlayer();
		this.playerServerPosition = plys.indexOf(p0);
		ListIterator<Player> iter = plys.listIterator(playerServerPosition);
		while (iter.hasNext()) {
			Player p = iter.next();
			players.add(p);
		}
		switch (playerServerPosition) {
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

	// roesti - trick array on server corresponds to the player order
	// goal: to put the cards in a array, starting with the card of the current
	// starting player (of this trick)
	public void refreshTrickCards() {
		LinkedList<Trick> tricks = Datastore.getInstance().getMainPlayer().getRound().getTricks();

		if (tricks.isEmpty())
			return;

		if (trick == null) {
			trick = tricks.getLast();
			trickIndex = tricks.indexOf(tricks.getLast());
		}

		// delay when new trick is set
		if (trickIndex != tricks.indexOf(tricks.getLast())) {
			// if client does not have all cards of the previous trick, get them
			long playedCards = Arrays.asList(trick.getPlayedCards()).stream().filter(x -> x != null).count();
			if (playedCards < 4) {
				trick = tricks.get(trickIndex);
				// if client have all cards of the previous trick, wait to give Player some time
				// to look at the finished trick
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				trick = tricks.getLast();
				trickIndex = tricks.indexOf(tricks.getLast());
			}
		}else {
			trick = tricks.getLast();
		}

		this.startingPlayer = trick.getStartingPlayer();
		this.currentPlayer = trick.getCurrentPlayer();
		trickCards.clear();
		Card[] cards = (Card[]) trick.getPlayedCards();
		ArrayList<Card> tmp = new ArrayList();
	
		if(!trickCards.contains(cards[0])){
			tmp.addAll((Arrays.asList(cards).stream().filter(c -> c != null).collect(Collectors.toList())));
			int trickSize = tmp.size();
			switch (trickSize) {
				case 1:
					trickCards.add(tmp.get(0));
					break;
				case 2:
					if (startingPlayer.equals(playerServerOrder[3])) {
						trickCards.add(tmp.get(1));
						trickCards.add(tmp.get(0));
					} else {
						trickCards.add(tmp.get(0));
						trickCards.add(tmp.get(1));
					}
					break;
				case 3:
					if (startingPlayer.equals(playerServerOrder[0]) || startingPlayer.equals(playerServerOrder[1])) {
						trickCards.add(tmp.get(0));
						trickCards.add(tmp.get(1));
						trickCards.add(tmp.get(2));
					}
					if (startingPlayer.equals(playerServerOrder[3])) {
						trickCards.add(tmp.get(2));
						trickCards.add(tmp.get(0));
						trickCards.add(tmp.get(1));
					}
					if (startingPlayer.equals(playerServerOrder[2])) {
						trickCards.add(tmp.get(1));
						trickCards.add(tmp.get(2));
						trickCards.add(tmp.get(0));
					}
					break;
				case 4:
					if (startingPlayer.equals(playerServerOrder[0])) {
						trickCards.add(tmp.get(0));
						trickCards.add(tmp.get(1));
						trickCards.add(tmp.get(2));
						trickCards.add(tmp.get(3));
					}
					if (startingPlayer.equals(playerServerOrder[1])) {
						trickCards.add(tmp.get(1));
						trickCards.add(tmp.get(2));
						trickCards.add(tmp.get(3));
						trickCards.add(tmp.get(0));
					}
					if (startingPlayer.equals(playerServerOrder[2])) {
						trickCards.add(tmp.get(2));
						trickCards.add(tmp.get(3));
						trickCards.add(tmp.get(0));
						trickCards.add(tmp.get(1));
					}
					if (startingPlayer.equals(playerServerOrder[3])) {
						trickCards.add(tmp.get(3));
						trickCards.add(tmp.get(0));
						trickCards.add(tmp.get(1));
						trickCards.add(tmp.get(2));
					}
					break;
				}
		}
	}

	//  get scores for n rounds
	public ArrayList<Integer> getScores() {
		Integer[] serverScores = (Integer[]) Datastore.getInstance().getMainPlayer().getRound().getScores();
		// TODO - count end result
	//	Integer[] serverScores = (Integer[]) Datastore.getInstance().getMainPlayer().getRound().getGame().getRounds().get(0).getScores();
		scoresList = new ArrayList(Arrays.asList(serverScores));
		LinkedList<Integer> scrs = new LinkedList();
		for (Integer i : scoresList) {
			scrs.add(i);
		}
		scoresList.clear();
		ListIterator<Integer> itera = scrs.listIterator(playerServerPosition);
		while (itera.hasNext()) {
			Integer j = itera.next();
			scoresList.add(j);
		}
		switch (playerServerPosition) {
			case 0:
				break;
			case 1:
				scoresList.add(scrs.get(0));
				break;
			case 2:
				scoresList.add(scrs.get(0));
				scoresList.add(scrs.get(1));
				break;
			case 3:
				scoresList.add(scrs.get(0));
				scoresList.add(scrs.get(1));
				scoresList.add(scrs.get(2));
			}
			System.out.println("Erhaltene Scores " + scoresList.toString());
		return scoresList;
	}

	public void refreshCurrentPlayer() {
// TODO - check
		try {
		trick = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast();
		this.trickNumber = (Datastore.getInstance().getMainPlayer().getRound().getTricks().size());
		if(currentPlayers.isEmpty() || trick.getCurrentPlayer() != currentPlayers.get(currentPlayers.size()-1)) {
			currentPlayers.add(trick.getCurrentPlayer());
			startingPlayers.add(trick.getStartingPlayer());
		}
		}catch(Exception e) {
			// to be catched
		}
	}

	public void refreshTrump() {
		if (Datastore.getInstance().getMainPlayer().getRound().getTrump() == null)
			return;

		trumps.add(Datastore.getInstance().getMainPlayer().getRound().getTrump());
	}

	public ObservableList<Card> getTrickCards() {
		return trickCards;
	}

	public ObservableList<Player> getCurrentPlayers() {
		return currentPlayers;
	}

	public ObservableList<Player> getStartingPlayers() {
		return startingPlayers;
	}

	public ObservableList<Trump> getTrump() {
		return trumps;
	}

	private void startTrickUpdater() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (!stop) {
					Platform.runLater(() -> refreshTrickCards());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// connection lost
				}}}
		};
		t = new Thread(r);
		t.setDaemon(true);
		t.start();
	}

	private void startPlayablesUpdater() {
		Runnable ru = new Runnable() {
			@Override
			public void run() {
				while (!stop) {
					Platform.runLater(() -> refreshCurrentPlayer());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// connection lost
				}}}
		};
		tu = new Thread(ru);
		tu.setDaemon(true);
		tu.start();
	}

	private void startTrumpUpdater() {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				while (!trumpStop) {
					Platform.runLater(() -> refreshTrump());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// message: Your choice couldn't be sent to others
				}}}
		};
		tru = new Thread(run);
		tru.setDaemon(true);
		tru.start();
	}

	public Player getStartingPlayer() {
		return this.startingPlayer;
	}

	public Player getCurrentPlayer() {
		this.currentPlayer = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast().getCurrentPlayer();
		return currentPlayer;
	}
	
	public String getLobbyName() {
		String lobby =  Datastore.getInstance().getMainPlayer().getRound().getGame().getLobby().getName();
		return lobby;
	}

//	public SimpleObjectProperty<Player> getCurrentPlayerProperty() {
//		SimpleObjectProperty<Player> curr = new SimpleObjectProperty<>(getCurrentPlayer());
//		return curr;
//	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	public ArrayList<Integer> getScoresList() {
		return this.scoresList;
	}

	public int getTrickNumber() {
		return this.trickNumber;
	}

	public Thread getT() {
		return tu;
	}

	public void setCardSet(String s) {
		this.cardSet = s;
	}

	public String getCardSet() {
		return this.cardSet;
	}
	
	public void setStopThread() {
		this.stop = true;
	}
	public void setStopTrumpThread() {
		this.trumpStop = true;
	}
}