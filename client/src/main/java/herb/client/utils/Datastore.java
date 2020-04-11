package herb.client.utils;

import herb.client.ressources.Player;

public class Datastore {
	
	private static Datastore datastore;
	private Player mainPlayer;
	
	private Datastore() {
		
	}
    
    public static Datastore getInstance() {
        if (datastore == null)
        	datastore = new Datastore();
        return datastore;
    }

	public Player getMainPlayer() {
		return mainPlayer;
	}

	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

}
