package herb.server.ressources;

import java.util.UUID;

import herb.server.DataStore_Repository;
import herb.server.Datastore;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.LoginBase;
import herb.server.ressources.core.PlayerBase;

//Bilger
public class Login extends LoginBase {

	public Login(String username, String password) {
		super(username, password);
	}

	@Override
	public PlayerBase login() throws ExceptionBase {
		Login login = Datastore.getInstance().logins.get(this.getUsername());

		if (login == null)
			throw new PlayerNotFoundException();

		if (!login.getPassword().equals(this.getPassword()))
			throw new PlayerLoginFailedException();
		
		//Etter Login mit MySQL DB
			//Check User existiert
			if(!DataStore_Repository.getDB().checkPlayerExist(this.getUsername())) {
				throw new PlayerNotFoundException();
			}
			//Check Passwort stimmt
			if(this.getPassword()!= DataStore_Repository.getDB().showPlayerPasswordfromDB(this.getUsername())) {
				throw new PlayerLoginFailedException();
			}
			
		

		// return player
		Player player = new Player(login.getUsername(), UUID.randomUUID().toString().toUpperCase());
		Datastore.getInstance().players.put(player.getUsername(), player);
		return player;
		
		

	}

	@Override
	public void register() throws ExceptionBase {
		if (Datastore.getInstance().logins.containsKey(this.getUsername()))
			throw new PlayerAlreadyExistsException();

		// TODO check username and password length, if needed

		Datastore.getInstance().logins.put(this.getUsername(), this);
		
		//Etter Eintrag in MYSQL-DB
		int i = DataStore_Repository.getDB().addPlayerToDB(this.getUsername(), this.getPassword());
		if(i==0) {
			throw new PlayerAlreadyExistsException();
		}
	
	}

}
