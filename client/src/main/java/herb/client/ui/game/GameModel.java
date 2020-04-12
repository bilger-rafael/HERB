package herb.client.ui.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import herb.client.ressources.Card;
import herb.client.ressources.Player;
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

	// TODO Input von Server Player[] players
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

		plys = new Player[4];

		Player p1 = new Player("Mea", "passwort");
		Player p2 = new Player("Etter", "passwort");
		Player p3 = new Player("Bilger", "passwort");
		Player p4 = new Player("Herren", "passwort");
		plys[0] = p1;
		plys[1] = p2;
		plys[2] = p3;
		plys[3] = p4;
		System.out.println(plys[0].getUsername());

		return plys;
	}

	// Player zuweisen - Linked List on server, here array?
	private Player identifyPlayer() {
		Player p1 = new Player();
		// usernames abgleichen
		return p1;
	}

	// Roesti - card array TODO receive from server
	public ArrayList<Card> getMyCards() {

		Datastore.getInstance().getMainPlayer().getHand().getCards();

		Card[] cards = (Card[]) Datastore.getInstance().getMainPlayer().getHand().getCards();
		
		//if cards received from server, use them instead of the testdata
		if (cards[0] != null)
			return new ArrayList<Card>(Arrays.asList(cards));

		currentCards = new ArrayList();

		for (int i = 0; i < 4; i++) {
			Rank r1 = Rank.Queen;
			Suit s1 = Suit.Hearts;
			Trump t1 = Trump.TopsDown;
			Card c1 = new Card(s1, r1, t1);
			currentCards.add(c1);
		}

		Rank r2 = Rank.Six;
		Suit s2 = Suit.Hearts;
		Trump t2 = Trump.TopsDown;
		Card c2 = new Card(s2, r2, t2);
		currentCards.add(c2);

		Rank r3 = Rank.Seven;
		Suit s3 = Suit.Diamonds;
		Trump t3 = Trump.TopsDown;
		Card c3 = new Card(s3, r3, t3);
		currentCards.add(c3);

		Rank r4 = Rank.Ace;
		Suit s4 = Suit.Spades;
		Trump t4 = Trump.TopsDown;
		Card c4 = new Card(s4, r4, t4);
		currentCards.add(c4);

		Rank r5 = Rank.King;
		Suit s5 = Suit.Clubs;
		Trump t5 = Trump.TopsDown;
		Card c5 = new Card(s5, r5, t5);
		currentCards.add(c5);

		Rank r6 = Rank.Nine;
		Suit s6 = Suit.Clubs;
		Trump t6 = Trump.TopsDown;
		Card c6 = new Card(s6, r6, t6);
		currentCards.add(c6);

//		String writeCardsOut = "Spieler-Karten...";
//		for (int i = 0; i < 9; i++) {
//			writeCardsOut += currentCards[i].getSuit();
//			writeCardsOut += currentCards[i].getRank();	
//			}
//		System.out.println(writeCardsOut);
		return currentCards;
	}
	
	public ArrayList<Card> getCurrentCards(){
		return this.currentCards;
	}
	
	public ArrayList<Card> getPlayableCards(){
		playables = new ArrayList();
		// TODO
		// for testing
		Rank r1 = Rank.Nine;
		Suit s1 = Suit.Clubs;
		Trump t1 = Trump.TopsDown;
		Card c1 = new Card(s1, r1, t1);
		playables.add(c1);
		
		return playables;
	}

	public ArrayList<Card> getTrickCards() {
		ArrayList<Card> trickCards = new ArrayList();
		
		Rank rank1 = Rank.Ten;
		Suit suit1 = Suit.Spades;
		Trump trump1 = Trump.TopsDown;
		Card card1 = new Card(suit1, rank1, trump1);
		trickCards.add(card1);
		
		Rank rank2 = Rank.Ace;
		Suit suit2 = Suit.Spades;
		Trump trump2 = Trump.TopsDown;
		Card card2 = new Card(suit2, rank2, trump2);
		trickCards.add(card2);
		
		Rank rank3 = Rank.Queen;
		Suit suit3 = Suit.Spades;
		Trump trump3 = Trump.TopsDown;
		Card card3 = new Card(suit3, rank3, trump3);
		trickCards.add(card3);
		
		Rank rank4 = Rank.Ten;
		Suit suit4 = Suit.Spades;
		Trump trump4 = Trump.TopsDown;
		Card card4 = new Card(suit4, rank4, trump4);
		trickCards.add(card4);

//		String writeCardsOut = "Trick-Karten...";
//		for (int i = 0; i < 4; i++) {
//			writeCardsOut += trickCards.get(i).getSuit();
//			writeCardsOut += trickCards.get(i).getRank();
//		}
//		System.out.println(writeCardsOut);
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
