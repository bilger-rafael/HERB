package herb.server.ressources;

import java.util.Random;

import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.TrickBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase{
	private Trump currentTrump;
	private DeckOfCards deck;
	protected PlayerBase[] players = new PlayerBase[4];
	private Trick currentTrick;
	
	public Round(PlayerBase[] players) {
		super();
		genTrump();
		this.deck = new DeckOfCards(this.currentTrump);
		this.players = players;
		
		//Hände leeren
		for (int i = 0; i<this.players.length; i++) {
			this.players[i].clearHand();
		}
		
		//Karten verteilen
		while (this.deck.getCardsRemaining()>0) {	
		}
		for (int i = 0; i<this.players.length; i++) {
			this.players[i].addCardtoHand(this.deck.dealCard());
		}
		
		//TODO Hände sortieren
		
		//Erster Trick erstellen
		createNewTrick();
	}

	@Override
	public TrickBase getCurrentTrick() {
		
		return this.currentTrick;
	}
	
	private void createNewTrick() {
		Trick trick = new Trick(this.players);
		this.currentTrick=trick;
	}
	
	@Override
	protected void genTrump() {
		Random rand = new Random();
		int i = rand.nextInt((6));
		currentTrump = Trump.values()[i];
	}
	
	@Override
	public Trump getCurrentTrump() {
		return currentTrump;
	}

}
