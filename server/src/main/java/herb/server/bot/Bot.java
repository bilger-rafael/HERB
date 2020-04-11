package herb.server.bot;

import java.util.Random;

import herb.server.ressources.Card;
import herb.server.ressources.Player;
import herb.server.ressources.Trick;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;

//ETTER Computerspieler, spielt Randomkarte
//TODO Besserer Bot
//TODO evtl in Player integrieren oder verbinden (Konstruktor in Player=> Player(){...}, welcher Bot erstellt)
public class Bot {
	private int botNumber = 1;
	private Player botPlayer;
	CardBase[] playableCards = new CardBase[9];;
	
	
	//Erstellt einen Bot, welcher ein Player ist
	public Bot() {
		String authToken = "AAA"+botNumber;
		String botName = "Bot "+botNumber;
		botNumber++;
		
		botPlayer = new Player(botName,authToken);
	}

	//Spielt die Karte über den Player aus Sicht des Bots (Logik) bester Wahl
	protected void play() {
		CardBase tempCard = determinBestCard();
		botPlayer.play(tempCard);
		
	}
	
	public CardBase returnPlayableCard(int i) {
		return this.playableCards[i];
	}

	// Sucht die Karte aus die gespielt werden soll
	private CardBase determinBestCard() {
		CardBase bestCard = null;
		PlayerBase startingPlayer = botPlayer.getRound().getCurrentStartingPlayer();
		Random rand = new Random ();
		int randInt;
		playableCards= botPlayer.determinPlayableCards();
		
		
		//Logik
		int NumberPlayedCards = ((Trick) botPlayer.getRound().getTricks().getLast()).getPlayedCards().size();
			
		switch (NumberPlayedCards) {
				//Bot ist der Startspieler, spielt eine Randomkarte
				case(0): 
						randInt =rand.nextInt(botPlayer.getHand().getCards().length);
						bestCard = botPlayer.getHand().getCard(randInt);
						break;
				//Bot ist 2. Spieler, spielt eine Randomkarte der spielbaren Karten aus
				case(1): 	
						randInt = rand.nextInt(playableCards.length);
						bestCard =  returnPlayableCard(randInt);
						break;

				//Bot ist 3.Spieler, spielt eine Randomkarte der spielbaren Karten aus
					case(2):
						randInt = rand.nextInt(playableCards.length);
						bestCard =  returnPlayableCard(randInt);
						break;
				//Bot ist Letzter Spieler, spielt eine Randomkarte der spielbaren Karten aus
				case(3): 
						randInt = rand.nextInt(playableCards.length);
						bestCard =  returnPlayableCard(randInt);
						break;
		}
		return bestCard;

		
	}
}
