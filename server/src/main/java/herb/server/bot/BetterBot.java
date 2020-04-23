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
			this.play(tempCard);
		} catch (ExceptionBase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Sucht die Karte aus die gespielt werden soll
	private CardBase determinBestCard() {
		CardBase bestCard = null;
		PlayerBase startingPlayer = this.getRound().getCurrentStartingPlayer();
		Random rand = new Random ();
		int randInt;
		this.updatePlayableHand(super.getRound().getTricks().getLast());
		
		//Logik des Bots
		int myPosition =this.getRound().getTricks().getLast().getNumberPlayedCards();
		switch (myPosition) {
		//Startspieler Random ausspielen
		case (0):
			randInt =rand.nextInt(this.getHand().getCards().size());
			bestCard = this.getHand().getCards().get(randInt);
			break;
		//2.Spieler, 30% versuch zu stechen und 70% verwerfen oder Stich mehr als 10 Punkte 
		case(1):
			randInt =rand.nextInt(1);
			if(randInt>=0.3||this.getRound().getTricks().getLast().getTrickPoints()>10) {
				if(returnLowCostPlayableCard()!=null) {
					bestCard = returnLowCostPlayableCard();
				}else {
					bestCard = returnLowLoserPlayableCard();
				}
			}else {
				bestCard = returnLowLoserPlayableCard();	
			}
			break;

		
		//3. Spieler, 50% versuch zu stechen und 50% verwerfen oder Stich mehr als 10 Punkte 
		case(2):
			randInt =rand.nextInt(1);
			if(randInt>=0.5||this.getRound().getTricks().getLast().getTrickPoints()>10) {
				if(returnLowCostPlayableCard()!=null) {
					bestCard = returnLowCostPlayableCard();
				}else {
					bestCard = returnLowLoserPlayableCard();
				}
			}else {
				bestCard = returnLowLoserPlayableCard();	
			}
			break;
		
		//Versuch zu stechen, falls Stich mehr als 10 Punkte
		case(3):
			if(this.getRound().getTricks().getLast().getTrickPoints()>10) {
				if(returnLowCostPlayableCard()!=null) {
					bestCard = returnLowCostPlayableCard();
				}else {
					bestCard = returnLowLoserPlayableCard();
				}
			}
			break;
		
		}
		
		if(this.equals(startingPlayer)) {
			
		}else {
		
		returnLowLoserPlayableCard();
		bestCard=returnLowCostPlayableCard();
		
			
		}
		return bestCard;
	}
	
	
	//Gibt die tiefste spielbare Karte zurück, die gewinnt
	private CardBase returnLowCostPlayableCard() {
		ArrayList<CardBase> winnerCards = returnWinnerHandCards();
		CardBase temp = null;
		
		int i=100;
		for(CardBase c:winnerCards) {
			if(c.getGameValue()<i) {
				i=c.getGameValue();
				temp=c;
			}
		}
		return temp;
	}
	
	//Gibt die tiefste spielbare Karte zurück, die verliert (Abwerfen)
	public CardBase returnLowLoserPlayableCard() {
		ArrayList<CardBase> loserCards = returnLoserHandCards();
		CardBase temp = null;
		
		int i=100;
		for(CardBase c:loserCards) {
			if(c.getGameValue()<i) {
				i=c.getGameValue();
				temp=c;
			}
		}
		return temp;
	}
	
	
	//Alle Spielbaren Karten auswerten, welche gewinnen würden gegen Trick
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
	
	//Alle Spielbaren Karten auswerten, welche verlieren würden gegen Trick
	private ArrayList<CardBase> returnLoserHandCards() {
		ArrayList<CardBase> loserHandCards = new ArrayList<>();
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
			if(!winAgainstTrick) {
				loserHandCards.add(c);
			}
		}
		return loserHandCards;
	}

}
