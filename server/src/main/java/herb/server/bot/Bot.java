package herb.server.bot;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import herb.server.ressources.Card;
import herb.server.ressources.core.CardBase;

//ETTER Computerspieler, spielt Randomkarte
//TODO Besserer Bot
public class Bot extends BotBase {

	// Sucht die Karte aus die gespielt werden soll
	@Override
	public Card determinBestCard() {
		Card bestCard = null;
		Random rand = new Random();
		int randInt;
		this.updatePlayableHand(this.getRound().getTricks().getLast());

		// Logik

		switch (this.getNumberPlayedCards()) {
		// Bot ist der Startspieler, spielt eine Randomkarte
		case (0):
			randInt = rand.nextInt(this.getHand().getCards().size());
			bestCard = this.getHand().getCards().get(randInt);
			break;
		// Bot ist 2. Spieler, spielt eine Randomkarte der spielbaren Karten aus
		case (1):
			randInt = rand.nextInt(this.getPlayableCards().size());
			bestCard = this.getPlayableCards().get(randInt);
			break;

		// Bot ist 3.Spieler, spielt eine Randomkarte der spielbaren Karten aus
		case (2):
			randInt = rand.nextInt(this.getPlayableCards().size());
			bestCard = this.getPlayableCards().get(randInt);
			break;
		// Bot ist Letzter Spieler, spielt eine Randomkarte der spielbaren Karten aus
		case (3):
			randInt = rand.nextInt(this.getPlayableCards().size());
			bestCard = this.getPlayableCards().get(randInt);
			break;
		}
		return bestCard;

	}

	@Override
	protected Card determinBestTrump() {
		// TODO Auto-generated method stub
		return null;
	}
}
