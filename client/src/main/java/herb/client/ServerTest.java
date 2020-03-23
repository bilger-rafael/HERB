package herb.client;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import herb.client.ressources.Lobby;
import herb.client.ressources.Login;
import herb.client.ressources.Player;
import herb.client.ressources.core.ExceptionBase;
import herb.client.utils.Configuration;
import herb.client.utils.ServiceLocator;

public class ServerTest {
	static ServiceLocator sl;

	public static void main(String[] args) throws ExceptionBase {

		sl = ServiceLocator.getInstance();
		sl.setLogger(configureLogging());
		sl.setConfiguration(new Configuration());

		/*
		 * 
		 * Login and registration
		 * 
		 */
		Login login = new Login("TestUser", "TestPW");

		// login that should NOT work
		try {
			login.login();
		} catch (ExceptionBase e) {
			sl.getLogger().warning("login failed");
		}

		// registration that should work
		login.register();

		// registration that should NOT work
		try {
			login.register();
		} catch (ExceptionBase e) {
			sl.getLogger().warning("registration failed");
		}

		// login that should work
		Player player = (Player) login.login();

		/*
		 * 
		 * Lobby
		 * 
		 */
		// lobby read that should NOT work
		try {
			Lobby.readLobby("TestLobby");
		} catch (ExceptionBase e) {
			sl.getLogger().warning("lobby read failed");
		}

		// lobby list read that should NOT work
		try {
			Lobby.readLobbyList();
		} catch (ExceptionBase e) {
			sl.getLogger().warning("lobby list read failed");
		}

		// lobby create that should work
		Lobby.createLobby("TestLobby");

		// lobby create that should NOT work
		try {
			Lobby.createLobby("TestLobby");
		} catch (ExceptionBase e) {
			sl.getLogger().warning("lobby create failed");
		}

		// lobby read that should work
		Lobby lobby = Lobby.readLobby("TestLobby");

		// lobby list read that should work
		Lobby[] lobbyList = Lobby.readLobbyList();

		// lobby join that should work
		lobby.addPlayer(player);

	}

	private static Logger configureLogging() {
		Logger rootLogger = Logger.getLogger("");
		rootLogger.setLevel(Level.FINEST);

		// By default there is one handler: the console
		Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
		defaultHandlers[0].setLevel(Level.INFO);

		// Add our logger
		Logger ourLogger = Logger.getLogger(sl.getAPP_NAME());
		ourLogger.setLevel(Level.FINEST);

		// Add a file handler, putting the rotating files in the tmp directory
		try {
			Handler logHandler = new FileHandler("%t/" + sl.getAPP_NAME() + "_%u" + "_%g" + ".log", 1000000, 9);
			logHandler.setLevel(Level.FINEST);
			ourLogger.addHandler(logHandler);
		} catch (Exception e) { // If we are unable to create log files
			throw new RuntimeException("Unable to initialize log files: " + e.toString());
		}

		return ourLogger;
	}
}
