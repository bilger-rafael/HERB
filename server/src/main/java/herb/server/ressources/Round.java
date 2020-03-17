package herb.server.ressources;

import java.util.Random;

import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.TrickBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase{
	private static Trump currentTrump;
	private DeckOfCards deck;
	
	public Round() {
		genTrump();
		this.deck = new DeckOfCards(this.currentTrump);
		
	}

	@Override
	public TrickBase getCurrentTrick() {
		// TODO Auto-generated method stub
		return null;
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
