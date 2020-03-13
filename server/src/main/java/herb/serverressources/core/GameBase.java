package herb.serverressources.core;

public abstract class GameBase {
	private PlayerBase[] players;
	private RoundBase[] rounds;
	
	public abstract RoundBase startRound();
}
