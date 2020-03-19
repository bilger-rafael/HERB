package herb.server.ressources.core;

//Etter
public abstract class RoundBase {
	private TrickBase[] tricks;
	private Trump currentTrump;
	protected PlayerBase[] players = new PlayerBase[4];
	
	public RoundBase (PlayerBase[] players) {
		this.players=players;
	}
	
	public abstract TrickBase getCurrentTrick();
	
	protected abstract void genTrump();
	
	public abstract Trump getCurrentTrump();
	
}
