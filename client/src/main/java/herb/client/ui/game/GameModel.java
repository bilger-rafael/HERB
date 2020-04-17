package herb.client.ui.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

public class GameModel extends Model {
	private Player[] plys;
	private ArrayList<Card> currentCards;
	private ArrayList<Card> playables;
	

	public GameModel() {
		super();
	}

	// Bilger - Input von Server
	// Roesti - TODO - check, that MainPlayer always stored in players[0] on the Client - then it's easy for the gui. 
	public Player[] getLobbyPlayers() {

		// Datastore.getInstance().getMainPlayer().getHand().getCards();
		// Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast().getPlayedCards();
		//TODO remove try catch later
		try {
		Player[] players = (Player[]) Datastore.getInstance().getMainPlayer().getRound().getPlayers();
		if (players[0] != null)
			return players;
		} catch (Exception e) {
			System.out.println("Fehler bei den Spielern");
		}

		// Roesti - ArrayList only for testing
		plys = new Player[4];
		Player p1 = new Player("Mea", "passwort");	Player p2 = new Player("Etter", "passwort");
		Player p3 = new Player("Bilger", "passwort");	Player p4 = new Player("Herren", "passwort");
		plys[0] = p1;	plys[1] = p2;	plys[2] = p3;	plys[3] = p4;
		System.out.println(plys[0].getUsername());
		return plys;
	}

	// Bilger - cards from MainPlayer from server (already sorted)
	// Roesti - TODO - new Thread for updated server input
	public ArrayList<Card> getMyCards() {

		//if cards received from server, use them instead of the testdata
		if (!Datastore.getInstance().getMainPlayer().getHand().getCards().isEmpty())
			return Datastore.getInstance().getMainPlayer().getHand().getCards();

		// Roesti - ArrayList only for testing
		currentCards = new ArrayList();
			Rank r1 = Rank.Queen; Suit s1 = Suit.Hearts;  Trump t1 = Trump.Hearts; Card c1 = new Card(s1, r1, t1);
			currentCards.add(c1); 
			Rank r2 = Rank.Six;		Suit s2 = Suit.Hearts;	Trump t2 = Trump.Hearts;	Card c2 = new Card(s2, r2, t2);
			currentCards.add(c2);
			Rank r3 = Rank.Seven;	Suit s3 = Suit.Diamonds; Trump t3 = Trump.Hearts;	Card c3 = new Card(s3, r3, t3);
			currentCards.add(c3);
			Rank r4 = Rank.Ace;	Suit s4 = Suit.Spades;	Trump t4 = Trump.Hearts;	Card c4 = new Card(s4, r4, t4);
			currentCards.add(c4);
			Rank r5 = Rank.King;	Suit s5 = Suit.Spades;	Trump t5 = Trump.Hearts;	Card c5 = new Card(s5, r5, t5);
			currentCards.add(c5);
			Rank r6 = Rank.Nine;	Suit s6 = Suit.Clubs;	Trump t6 = Trump.Hearts;	Card c6 = new Card(s6, r6, t6);
			currentCards.add(c6);
			Rank r7 = Rank.Ten; Suit s7 = Suit.Clubs;  Trump t7 = Trump.Hearts; Card c7 = new Card(s7, r7, t7);
			currentCards.add(c7);
			Rank r8 = Rank.Jack; Suit s8 = Suit.Clubs;  Trump t8 = Trump.Hearts; Card c8 = new Card(s8, r8, t8);
			currentCards.add(c8);
			Rank r9 = Rank.Ace; Suit s9 = Suit.Clubs;  Trump t9 = Trump.Hearts; Card c9 = new Card(s9, r9, t9);
			currentCards.add(c9);
			return currentCards;
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
	public ArrayList<Card> getCurrentCards(){
		return this.currentCards;
	}

	// Bilger - TODO Server input
	// Roesti - ArrayList only for testing
	public ArrayList<Card> getTrickCards() {
		
		//TODO remove try catch later
		try {
			Trick trick = Datastore.getInstance().getMainPlayer().getRound().getTricks().getLast();
			Card[] cards = (Card[]) trick.getPlayedCards();
			if (trick != null)
				return new ArrayList<Card>(Arrays.asList(cards).stream().filter(c -> c != null).collect(Collectors.toList()));
		} catch (Exception e) {
			System.out.println("Fehler im Trick (Server)"); 
		}
		
		// for testing
		ArrayList<Card> trickCards = new ArrayList();
		Rank rank1 = Rank.Ten;	Suit suit1 = Suit.Spades;	Trump trump1 = Trump.Hearts;
		Card card1 = new Card(suit1, rank1, trump1);	trickCards.add(card1);	
		Rank rank2 = Rank.Ace;	Suit suit2 = Suit.Spades;	Trump trump2 = Trump.Hearts;
		Card card2 = new Card(suit2, rank2, trump2);	trickCards.add(card2);
		Rank rank3 = Rank.Queen;	Suit suit3 = Suit.Spades;	Trump trump3 = Trump.Hearts;
		Card card3 = new Card(suit3, rank3, trump3);	trickCards.add(card3);
		String writeCardsOut = "Trick-Karten...";
		for (int i = 0; i < trickCards.size(); i++) {writeCardsOut += trickCards.get(i).getSuit();	writeCardsOut += trickCards.get(i).getRank();	}
		System.out.println(writeCardsOut);
		return trickCards;
	}
	
	// only for testing
	public ArrayList<Card> setMyCards(Card card){
		ArrayList<Card> lessCards = new ArrayList();
		lessCards = getMyCards();
		lessCards.remove(card);
		currentCards = lessCards;
		return lessCards;
	}
	// only for testing
	public ArrayList<Card> setTrick(Card card){
		ArrayList<Card> moreCards = new ArrayList();
		moreCards = getTrickCards();
		moreCards.add(card);
		return moreCards;
	}
}
