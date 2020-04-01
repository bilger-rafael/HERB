package herb.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
	static Connection cn = null;
	static String ipAdress = "jdbc:mysql://127.0.0.1:3306/";
	static String userDB = "root";
	static String pwDB = "JASS";
	static String optionInfo = "?connectTimeout=5000";

	public static void main(String[] args) throws Exception {

		SpringApplication.run(RestServer.class, args);
		try{start();}
		catch (Exception e) {
			//TODO catch
		};

	}
	
    public static void start() throws Exception {
    	
		//ETTER DB verbinden
		connectDatabase();
    }
	
    private static void connectDatabase() {
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            cn = DriverManager.getConnection(ipAdress + optionInfo, userDB, pwDB);
            System.out.print(cn);
            
            // Execute some SQL
            stmt = cn.createStatement();
            stmt.execute("USE JASS");

            // Add some data
            stmt.execute("INSERT INTO Players (idPlayers, Name, Password) VALUES (1, 'Etter', '123')");
            stmt.close();
            
        } catch (SQLException e) {
        		//TODO catch Exception
        } finally {
            if (rs != null) try {
                if (!rs.isClosed()) rs.close();
            } catch (Exception e) {}
            if (stmt != null) try {
                if (!stmt.isClosed()) stmt.close();
            } catch (Exception e) {}
        }
    }
   
    
    public void stop() {
        if (cn != null) try {
            if (!cn.isClosed()) cn.close();
        } catch (Exception e) {}
    }

}
