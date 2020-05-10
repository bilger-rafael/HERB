package herb.client.ressources;

import herb.client.ressources.core.RoundBase;

public class Round extends RoundBase<Game, Player, Trick>{

	// default constructor for json deserialization
	public Round() {
		super(null, null);
	}
	
	public Round(Game game, Player[] players) {
		super(game, players);
		// TODO Auto-generated constructor stub
	}

}
