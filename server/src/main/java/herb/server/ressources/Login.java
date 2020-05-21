package herb.server.ressources;

import java.util.UUID;

import herb.server.DataStore_Repository;
import herb.server.Datastore;
import herb.server.ressources.core.ExceptionBase;
import herb.server.ressources.core.LoginBase;

public class Login extends LoginBase<Player> {

	public Login(String username, String password) {
		super(username, password);
	}

	@Override
	public Player login() throws ExceptionBase {

		// Etter Login mit MySQL DB
		// Check User existiert
		if (!DataStore_Repository.getDB().checkLoginExist(this.getUsername())) {
			throw new PlayerNotFoundException();
		}

		// Etter Check Passwort stimmt
		if (!this.getPassword().equals(DataStore_Repository.getDB().showLoginPasswordfromDB(this.getUsername()))) {
			throw new PlayerLoginFailedException();
		}

		// Bilger check if player is already logged in
		if (Datastore.getInstance().players.get(this.getUsername()) != null)
			return Datastore.getInstance().players.get(this.getUsername());

		// Bilger add player and return it
		Player player = new Player(this.getUsername(), UUID.randomUUID().toString().toUpperCase());
		Datastore.getInstance().players.put(player.getUsername(), player);
		return player;

	}

	@Override //Bilger
	public void register() throws ExceptionBase {
		if (Datastore.getInstance().logins.containsKey(this.getUsername()))
			throw new PlayerAlreadyExistsException();

		// Etter Eintrag in MYSQL-DB
		int i = DataStore_Repository.getDB().addLoginToDB(this.getUsername(), this.getPassword());
		if (i == 0) {
			throw new PlayerAlreadyExistsException();
		}
	}

}
