package herb.client.ressources;

import herb.client.ressources.core.RoundBase;

public class Round extends RoundBase<Player, Trick>{

	// default constructor for json deserialization
	public Round() {
		super(null);
	}
	
	public Round(Player[] players) {
		super(players);
		// TODO Auto-generated constructor stub
	}

}
