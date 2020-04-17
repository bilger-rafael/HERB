package herb.server.ressources;

import java.util.ArrayList;
import java.util.LinkedList;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Player extends PlayerBase<Hand, Round> {
	private ArrayList<PlayListener> playListeners;
	
	
	public Player(String username, String authToken) {
		super(username, authToken);
		super.setHand(new Hand());
		this.playListeners = new ArrayList<PlayListener>();
	}

	@Override
	public void play(CardBase card) throws ExceptionBase {
		// Aus Hand entfernen
		this.getHand().play((Card) card);
		// Karte dem Trick hinzufügen

		getLastTrick().addCardtoTrick((Card) card);
		
		//clone array to avoid exception when listener is removed in the for loop
		ArrayList<PlayListener> listeners = new ArrayList<PlayListener>(this.playListeners); 
		
		for(PlayListener playListener : listeners) {
			playListener.played();
		}
		
		this.playListeners.clear();
	}

	public void addCardtoHand(CardBase card) {
		this.getHand().addCard((Card) card);
	}

	public void clearHand() {
		this.getHand().clearCards();
	}

	public boolean PlayerNoCards() {
		if (this.getHand().cardsEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public void sortHand() {
		this.getHand().sortCards();
	}

	public void updatePlayableHand(Trick t) {
		this.playableHandCards = determinPlayableCards(t);
	}
	
	//Gibt die Karten zurück, die gespielt werden dürfen
	private CardBase[] determinPlayableCards(Trick t) {
		//Lokale Var, damit nicht gesamter Pfad aufgerufen werden muss
		Player[] players = new Player[this.getRound().getPlayers().length];
		CardBase[] playableCards = new CardBase[9];
		int counterPlayableCards =0;
		Player startingPlayer = (Player) this.getRound().getCurrentStartingPlayer();
		//Erster Spieler
		int startingPlayerIndex=0;
		for (int i =0; i<this.getRound().getPlayers().length;i++) {
			if ( startingPlayer == players[i] ) {
				startingPlayerIndex = i;
			}
		}
		//ZweiterSpieler auslesen
		int secoundPlayerIndex=0;
		Player secoundPlayer = t.getNextPlayer(startingPlayer);
		for (int i =0; i<this.getRound().getPlayers().length;i++) {
			if(secoundPlayer == players[i]) {
				secoundPlayerIndex = i;
			}
		}
		//DritterSpieler auslesen
		int thirdPlayerIndex=0;
		Player thirdPlayer = t.getNextPlayer(secoundPlayer);
		for (int i =0; i<this.getRound().getPlayers().length;i++) {
			if(thirdPlayer == players[i]) {
				thirdPlayerIndex = i;
			}
		}
			

		//Logik
		int NumberPlayedCards = t.getPlayedCards().length;
			
		switch (NumberPlayedCards) {
				//Ich bin der Startspieler
				case(0): for (int j = 0; j<this.getHand().getCards().length;j++) {
							playableCards[counterPlayableCards] = this.getHand().getCard(j);
							counterPlayableCards++;		
						}
						break;
				//2. Spieler
				case(1): 
					//Spielen kann ich Trümpfe und gleiche Farbe, falls keine alles spielbar
					for (int j = 0; j<this.getHand().getCards().length;j++) {
						if(this.getHand().getCard(j).getSuit()== t.getPlayedCard(players[startingPlayerIndex]).getSuit() 
								|| this.getHand().getCard(j).getTrump().ordinal()==this.getHand().getCard(j).getSuit().ordinal()) {
							playableCards[counterPlayableCards] = this.getHand().getCard(j);
							counterPlayableCards++;		
						}
					}
					if(counterPlayableCards!=0) {
					
					}else {
						for (int j = 0; j<this.getHand().getCards().length;j++) {
							playableCards[counterPlayableCards] = this.getHand().getCard(j);
							counterPlayableCards++;		
						}
					}
						
					break;
				//3.Spieler
					//gleiche Farbe wie StartSpieler oder man gewinnt (höchster Trumpf), sonst alles erlaubt
				case(2):
					for (int j = 0; j<this.getHand().getCards().length;j++) {
						if(this.getHand().getCard(j).getSuit()== t.getPlayedCard(players[startingPlayerIndex]).getSuit() 
							|| (this.getHand().getCard(j).compareToPlayable(t.getPlayedCard(players[startingPlayerIndex])) 
								&& this.getHand().getCard(j).compareToPlayable(t.getPlayedCard(players[secoundPlayerIndex]))
								)){
							playableCards[counterPlayableCards] = this.getHand().getCard(j);
							counterPlayableCards++;	
						}
					}
					if(counterPlayableCards!=0) {
					}else {
						for (int j = 0; j<this.getHand().getCards().length;j++) {
						playableCards[counterPlayableCards] = this.getHand().getCard(j);
						counterPlayableCards++;		
						}
					}

					break;
				//Letzter Spieler 
					//gleiche Farbe wie StartSpieler oder man gewinnt (höchster Trumpf), sonst alles erlaubt
				case(3): 
					for (int j = 0; j<this.getHand().getCards().length;j++) {
						if(this.getHand().getCard(j).getSuit()== t.getPlayedCard(players[startingPlayerIndex]).getSuit() 
							|| (this.getHand().getCard(j).compareToPlayable(t.getPlayedCard(players[startingPlayerIndex])) 
								&& this.getHand().getCard(j).compareToPlayable(t.getPlayedCard(players[secoundPlayerIndex]))
								&& this.getHand().getCard(j).compareToPlayable(t.getPlayedCard(players[thirdPlayerIndex]))
								)){
							playableCards[counterPlayableCards] = this.getHand().getCard(j);
							counterPlayableCards++;	
						}
					}
					if(counterPlayableCards!=0) {
					}else {
						for (int j = 0; j<this.getHand().getCards().length;j++) {
						playableCards[counterPlayableCards] = this.getHand().getCard(j);
						counterPlayableCards++;		
						}
					}
					break;
		}
		return playableCards;
	
	}
	
	public void addPlayListener(PlayListener playListener) {
		this.playListeners.add(playListener);
	}
	
	private Trick getLastTrick() {
		return (Trick) ((LinkedList<Trick>) this.getRound().getTricks()).getLast();
	}
}