package herb.client.ressources;

import herb.client.ressources.core.TrickBase;

public class Trick extends TrickBase<Player, Card>{

	// default constructor for json deserialization
	public Trick() {
		super(null, null);
	}

	public Trick(Player[] players, Player startingPlayer) {
		super(players, startingPlayer);
		// TODO Auto-generated constructor stub
	}

}
