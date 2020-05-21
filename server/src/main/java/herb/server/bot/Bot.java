package herb.server.bot;

import java.util.Random;
import herb.server.ressources.Card;
import herb.server.ressources.core.Trump;

//ETTER Computerspieler, spielt Randomkarte und wählt bei Bedarf Random Trumpf
public class Bot extends BotBase {
	public Bot(String s) {
		super(s);
	}

	private Random rand = new Random();
	private int randInt;
	// Sucht die Karte aus die gespielt werden soll
	@Override
	public Card determinBestCard() {
		Card bestCard = null;
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

	@Override // Gibt einen Random Trumpf zurück
	protected Trump determinBestTrump() {
		 randInt = rand.nextInt(6);
		 return Trump.values()[randInt];
	}
}
