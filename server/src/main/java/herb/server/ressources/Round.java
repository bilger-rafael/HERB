package herb.server.ressources;

import java.util.Random;

import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Round extends RoundBase{
	private Trump currentTrump;
	
	@Override
	public Round() {
		genTrump();
	}

	@Override
	public TrickBase getCurrentTrick() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	private void genTrump() {
		Random rand = new Random();
		currentTrump = Trump.values()[rand.nextInt((Enum.GetNames(typeof(Trump)).Length-1))];
	}
	
	@Override
	public Trump getCurrentTrump() {
		return currentTrump;
	}

}
