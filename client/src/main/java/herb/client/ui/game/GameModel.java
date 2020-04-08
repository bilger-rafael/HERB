package herb.client.ui.game;

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
    	
    //	plys.getCard(int i);
    	
    	
    	return plys;
    }
    
    
    // Player zuweisen
    private Player identifyPlayer() {
    	Player p1 = new Player();
    	// usernames abgleichen
    	return p1;
    }
    
    // Roesti - card array  TODO receive from server
	public Card[] getMyCards() {
		Card[] currentCards = new Card[9];
		
		for (int i = 0; i<9; i++) {
		Rank r1 = Rank.Eight;
		Suit s1 = Suit.Clubs;
		Trump t1 = Trump.TopsDown;
		Card c1 = new Card(s1.toString(), "Eight", t1.toString());
		currentCards[i] = c1;
		}
		
		String writeCardsOut = "Spieler-Karten...";
		for (int i = 0; i < 9; i++) {
			writeCardsOut += currentCards[i].getSuit();
			writeCardsOut += currentCards[i].getRank();	
			}
		System.out.println(writeCardsOut);
		return currentCards;
	}

}
