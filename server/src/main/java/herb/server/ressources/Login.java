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

		// Etter Login mit MySQL DB
		// Check User existiert
		if (!DataStore_Repository.getDB().checkLoginExist(this.getUsername())) {
			throw new PlayerNotFoundException();
		}
		
		// Check Passwort stimmt
		if (!this.getPassword().equals(DataStore_Repository.getDB().showLoginPasswordfromDB(this.getUsername()))) {
			throw new PlayerLoginFailedException();
		}
		
		// check if player is already logged in
		if (Datastore.getInstance().players.get(this.getUsername()) != null) 
			return Datastore.getInstance().players.get(this.getUsername());

		// add player and return it
		Player player = new Player(this.getUsername(), UUID.randomUUID().toString().toUpperCase());
		Datastore.getInstance().players.put(player.getUsername(), player);
		return player;

	}

	@Override
	public void register() throws ExceptionBase {
		if (Datastore.getInstance().logins.containsKey(this.getUsername()))
			throw new PlayerAlreadyExistsException();

		// TODO check username and password length, if needed
		// TODO check username is not Bot...

		// Etter Eintrag in MYSQL-DB
		int i = DataStore_Repository.getDB().addLoginToDB(this.getUsername(), this.getPassword());
		if (i == 0) {
			throw new PlayerAlreadyExistsException();
		}

	}

}
