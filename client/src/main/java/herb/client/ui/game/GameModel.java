package herb.client.ui.game;

import java.util.ArrayList;

import herb.client.ressources.Card;
import herb.client.ressources.Player;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.Model;

public class GameModel extends Model{

    public GameModel() {
    	super();

    }
    
    
    // Roesti > receive cards for this player 
    // TODO Input von Server Player[] players
    public Player[] getLobbyPlayers() {
    	Player[] plys = new Player[4];
    	
    	Player p1 = new Player ("Mea", "passwort");
    	Player p2 = new Player ("Etter", "passwort");
    	Player p3 = new Player ("Bilger", "passwort");
    	Player p4 = new Player ("Herren", "passwort");
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
    
    // Roesti - card array  TODO receive from server
	public ArrayList<Card> getMyCards() {
		ArrayList<Card> currentCards = new ArrayList();
		
		for (int i = 0; i<4; i++) {
		Rank r1 = Rank.Queen;
		Suit s1 = Suit.Hearts;
		Trump t1 = Trump.TopsDown;
		Card c1 = new Card(s1.toString(), "Queen", t1.toString());
		currentCards.add(c1);
		}
		
		Rank r2 = Rank.Six;
		Suit s2 = Suit.Hearts;
		Trump t2 = Trump.TopsDown;
		Card c2 = new Card(s2.toString(), "Six", t2.toString());
		currentCards.add(c2);
		
		Rank r3 = Rank.Seven;
		Suit s3 = Suit.Diamonds;
		Trump t3 = Trump.TopsDown;
		Card c3 = new Card(s3.toString(), "Seven", t3.toString());
		currentCards.add(c3);
		
		Rank r4 = Rank.Ace;
		Suit s4 = Suit.Spades;
		Trump t4 = Trump.TopsDown;
		Card c4 = new Card(s4.toString(), "Ace", t4.toString());
		currentCards.add(c4);
		
		Rank r5 = Rank.King;
		Suit s5 = Suit.Clubs;
		Trump t5 = Trump.TopsDown;
		Card c5 = new Card(s5.toString(), "King", t5.toString());
		currentCards.add(c5);
		
		Rank r6 = Rank.Nine;
		Suit s6 = Suit.Clubs;
		Trump t6 = Trump.TopsDown;
		Card c6 = new Card(s6.toString(), "Nine", t6.toString());
		currentCards.add(c6);
		
//		String writeCardsOut = "Spieler-Karten...";
//		for (int i = 0; i < 9; i++) {
//			writeCardsOut += currentCards[i].getSuit();
//			writeCardsOut += currentCards[i].getRank();	
//			}
//		System.out.println(writeCardsOut);
		return currentCards;
	}
	
	public Card[] getTrickCards() {
		Card[] trickCards = new Card[4];
		
		for (int i = 0; i<4; i++) {
		Rank r1 = Rank.Ten;
		Suit s1 = Suit.Spades;
		Trump t1 = Trump.TopsDown;
		Card c1 = new Card(s1.toString(), "Ten", t1.toString());
		trickCards[i] = c1;
		}
		
		String writeCardsOut = "Trick-Karten...";
		for (int i = 0; i < 4; i++) {
			writeCardsOut += trickCards[i].getSuit();
			writeCardsOut += trickCards[i].getRank();	
			}
		System.out.println(writeCardsOut);
		return trickCards;
	}

}
