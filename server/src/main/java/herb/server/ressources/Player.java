package herb.server.ressources;

import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import herb.server.Datastore;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;

//Etter
public class Player extends PlayerBase {
	
	public Player(String username, String authToken) {
		super(username, authToken);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(CardBase card) {
		// Aus Hand entfernen
		this.hand.play(card);
		// Karte dem Trick hinzufügen
		this.getRound().getTricks().getLast().addCardtoTrick(card);
	}

	@Override
	public void addCardtoHand(CardBase card) {
		this.hand.addCard(card);

	}

	@Override
	public void clearHand() {
		this.hand.clearCards();
	}

	@Override
	public boolean PlayerNoCards() {
		if (this.hand.cardsEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void sortHand() {
		this.hand.sortCards();
	}

	@Override //Gibt die Karten zurück, die gespielt werden dürfen
	public CardBase[] getPlayableCards() {
		//Lokale Var, damit nicht gesamter Pfad aufgerufen werden muss
		PlayerBase[] players = new PlayerBase[this.getRound().getPlayers().length];
		CardBase[] playableCards = new CardBase[9];
		int counterPlayableCards =0;
		PlayerBase startingPlayer = this.getRound().getCurrentStartingPlayer();
		int startingPlayerIndex=0;
		for (int i =0; i<this.getRound().getPlayers().length;i++) {
			if ( startingPlayer == players[i] ) {
				startingPlayerIndex = i;
			}
			
			
		//Ich bin der Startspieler
		if(this == players[startingPlayerIndex]) {
			return this.hand.getCards();
		}else {//Ein anderer ist Startspieler
			//TODO Logik
			//Gleiche Farbe wie Startspieler? Trümpfe? Untertrumpfen?
			
			//TODO Hilfe für nächsten Schritt
			for (int j = 0; j<this.hand.getCards().length;j++) {
				if(this.hand.getCard(j).compareToPlayable(this.getRound().getTricks().getLast().getPlayedCard(players[startingPlayerIndex])))
				playableCards[counterPlayableCards] = this.hand.getCard(j);
				counterPlayableCards++;
			}
			
			
			
		}
		
		
			
			
			
		}
		// TODO 
		return null;
	}
	
}
