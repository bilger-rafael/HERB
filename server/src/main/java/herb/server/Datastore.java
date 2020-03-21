package herb.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import herb.server.ressources.Game;
import herb.server.ressources.Lobby;
import herb.server.ressources.Player;

public class Datastore {
	
    private static Datastore datastore; // singleton
    
    public HashMap<String,Player> players = new HashMap<String,Player>();
    public HashMap<UUID,Game> games = new HashMap<UUID,Game>();
    public HashMap<UUID,Lobby> lobbys = new HashMap<UUID,Lobby>();
    
    public static Datastore getInstance() {
        if (datastore == null)
        	datastore = new Datastore();
        return datastore;
    }

}
