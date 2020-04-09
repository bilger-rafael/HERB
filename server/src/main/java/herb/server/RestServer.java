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
	static Connection cn = null;
	static String ip = null;
	static String user = null;
	static String pw = null;
	static DataStore_Repository db;

	public static void main(String[] args) throws Exception {

		SpringApplication.run(RestServer.class, args);
		try{start();}
		catch (Exception e) {
			//TODO catch
		};

	}
	
    public static void start() throws Exception {
    	
		//TODO ETTER DB verbinden => Driver wird nicht gefunden, warum?
		startDatabase();
    }
	
    //ETTER Verbindung zur Server DB erstellen
    private static void startDatabase() {
    	boolean valid = false;
    	Scanner scan = new Scanner(System.in);
    	
    	while(!valid) {
    		System.out.println("Geben Sie den Pfad zur MySQL-Datenbank ein");
    		System.out.println(" Für Mac User: jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC");
    		ip = scan.nextLine();
    		
    		System.out.println("Geben Sie ihren MySQL-User ein");
    		System.out.println("Üblicherweise root");
    		user = scan.nextLine();
    		
    		System.out.println("Geben Sie ihr MySQL-Passwort ein");
    		pw = scan.nextLine();
    		
    		System.out.println("Die Eingaben werden nun geprüft");
    		
    		db = new DataStore_Repository(ip, user, pw);
    			
    		valid = db.connectDatabase(ip, user, pw);
    		
    		if(!valid) {
    			System.out.println("Eigaben haben nicht funktioniert, wiederholen");
    		}else {
    			System.out.println("Verbindungsaufbau hat funktioniert");
    		}
    	}
    	
    	//DB initialisieren
    	db.dbInitialize();
    	
    }
}
