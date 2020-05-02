package CoreTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import herb.server.ressources.Card;
import herb.server.ressources.core.Rank;
import herb.server.ressources.core.Suit;
import herb.server.ressources.core.Trump;


//Funktioniert nicht mehr, weil Trump nun nicht mehr Trump als Parameter erhält.
public class CardTest {
	private static String cardTrump9s = "H9H";
	private static String card6s = "D6C";
	private static String cardTrumpBs = "HJH";
	private static String cardTopdown8s = "S8T";
	private static String cardBottonUp6s = "H6B";
	private static String cardtrumpQs= "DQD";
	private static String cardtrumpKs= "DKD";
	private static String cardTopdown7s = "S7T";
	private static String cardBottomUp7s = "H7B";
	
	
	// Speicherort der Karten
	Card cardTrump9;
	Card card6;
	Card cardTrumpB;
	Card cardTopdown8;
	Card cardBottonUp6;
	Card cardtrumpQ;
	Card cardtrumpK;
	Card cardTopdown7;
	Card cardBottomUp7;

	@Before
	public void makeCard() {
		cardTrump9 = makeCard(cardTrump9s);
		card6 = makeCard(card6s);
		cardTrumpB = makeCard(cardTrumpBs);
		cardTopdown8 = makeCard(cardTopdown8s);
		cardBottonUp6 = makeCard(cardBottonUp6s);
		cardtrumpQ = makeCard(cardtrumpQs);
		cardtrumpK = makeCard(cardtrumpKs);
		cardTopdown7 = makeCard(cardTopdown7s);
		cardBottomUp7 = makeCard(cardBottomUp7s);
	}

	@Test
	public void testGetPoints() {
		assertTrue (14 == cardTrump9.getPoints());
		assertTrue (0 == card6.getPoints());
		assertTrue (20 == cardTrumpB.getPoints());
		assertTrue (8 == cardTopdown8.getPoints());
		assertTrue (11 == cardBottonUp6.getPoints());
	
	}
	
	@Test
	public void testCompareTo() {
		assertTrue (10 == cardTrumpB.compareTo(cardTrump9));
		assertTrue (-10 == cardTrump9.compareTo(cardTrumpB));
		assertTrue (-8 == cardTopdown8.compareTo(card6));
		assertTrue (-1 == cardtrumpQ.compareTo(cardtrumpK));
		assertTrue (1 == cardTopdown8.compareTo(cardTopdown7));
		assertTrue (1 == cardBottonUp6.compareTo(cardBottomUp7));
	}

	//Etter Input aus Testklasse von PokerGame HS19
	private Card makeCard(String in) {

		char s = in.charAt(0);
		Suit suit = null;
		if (s == 'C') suit = Suit.Clubs;
		if (s == 'D') suit = Suit.Diamonds;
		if (s == 'H') suit = Suit.Hearts;
		if (s == 'S') suit = Suit.Spades;
	
		char r = in.charAt(1);
		Rank rank = null;
		if (r == '6') rank = Rank.Six;
		else if (r == '7') rank = Rank.Seven;
		else if (r == '8') rank = Rank.Eight;
		else if (r == '9') rank = Rank.Nine;
		else if (r == 'J') rank = Rank.Jack;
		else if (r == 'Q') rank = Rank.Queen;
		else if (r == 'K') rank = Rank.King;
		else if (r == 'A') rank = Rank.Ace;
	
		char s2 = in.charAt(2);
		Trump trump = null;
		if (s2 == 'C') trump = Trump.Clubs;
		if (s2 == 'D') trump = Trump.Diamonds;
		if (s2 == 'H') trump = Trump.Hearts;
		if (s2 == 'S') trump = Trump.Spades;
		if (s2 == 'T') trump = Trump.TopsDown;
		if (s2 == 'B') trump = Trump.BottomsUp;

	return new Card(suit, rank);
	}

}
