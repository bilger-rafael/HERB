package herb.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
	static String dbUrl = null;
	static String dbUser = null;
	static String dbPassword = null;

	public static void main(String[] args) throws Exception {

		readArguments(args);

		startDatabase();

		SpringApplication.run(RestServer.class, args);

	}

	// Bilger
	private static void readArguments(String[] args) throws Exception {
		if (args.length < 3)
			throw new Exception("Program Arguments missing \n" + getArgumentsDescription());

		dbUrl = args[0];
		dbUser = args[1];
		dbPassword = args[2];
	}

	// ETTER Verbindung zur Server DB erstellen
	private static void startDatabase() throws Exception {
		// DB erstellen
		DataStore_Repository.getDB();
		DataStore_Repository.getDB().setIp(dbUrl);
		DataStore_Repository.getDB().setUser(dbUser);
		DataStore_Repository.getDB().setPw(dbPassword);

		if (!DataStore_Repository.getDB().connectDatabase())
			throw new Exception("Program Arguments invalid \n" + getArgumentsDescription());

		// DB initialisieren
		DataStore_Repository.getDB().dbInitialize();
	}
	
	// Bilger
	private static String getArgumentsDescription() {
		return "1. Argument: Database Url \n" +
			   "\t Mac: \t\t \"jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC\" \n" +
			   "\t Windoof: \t \"jdbc:mysql://localhost:3306/?useLegacyDatetimeCode=false&serverTimezone=UTC\" \n" +
			   "2. Argument: Database User \n" +
			   "3. Argument: Database Password \n";
	}
}
