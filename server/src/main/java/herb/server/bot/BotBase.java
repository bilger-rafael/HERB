package herb.server.bot;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import herb.server.ressources.Card;
import herb.server.ressources.PlayListener;
import herb.server.ressources.Player;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.ExceptionBase;

//Bilger
public abstract class BotBase extends Player {
	private static int botNumber = 1;

	public BotBase() {
		super("Bot" + botNumber, UUID.randomUUID().toString().toUpperCase());
		botNumber++;
	}

	protected abstract Card determinBestCard();

	protected List<Card> getPlayableCards() {
		return this.getHand().getCards().stream().filter(c -> c.isPlayable()).collect(Collectors.toList());
	}
	
	protected int getNumberPlayedCards() {
		CardBase[] cards = this.getRound().getTricks().getLast().getPlayedCards();
		return (int) Arrays.asList(cards).stream().filter(c -> c != null).count();
	}

	@Override
	public void setPlayListener(PlayListener playListener) {
		super.setPlayListener(playListener);
		try {
			this.play(determinBestCard());
		} catch (ExceptionBase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
