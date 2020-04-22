package herb.server.bot;

import java.util.ArrayList;
import java.util.Random;

import herb.server.ressources.Card;
import herb.server.ressources.Player;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.PlayerBase;

public class BetterBot extends Player {
	private static int botNumber = 1;
	private Player botPlayer;
	CardBase[] playableCards = new CardBase[9];;
	private static String authToken = "AAA"+botNumber;
	private static String botName = "Bot "+botNumber;

	public BetterBot() {
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
	
	//Gibt die tiefste spielbare Karte zurück, die gewinnt
	public CardBase returnLowCostPlayableCard() {
		ArrayList<CardBase> winnerCards = returnWinnerHandCards();
		CardBase temp = winnerCards.get(0);
		
		int i=100;
		for(CardBase c:winnerCards) {
			if(c.getGameValue()<i) {
				i=c.getGameValue();
				temp=c;
			}
		}
		return temp;
	}
	
	
	//Alle Spielbaren Karten auswerten, welche Gewinnen würden gegen Trick
	private ArrayList<CardBase> returnWinnerHandCards() {
		ArrayList<CardBase> winnerHandCards = new ArrayList<>();
		for(CardBase c: this.playableCards) {
			CardBase[] playedCardsFromTrick = this.getRound().getTricks().getLast().getPlayedCards();
			//Alle Karten vom Trick
			Boolean winAgainstTrick = false;
			for(CardBase cT: playedCardsFromTrick) {
				if(c.compareToWinnerCard(cT)){
					//Diese Karte gewinnt
					winAgainstTrick = true;
				}else {
					//nichts
				}
			}
			if(winAgainstTrick) {
				winnerHandCards.add(c);
			}
		}
		
		return winnerHandCards;
	}
	
	
	// Sucht die Karte aus die gespielt werden soll
	private CardBase determinBestCard() {
		CardBase bestCard = null;
		PlayerBase startingPlayer = botPlayer.getRound().getCurrentStartingPlayer();
		Random rand = new Random ();
		int randInt;
		this.updatePlayableHand(super.getRound().getTricks().getLast());
		
		
		//Logik
		bestCard=returnLowCostPlayableCard();
		
		return bestCard;

		
	}

}
