package herb.server.ressources;

import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Round extends RoundBase{
	Trump currentTrump;
	
	@Override
	public Round() {
		this.genTrump();
	}

	@Override
	public TrickBase getCurrentTrick() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	private void genTrump() {
		
		
	}
	
	@Override
	public Trump getCurrentTrump() {
		return currentTrump;
	}

}
