package herb.server.bot;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import herb.server.ressources.Card;
import herb.server.ressources.PlayListener;
import herb.server.ressources.TrumpListener;
import herb.server.ressources.Player;
import herb.server.ressources.RematchListener;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.Trump;

//Bilger
public abstract class BotBase extends Player {
	private static int botNumber = 1;

	public BotBase(String s) {
		super(s + botNumber, UUID.randomUUID().toString().toUpperCase());
		botNumber++;
	}

	protected abstract Card determinBestCard();

	protected abstract Trump determinBestTrump();

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
		Card c = determinBestCard();
		if (c == null)
			c = this.getHand().getCards().stream().filter(x -> x.isPlayable()).findFirst().get();
		try {
			this.play(c);
		} catch (ExceptionBase e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setTrumpListener(TrumpListener trumpListener) {
		super.setTrumpListener(trumpListener);
		try {
			this.chooseTrump(determinBestTrump());
		} catch (ExceptionBase e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setRematchListener(RematchListener rematchListener) {
		super.setRematchListener(rematchListener);
		try {
			this.demandRematch(true);
		} catch (ExceptionBase e) {
			e.printStackTrace();
		}
	}

}
