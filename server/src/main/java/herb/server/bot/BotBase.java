package herb.server.bot;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import herb.server.ressources.Card;
import herb.server.ressources.Player;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.ExceptionBase;

//Bilger
public abstract class BotBase extends Player{
	private static int botNumber = 1;
	private static String botName = "Bot "+botNumber;
	
	public BotBase() {
		super(botName, UUID.randomUUID().toString().toUpperCase());
		botNumber++;
	}

	//Spielt die Karte über den Player aus Sicht des Bots (Logik) bester Wahl
	protected void play() {
		CardBase tempCard = determinBestCard();
		try {
			this.play(tempCard);
		} catch (ExceptionBase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Card> getPlayableCards() {
		return this.getHand().getCards().stream().filter(c -> c.isPlayable()).collect(Collectors.toList());
	}

	public abstract Card determinBestCard();
}
