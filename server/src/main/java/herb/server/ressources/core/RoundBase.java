package herb.server.ressources.core;

//Etter
public abstract class RoundBase {
	private TrickBase[] tricks;
	private Trump currentTrump;
	
	
	public abstract TrickBase getCurrentTrick();
	
	protected abstract void genTrump();
	
	public abstract Trump getCurrentTrump();
}
