package herb.server.ressources.core;

//Etter
public abstract class RoundBase {
	private TrickBase[] tricks;
	private Trump current Trump;
	
	public abstract RoundBase();
	
	public abstract TrickBase getCurrentTrick();
	
	private abstract void genTrump();
	
	public abstract Trump getCurrentTrump();
}
