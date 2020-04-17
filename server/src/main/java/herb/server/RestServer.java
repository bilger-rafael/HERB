package herb.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
	// static Connection cn = null;
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
		// TODO if args are missing (length < 3), show explanation: first arg = dbUrl second arg = dbUser etc.
		// Help for URL:
		// Mac:     "jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC";
		// Windoof: "jdbc:mysql://localhost:3306/?useLegacyDatetimeCode=false&serverTimezone=UTC";
		if (args.length < 3)
			throw new Exception();

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

		// TODO if args are not valid, show explanation: first arg = dbUrl second arg = dbUser etc.
		// Help for URL:
		// Mac:     "jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC";
		// Windoof: "jdbc:mysql://localhost:3306/?useLegacyDatetimeCode=false&serverTimezone=UTC";
		if (!DataStore_Repository.getDB().connectDatabase())
			throw new Exception();

		// DB initialisieren
		DataStore_Repository.getDB().dbInitialize();

	}
}
