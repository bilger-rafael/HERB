package herb.server.bot;

import java.util.Random;

import herb.server.ressources.Card;
import herb.server.ressources.Player;
import herb.server.ressources.Trick;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.PlayerBase;

//ETTER Computerspieler, spielt Randomkarte
//TODO Besserer Bot
public class Bot extends Player{
	private static int botNumber = 1;
	private Player botPlayer;
	CardBase[] playableCards = new CardBase[9];;
	private static String authToken = "AAA"+botNumber;
	private static String botName = "Bot "+botNumber;
	
	//Erstellt einen Bot, welcher ein Player ist
	public Bot() {
		super(botName, authToken);
		botNumber++;
		authToken = "AAA"+botNumber;
		botName = "Bot "+botNumber;
	}

	//Spielt die Karte über den Player aus Sicht des Bots (Logik) bester Wahl
	protected void play() {
		CardBase tempCard = determinBestCard();
		try {
			botPlayer.play(tempCard);
		} catch (ExceptionBase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		this.updatePlayableHand(super.getRound().getTricks().getLast());
		
		
		//Logik
		int NumberPlayedCards = super.getRound().getTricks().size();
			
		switch (NumberPlayedCards) {
				//Bot ist der Startspieler, spielt eine Randomkarte
				case(0): 
						randInt =rand.nextInt(botPlayer.getHand().getCards().size());
						bestCard = botPlayer.getHand().getCards().get(randInt);
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
