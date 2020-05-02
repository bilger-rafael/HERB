package herb.server.bot;

import java.util.ArrayList;
import java.util.Random;

import herb.server.ressources.Card;
import herb.server.ressources.DeckOfCards;
import herb.server.ressources.Trick;
import herb.server.ressources.core.Rank;
import herb.server.ressources.core.Suit;
import herb.server.ressources.core.Trump;

public class BetterBot extends BotBase {

	private ArrayList<Card> remainingCards = new ArrayList<>();
	private Card bestCard = null;
	private int randInt;
	private Random rand = new Random();
	
	// Sucht die Karte aus die gespielt werden soll
	@Override
	public Card determinBestCard() {
		bestCard = null;
		this.updatePlayableHand(super.getRound().getTricks().getLast());
		updateRemainingCards();
		

		// Logik des Bots
		switch (this.getNumberPlayedCards()) {
		
		
		// Startspieler Random ausspielen,
		// ausser er hat die höchste verbleibende Karte mit Wert <10 oder es is TopDown/BottomUp
		case (0):
			// Wenn Bot die höchste Karte hat und diese nicht viel Wert hat oder keine Trümpfe mehr=> ausspielen
			// Falls Trumpf nicht ausspielen
			if (BotHasHighstCard(Suit.Clubs)){
					if(BotHighstCardSuit(Suit.Clubs).getGameValue()<10 || !isTrumpsLeft()) {
						if(!BotHighstCardSuit(Suit.Clubs).isTrump())
						{bestCard = BotHighstCardSuit(Suit.Clubs);}
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Clubs);
						break;}
				}
			if (BotHasHighstCard(Suit.Diamonds)){
					if(BotHighstCardSuit(Suit.Diamonds).getGameValue()<10 || !isTrumpsLeft()) {
						if(!BotHighstCardSuit(Suit.Diamonds).isTrump())
						{bestCard = BotHighstCardSuit(Suit.Diamonds);}
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Diamonds);
						break;}
				}
			if (BotHasHighstCard(Suit.Hearts)){
					if(BotHighstCardSuit(Suit.Hearts).getGameValue()<10 || !isTrumpsLeft()) {
						if(!BotHighstCardSuit(Suit.Hearts).isTrump())
						{bestCard = BotHighstCardSuit(Suit.Hearts);}
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Hearts);
						break;}
				}
			if (BotHasHighstCard(Suit.Spades)){
					if(BotHighstCardSuit(Suit.Spades).getGameValue()<10 || !isTrumpsLeft()) {
						if(!BotHighstCardSuit(Suit.Spades).isTrump())
						{bestCard = BotHighstCardSuit(Suit.Spades);}
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Spades);
						break;}
				}
			
			// Falls kein sicherer Stich, Verliererkarte ausspielen
			bestCard = returnLowLoserPlayableCard();
			break;
			
			
		// 2.Spieler, 30% versuch zu stechen und 70% verwerfen oder Stich mehr als 10 oder er hat die höchste Karte
		// Punkte
		case (1):
			// Wenn Bot die höchste Karte hat und diese nicht viel Wert hat oder keine Trümpfe mehr=> ausspielen
			if (BotHasHighstCard(Suit.Clubs)){
					if(BotHighstCardSuit(Suit.Clubs).getGameValue()<10 || !isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Clubs);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Clubs);
						break;}
				}
			if (BotHasHighstCard(Suit.Diamonds)){
					if(BotHighstCardSuit(Suit.Diamonds).getGameValue()<10 || !isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Diamonds);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Diamonds);
						break;}
				}
			if (BotHasHighstCard(Suit.Hearts)){
					if(BotHighstCardSuit(Suit.Hearts).getGameValue()<10 || !isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Hearts);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Hearts);
						break;}
				}
			if (BotHasHighstCard(Suit.Spades)){
					if(BotHighstCardSuit(Suit.Spades).getGameValue()<10 || !isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Spades);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Spades);
						break;}
				}
			
			//Falls Bot Buur hat und Nell gespielt ist=> stechen
			if(botHasBuur()) {
				for (Card c: this.getRound().getTricks().getLast().getPlayedCards()) {
					if (c.getPoints()==14) {
						if(botHandBuur()!=null) {
							bestCard = botHandBuur();
							break;
						}
					}
				}
			}
			
			//Falls nicht höchste Karte, dann mit Wahrscheinlichkeit
			randInt = rand.nextInt(1);
			if (randInt >= 0.3 || this.getRound().getTricks().getLast().getTrickPoints() > 10) {
				if (returnLowCostPlayableCard() != null) {
					bestCard = returnLowCostPlayableCard();
				} else {
					bestCard = returnLowLoserPlayableCard();
				}
			} else {
				bestCard = returnLowLoserPlayableCard();
			}
			break;

			
		// 3. Spieler, 50% versuch zu stechen und 50% verwerfen oder Stich mehr als 10
		// Punkte oder er hat die höchste Karte
		case (2):
			// Wenn Bot die höchste Karte hat oder keine Trümpfe mehr=> ausspielen
			if (BotHasHighstCard(Suit.Clubs)){
					if(!isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Clubs);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Clubs);
						break;}
				}
			if (BotHasHighstCard(Suit.Diamonds)){
					if(!isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Diamonds);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Diamonds);
						break;}
				}
			if (BotHasHighstCard(Suit.Hearts)){
					if(!isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Hearts);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Hearts);
						break;}
				}
			if (BotHasHighstCard(Suit.Spades)){
					if(!isTrumpsLeft()) {
						bestCard = BotHighstCardSuit(Suit.Spades);
						break;}
				//TopDown/BottomsUp
					if(this.getRound().getTrump()==Trump.TopsDown || this.getRound().getTrump()==Trump.BottomsUp) {
						bestCard = BotHighstCardSuit(Suit.Spades);
						break;}
				}
			
			//Falls Bot Buur hat und Nell gespielt ist=> stechen
			if(botHasBuur()) {
				for (Card c: this.getRound().getTricks().getLast().getPlayedCards()) {
					if (c.getPoints()==14) {
						if(botHandBuur()!=null) {
							bestCard = botHandBuur();
							break;
						}
					}
				}
			}
			//Falls nicht höchste Karte, dann mit Wahrscheinlichkeit
			randInt = rand.nextInt(1);
			if (randInt >= 0.5 || this.getRound().getTricks().getLast().getTrickPoints() > 10 ) {
				if (returnLowCostPlayableCard() != null) {
					bestCard = returnLowCostPlayableCard();
				} else {
					bestCard = returnLowLoserPlayableCard();
				}
			} else {
				bestCard = returnLowLoserPlayableCard();
			}
			break;
			
		// Versuch zu stechen, falls Stich mehr als 10 Punkte
		case (3):
			//Falls Bot Buur hat und Nell gespielt ist=> stechen
			if(botHasBuur()) {
				for (Card c: this.getRound().getTricks().getLast().getPlayedCards()) {
					if (c.getPoints()==14) {
						if(botHandBuur()!=null) {
							bestCard = botHandBuur();
							break;
						}
					}
				}
			}	
			// Sonst bei >10 Pointen versuchen zu stechen
			if (this.getRound().getTricks().getLast().getTrickPoints() > 10) {
				if (returnLowCostPlayableCard() != null) {
					bestCard = returnLowCostPlayableCard();
				} else {
					bestCard = returnLowLoserPlayableCard();
				}
			}
			break;

		}

		return bestCard;
	}
		
	// Gibt die tiefste spielbare Karte zurück, die gewinnt
	private Card returnLowCostPlayableCard() {
		ArrayList<Card> winnerCards = returnWinnerHandCards();
		Card temp = null;

		int i = 100;
		for (Card c : winnerCards) {
			if (c.getGameValue() < i) {
				i = c.getGameValue();
				temp = c;
			}
		}
		return temp;
	}

	// Gibt die tiefste spielbare Karte zurück, die verliert (Abwerfen)
	public Card returnLowLoserPlayableCard() {
		ArrayList<Card> loserCards = returnLoserHandCards();
		Card temp = null;

		int i = 100;
		for (Card c : loserCards) {
			if (c.getGameValue() < i) {
				i = c.getGameValue();
				temp = c;
			}
		}
		return temp;
	}

	// Alle Spielbaren Karten auswerten, welche gewinnen würden gegen Trick
	private ArrayList<Card> returnWinnerHandCards() {
		ArrayList<Card> winnerHandCards = new ArrayList<>();
		for (Card c : this.getPlayableCards()) {
			Card[] playedCardsFromTrick = (herb.server.ressources.Card[]) this.getRound().getTricks().getLast()
					.getPlayedCards();
			// Alle Karten vom Trick
			Boolean winAgainstTrick = false;
			for (Card cT : playedCardsFromTrick) {
				if (c.compareToWinnerCard(cT)) {
					// Diese Karte gewinnt
					winAgainstTrick = true;
				} else {
					// nichts
				}
			}
			if (winAgainstTrick) {
				winnerHandCards.add(c);
			}
		}
		return winnerHandCards;
	}

	// Alle spielbaren Karten auswerten, welche verlieren würden gegen Trick
	private ArrayList<Card> returnLoserHandCards() {
		ArrayList<Card> loserHandCards = new ArrayList<>();
		for (Card c : this.getPlayableCards()) {
			Card[] playedCardsFromTrick = (herb.server.ressources.Card[]) this.getRound().getTricks().getLast()
					.getPlayedCards();
			// Alle Karten vom Trick
			Boolean winAgainstTrick = false;
			for (Card cT : playedCardsFromTrick) {
				if (c.compareToWinnerCard(cT)) {
					// Diese Karte gewinnt
					winAgainstTrick = true;
				} else {
					// nichts
				}
			}
			if (!winAgainstTrick) {
				loserHandCards.add(c);
			}
		}
		return loserHandCards;
	}
	
	//Manage verbleibende Karten(Zählt Karten)
	private void updateRemainingCards(){
		DeckOfCards deck = new DeckOfCards();
		this.remainingCards = deck.getCardSet();
		
		// Abgleich gespielten Karten mit dem Deck
		for(Trick t : this.getRound().getTricks()) {
			Card [] tempCards = t.getPlayedCards();
			for(Card c : tempCards) {
				for(Card rC : this.remainingCards) {
					if(c.equals(rC)) {
						this.remainingCards.remove(rC);
					}
				}
				
			}
		}
	}
	
	//Höchster Wert einer Farbe der verbleibenden Karten
	private int returnHighestValueSuit(Suit s) {
		int value = 0;
		
		for (Card c : this.remainingCards) {
			if(c.getSuit().equals(s)) {
				if(c.getGameValue() >= value) {
					value = c.getGameValue();
				}
			}
		}
		return value;
	}
	
	// Hat Bot die höchste Karte einer Suit
	private boolean BotHasHighstCard(Suit s) {
		boolean b = false;
		
		for (Card c : this.getHand().getCards()) {
			if (c.getSuit().equals(s)){
				if(isHighstRemainingCard(c)) {
					b = true;
				}
			}
		}
		return b;
	}
	
	// Gibt die höchste Karte einer Suit zurück
	private Card BotHighstCardSuit(Suit s) {
		Card temp = new Card(s, null);
		
		for (Card c : this.getHand().getCards()) {
			if (c.getSuit().equals(s)){
				if(c.getGameValue()>=temp.getGameValue()) {
					temp = c;
				}
			}
		}
		return temp;
	}
	
	
	// Ist die Input Karte die höchste Karte eines Suits
	private boolean isHighstRemainingCard(Card cInput) {
		boolean b = false;
		
		for (Card c : this.remainingCards) {
			if (cInput.getSuit().equals(c.getSuit())) {
				if(cInput.getGameValue()>=c.getGameValue()) {
					b = true;
				}else {
					b = false;
				}
			}
		}
		return b;
	}
	
	//Hat es noch Trümpfe ausser die vom Bot
	private boolean isTrumpsLeft() {
		boolean b = false;
		for (Card c : this.remainingCards){
			if (c.getTrump().equals(this.getRound().getTrump())) {
				//Prüfen, ob nur Trumps von Bot
				boolean onlyMyTrumps = true;
				for (Card cH : this.getHand().getCards()) {
					while(onlyMyTrumps) {
						if(!c.equals(cH)) {
							onlyMyTrumps = false;
						}
					}
				}
				if(!onlyMyTrumps) {
					b = true;	
				}
			}
		}
		return b;
	}
	
	// Falls nur noch Buur auf den Hand, alle spielbar setzen
	private boolean botHasBuur() {
	if (this.getHand().getCards().stream().filter(x-> x.isPlayable())
		.anyMatch(x-> x.getPoints()==20)) {
		return true;
	}else {
		return false;
		}
	}
	
	//Buur zurückgeben
	private Card botHandBuur() {
		for (Card c: this.getHand().getCards()) {
			if (c.getPoints()==20) {
				return c;
			}
		}
		return null;
	}

}
