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
		this.hand = new Hand();
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
	public CardBase[] determinPlayableCards() {
		//Lokale Var, damit nicht gesamter Pfad aufgerufen werden muss
		PlayerBase[] players = new PlayerBase[this.getRound().getPlayers().length];
		CardBase[] playableCards = new CardBase[9];
		int counterPlayableCards =0;
		PlayerBase startingPlayer = this.getRound().getCurrentStartingPlayer();
		//Erster Spieler
		int startingPlayerIndex=0;
		for (int i =0; i<this.getRound().getPlayers().length;i++) {
			if ( startingPlayer == players[i] ) {
				startingPlayerIndex = i;
			}
		}
		//ZweiterSpieler auslesen
		int secoundPlayerIndex=0;
		PlayerBase secoundPlayer=this.getRound().getTricks().getLast().getNextPlayer(startingPlayer);
		for (int i =0; i<this.getRound().getPlayers().length;i++) {
			if(secoundPlayer == players[i]) {
				secoundPlayerIndex = i;
			}
		}
		//DritterSpieler auslesen
		int thirdPlayerIndex=0;
		PlayerBase thirdPlayer=this.getRound().getTricks().getLast().getNextPlayer(secoundPlayer);
		for (int i =0; i<this.getRound().getPlayers().length;i++) {
			if(thirdPlayer == players[i]) {
				thirdPlayerIndex = i;
			}
		}
			

		//Logik
		int NumberPlayedCards = this.getRound().getTricks().getLast().getPlayedCards().size();
			
		switch (NumberPlayedCards) {
				//Ich bin der Startspieler
				case(0): for (int j = 0; j<this.hand.getCards().length;j++) {
							playableCards[counterPlayableCards] = this.hand.getCard(j);
							counterPlayableCards++;		
						}
						break;
				//2. Spieler
				case(1): 
					//Spielen kann ich Trümpfe und gleiche Farbe, falls keine alles spielbar
					for (int j = 0; j<this.hand.getCards().length;j++) {
						if(this.hand.getCard(j).getSuit()== this.getRound().getTricks().getLast().getPlayedCard(players[startingPlayerIndex]).getSuit() 
								|| this.hand.getCard(j).getTrump().ordinal()==this.hand.getCard(j).getSuit().ordinal()) {
							playableCards[counterPlayableCards] = this.hand.getCard(j);
							counterPlayableCards++;		
						}
					}
					if(counterPlayableCards!=0) {
					
					}else {
						for (int j = 0; j<this.hand.getCards().length;j++) {
							playableCards[counterPlayableCards] = this.hand.getCard(j);
							counterPlayableCards++;		
						}
					}
						
					break;
				//3.Spieler
					//gleiche Farbe wie StartSpieler oder man gewinnt (höchster Trumpf), sonst alles erlaubt
				case(2):
					for (int j = 0; j<this.hand.getCards().length;j++) {
						if(this.hand.getCard(j).getSuit()== this.getRound().getTricks().getLast().getPlayedCard(players[startingPlayerIndex]).getSuit() 
							|| (this.hand.getCard(j).compareToPlayable(this.getRound().getTricks().getLast().getPlayedCard(players[startingPlayerIndex])) 
								&& this.hand.getCard(j).compareToPlayable(this.getRound().getTricks().getLast().getPlayedCard(players[secoundPlayerIndex]))
								)){
							playableCards[counterPlayableCards] = this.hand.getCard(j);
							counterPlayableCards++;	
						}
					}
					if(counterPlayableCards!=0) {
					}else {
						for (int j = 0; j<this.hand.getCards().length;j++) {
						playableCards[counterPlayableCards] = this.hand.getCard(j);
						counterPlayableCards++;		
						}
					}

					break;
				//Letzter Spieler 
					//gleiche Farbe wie StartSpieler oder man gewinnt (höchster Trumpf), sonst alles erlaubt
				case(3): 
					for (int j = 0; j<this.hand.getCards().length;j++) {
						if(this.hand.getCard(j).getSuit()== this.getRound().getTricks().getLast().getPlayedCard(players[startingPlayerIndex]).getSuit() 
							|| (this.hand.getCard(j).compareToPlayable(this.getRound().getTricks().getLast().getPlayedCard(players[startingPlayerIndex])) 
								&& this.hand.getCard(j).compareToPlayable(this.getRound().getTricks().getLast().getPlayedCard(players[secoundPlayerIndex]))
								&& this.hand.getCard(j).compareToPlayable(this.getRound().getTricks().getLast().getPlayedCard(players[thirdPlayerIndex]))
								)){
							playableCards[counterPlayableCards] = this.hand.getCard(j);
							counterPlayableCards++;	
						}
					}
					if(counterPlayableCards!=0) {
					}else {
						for (int j = 0; j<this.hand.getCards().length;j++) {
						playableCards[counterPlayableCards] = this.hand.getCard(j);
						counterPlayableCards++;		
						}
					}
					break;
		}
		return playableCards;
	
	}
}