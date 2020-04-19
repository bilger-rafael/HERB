package herb.client.ui.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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
	private int position; 

	public GameModel() {
		super();
		startTrickUpdater();
	}
	
	public ObservableList<Card> getTrickCards() {
		return trickCards;
	}
	
	// Bilger - Input von Server
	// Roesti - store MainPlayer in players[0]
	public ArrayList<Player> getLobbyPlayers() {
		Player[] tmp = Datastore.getInstance().getMainPlayer().getRound().getPlayers();
		
		ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(tmp));
		
		LinkedList<Player> plys = new LinkedList();
		for(Player p : players)
			plys.add(p);
		
		players.clear();
		Player p0 = Datastore.getInstance().getMainPlayer();
		this.position = plys.indexOf(p0);
		
		ListIterator<Player> iter = plys.listIterator(position);
		while(iter.hasNext()) {
			Player p = iter.next();
			players.add(p);
		}
		switch(position) {
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
		
		// Bilger					
//		players = (ArrayList<Player>) Stream.concat( players.stream().filter(p -> p.equals(Datastore.getInstance().getMainPlayer())),
//				players.stream().filter(p -> !p.equals(Datastore.getInstance().getMainPlayer()))
//				).collect(Collectors.toList());		
		return players;
	}

	// Bilger - cards from MainPlayer from server (already sorted)
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
	
	public void refreshTrickCards() {
		Trick trick = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast();
		Card[] cards = (Card[]) trick.getPlayedCards();
		trickCards.clear();
		trickCards.addAll((Arrays.asList(cards).stream().filter(c -> c != null).collect(Collectors.toList())));
		
//		ListIterator<Card> iter = tmp.listIterator(position);
//		while(iter.hasNext()) {
//			Card c = iter.next();
//			trickCards.add(c);
//		}
//		switch(position) {
//		case 0: 
//			break;				
//		case 1: 
//			trickCards.add(tmp.get(0));
//			break;			
//		case 2: 
//			trickCards.add(tmp.get(0));
//			trickCards.add(tmp.get(1));
//			break;
//		case 3: 
//			trickCards.add(tmp.get(0));
//			trickCards.add(tmp.get(1));
//			trickCards.add(tmp.get(2));
//			}	
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
