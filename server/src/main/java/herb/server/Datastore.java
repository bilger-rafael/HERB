package herb.server;

import java.util.HashMap;
import java.util.UUID;

import herb.server.ressources.Game;
import herb.server.ressources.Lobby;
import herb.server.ressources.Login;
import herb.server.ressources.Player;

//Bilger
public class Datastore {
	
    private static Datastore datastore; // singleton
    
    public HashMap<String,Login> logins = new HashMap<String,Login>();
    public HashMap<String,Player> players = new HashMap<String,Player>();
    public HashMap<String,Lobby> lobbys = new HashMap<String,Lobby>();
    public HashMap<UUID,Game> games = new HashMap<UUID,Game>();

    
    public static Datastore getInstance() {
        if (datastore == null)
        	datastore = new Datastore();
        return datastore;
    }

}
