package herb.client.ui.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import herb.client.ressources.Card;
import herb.client.ressources.Player;
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
		try {
		Player[] players = (Player[]) Datastore.getInstance().getMainPlayer().getRound().getPlayers();
		if (players[0] != null)
			return players;
		} catch (Exception e) {
			System.out.println("Fehler");
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

		Datastore.getInstance().getMainPlayer().getHand().getCards();

		Card[] cards = (Card[]) Datastore.getInstance().getMainPlayer().getHand().getCards();
		
		//if cards received from server, use them instead of the testdata
		if (cards[0] != null)
			return new ArrayList<Card>(Arrays.asList(cards));

		// Roesti - ArrayList only for testing
		currentCards = new ArrayList();
		for (int i = 0; i < 4; i++) {
			Rank r1 = Rank.Queen; Suit s1 = Suit.Hearts;  Trump t1 = Trump.TopsDown; Card c1 = new Card(s1, r1, t1);
			currentCards.add(c1); }
		Rank r2 = Rank.Six;		Suit s2 = Suit.Hearts;	Trump t2 = Trump.TopsDown;	Card c2 = new Card(s2, r2, t2);
		currentCards.add(c2);
		Rank r3 = Rank.Seven;	Suit s3 = Suit.Diamonds; Trump t3 = Trump.TopsDown;	Card c3 = new Card(s3, r3, t3);
		currentCards.add(c3);
		Rank r4 = Rank.Ace;	Suit s4 = Suit.Spades;	Trump t4 = Trump.TopsDown;	Card c4 = new Card(s4, r4, t4);
		currentCards.add(c4);
		Rank r5 = Rank.King;	Suit s5 = Suit.Clubs;	Trump t5 = Trump.TopsDown;	Card c5 = new Card(s5, r5, t5);
		currentCards.add(c5);
		Rank r6 = Rank.Nine;	Suit s6 = Suit.Clubs;	Trump t6 = Trump.TopsDown;	Card c6 = new Card(s6, r6, t6);
		currentCards.add(c6);
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
	
	public ArrayList<Card> getCurrentCards(){
		return this.currentCards;
	}
	
	// TODO - Input from Server
	public ArrayList<Card> getPlayableCards(){
		playables = new ArrayList();
		// TODO
		
		// for testing
		Rank r1 = Rank.King;	Suit s1 = Suit.Clubs;	Trump t1 = Trump.TopsDown;	Card c1 = new Card(s1, r1, t1);
		playables.add(c1);
		return playables;
	}

	// Bilger - TODO Server input
	// Roesti - ArrayList only for testing
	public ArrayList<Card> getTrickCards() {
		
		// server...
		
		// for testing
		ArrayList<Card> trickCards = new ArrayList();
		Rank rank1 = Rank.Ten;	Suit suit1 = Suit.Spades;	Trump trump1 = Trump.TopsDown;
		Card card1 = new Card(suit1, rank1, trump1);	trickCards.add(card1);	
		Rank rank2 = Rank.Ace;	Suit suit2 = Suit.Spades;	Trump trump2 = Trump.TopsDown;
		Card card2 = new Card(suit2, rank2, trump2);	trickCards.add(card2);
		Rank rank3 = Rank.Queen;	Suit suit3 = Suit.Spades;	Trump trump3 = Trump.TopsDown;
		Card card3 = new Card(suit3, rank3, trump3);	trickCards.add(card3);
		Rank rank4 = Rank.Ten;	Suit suit4 = Suit.Spades;	Trump trump4 = Trump.TopsDown;
		Card card4 = new Card(suit4, rank4, trump4);	trickCards.add(card4);
		String writeCardsOut = "Trick-Karten...";
		for (int i = 0; i < 4; i++) {writeCardsOut += trickCards.get(i).getSuit();	writeCardsOut += trickCards.get(i).getRank();	}
		System.out.println(writeCardsOut);
		return trickCards;
	}
	
	// only for testing
	public ArrayList<Card> setMyCards(Card card){
		ArrayList<Card> lessCards = new ArrayList();
		lessCards = getMyCards();
		lessCards.remove(card);
		return lessCards;
	}
}
