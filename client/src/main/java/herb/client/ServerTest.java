package herb.client;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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

		Login l = new Login("TestUser", "TestPW");
		
		try {
			l.login();
		} catch (ExceptionBase e) {
			sl.getLogger().warning("login failed");
		}
		
		try {
			l.register();
		} catch (ExceptionBase e) {
			sl.getLogger().warning("registration failed");
		}
		
		try {
			Player p = (Player) l.login();
			System.out.println(p.getUsername());
		} catch (ExceptionBase e) {
			sl.getLogger().warning("login failed");
		}
		
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
            Handler logHandler = new FileHandler("%t/"
                    + sl.getAPP_NAME() + "_%u" + "_%g" + ".log",
                    1000000, 9);
            logHandler.setLevel(Level.FINEST);
            ourLogger.addHandler(logHandler);
        } catch (Exception e) { // If we are unable to create log files
            throw new RuntimeException("Unable to initialize log files: "
                    + e.toString());
        }

        return ourLogger;
    }
}
