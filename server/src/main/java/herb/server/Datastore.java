package herb.server;

import java.util.ArrayList;

import herb.server.ressources.Game;
import herb.server.ressources.Lobby;
import herb.server.ressources.Player;

public class Datastore {
	
    private static Datastore datastore; // singleton
    
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Game> games = new ArrayList<Game>();
    private ArrayList<Lobby> lobbys = new ArrayList<Lobby>();
    
    public static Datastore getInstance() {
        if (datastore == null)
        	datastore = new Datastore();
        return datastore;
    }
	
}
