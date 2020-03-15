package herb.server;

import java.util.HashMap;
import java.util.UUID;

import herb.server.ressources.Game;
import herb.server.ressources.Lobby;
import herb.server.ressources.Player;

public class Datastore {
	
    private static Datastore datastore; // singleton
    
    public HashMap<UUID,Game> games = new HashMap<UUID,Game>();
    public HashMap<UUID,Player> players = new HashMap<UUID,Player>();
    public HashMap<UUID,Lobby> lobbys = new HashMap<UUID,Lobby>();
    
    public static Datastore getInstance() {
        if (datastore == null)
        	datastore = new Datastore();
        return datastore;
    }

}
