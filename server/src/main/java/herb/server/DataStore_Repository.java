package herb.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//ETTER
public class DataStore_Repository {
	Connection cn = null;
	
	//Test Infos
	private String ipAdress = "jdbc:mysql://localhost/JASS?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String userDB = "root";
	private String pwDB = "A01051991";
	
	//Input Parameter
	private String ip = null;
	private String user = null;
	private String pw = null;
	


	
	
	//Test Konstructor
	public DataStore_Repository(){
		connectDatabase(ipAdress, userDB, pwDB);
	}
	
	//Konstruktur mit Input
	public DataStore_Repository(String ip, String user, String pw) {
		this.ip = ip;
		this.user = user;
		this.pw = pw;
		
		
	}
		

	public void stop() {
	        if (cn != null) try {
	            if (!cn.isClosed()) cn.close();
	        } catch (Exception e) {}
	}

	//Verbindet mit der DB und gibt eine Rückmeldung
	public boolean connectDatabase(String ip, String user, String pw) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
        try {
            this.cn = DriverManager.getConnection(ip, user, pw);
            return true;
            
        } catch (SQLException e) {
        	e.getMessage();
        	e.printStackTrace();
        	return false;
        } finally {
            if (rs != null) try {
                if (!rs.isClosed()) rs.close();
            } catch (Exception e) {}
            if (stmt != null) try {
                if (!stmt.isClosed()) stmt.close();
            } catch (Exception e) {}
        }
    }
	
	
	public void dbInitialize() {
		try {
			//Teste, ob es DB bereits gibt
			if(this.dbExist()) {
				//Tabellen wenn nötig erstellen
				this.createPlayerTable();
				this.createLobbyTable();
				this.createHighScoresTable();
    		
			}else {
				//DB mit Tabellen erstellen 
				this.createSchema();
				this.createPlayerTable();
				this.createLobbyTable();
				this.createHighScoresTable();
				
				//Grunddaten erstellen
				this.addPlayerToDB("Etter", "Etter");
				this.addPlayerToDB("Bilger", "Bilger");
				this.addPlayerToDB("Rösti", "Rösti");
				this.addPlayerToDB("Herren", "Herren");
				this.addLobby("BaseLobby");
				this.addLobby("LobbySonntag");
				this.addPlayertoHighScore("Etter", "123");
				this.addPlayertoHighScore("Herren", "130");
			
			}
		}catch (SQLException e) {
			System.out.println("Initialisierung hat nicht funktioniert");
		}
	}
	
	//Testet, ob es die DB bereits gibt, Idee von StackOverflow
	private boolean dbExist() {
	     ResultSet rs = null;
	     String dbName="JASSHERB";
       try {
           this.cn = DriverManager.getConnection(this.ip, this.user, this.pw);
           rs = this.cn.getMetaData().getCatalogs();

           while (rs.next()) {
        	   
             String databaseName = rs.getString(1);
               if(databaseName.equals(dbName)){
                   return true;
               }
           }
           rs.close();

       }
       catch(Exception e){
           e.printStackTrace();
           
       }
       return false;
   }
	
	
	//Idee von Stackoverflow: 
	//https://stackoverflow.com/questions/11909324/creating-a-database-table-if-it-does-not-exist-in-java-production-code-and-confi
	private void createSchema() throws SQLException{
	    String sqlCreate = "CREATE SCHEMA JASSHERB" ;
	    
	    Statement stmt = this.cn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	private void createPlayerTable() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS JASSHERB.Players (" 
	            + "   Name VARCHAR(45) NOT NULL,"
	            + "   Password VARCHAR(45) NOT NULL,"
	            + "   PRIMARY KEY (Name))";
	    
	    Statement stmt = this.cn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	private void createLobbyTable() throws SQLException{
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS JASSHERB.Lobbys (" 
	            + "   LobbyName VARCHAR(45) NOT NULL,"
	            + "   PRIMARY KEY (LobbyName))";
	    
	    Statement stmt = this.cn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	private void createHighScoresTable() throws SQLException{
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS JASSHERB.HighScores (" 
	            + "   PlayerName VARCHAR(45) NOT NULL,"
	            + "   Points INT NOT NULL,"
	            + "   PRIMARY KEY (PlayerName))";
	    
	    Statement stmt = this.cn.createStatement();
	    stmt.execute(sqlCreate);
	}
		
	//Fügt einen Player der DB mit PreparedStatment hinzu
	public void addPlayerToDB(String playername, String password) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     
	     try {
	    	 stmt = this.cn.prepareStatement("INSERT IGNORE INTO JASSHERB.Players (Name, Password) VALUES (?,?)");
	    	 stmt.setString(1, playername);
	    	 stmt.setString(2, password);
	    	 int answer = stmt.executeUpdate();
	    	 System.out.println(answer+" eingefügt");
		 
	     }catch (SQLException e) {
	    	 System.out.println(e);
	    	 
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
	}
	
	//Löscht einen Player der DB mit PreparedStatment
	public void deletePlayerFromDB(String playername) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     
	     try { 
	    	 stmt = this.cn.prepareStatement("DELETE FROM JASSHERB.Players WHERE Name=?");
	    	 stmt.setString(1, playername);
	    	 int answer = stmt.executeUpdate();
	    	 System.out.println(answer+" gelöscht");
	     }catch (SQLException e) {
	    	 System.out.println(e);
	    	 
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
	}
	
	//Gibt Player PW zurück der DB mit PreparedStatment
	public String showPlayerPasswordfromDB(String playername) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     String password = null;
	     
	     try {
		     stmt = this.cn.prepareStatement("SELECT * FROM JASSHERB.Players WHERE name=?");
		     stmt.setString(1, playername);
		   	 rs = stmt.executeQuery();
		   	 rs.next();
		   	 password = rs.getString(2);
		 
	     }catch (SQLException e) {
	    	 System.out.println(e);
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
		return password;
	}
	
	//Fügt einen Player HighScore der DB mit PreparedStatment hinzu
	public void addPlayertoHighScore(String playername, String points) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     
	     try {
			 stmt = this.cn.prepareStatement("INSERT IGNORE INTO JASSHERB.HighScores (PlayerName, Points) VALUES (?,?)");
			 stmt.setString(1, playername);
			 stmt.setString(2, points);
			 int answer = stmt.executeUpdate();
			 System.out.println(answer+" eingefügt");
	     }catch (SQLException e) {
	    	 System.out.println(e);
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
	}
	
	//Löscht einen Player HighScore der DB mit PreparedStatment
	public void deletePlayerfromHighScore(String playername) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		     stmt = this.cn.prepareStatement("DELETE FROM JASSHERB.HighScores WHERE PlayerName=?");
		     stmt.setString(1, playername);
		   	 int answer = stmt.executeUpdate();
			 System.out.println(answer+" gelöscht");	 
	     }catch (SQLException e) {
	    	 System.out.println(e);
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
	}
	
	//Fügt eine Lobby der DB mit PreparedStatment hinzu
	public void addLobby(String lobbyName) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     
	     try {
			 stmt = this.cn.prepareStatement("INSERT IGNORE INTO JASSHERB.Lobbys (LobbyName) VALUES (?)");
			 stmt.setString(1, lobbyName);
			 int answer = stmt.executeUpdate();
			 System.out.println(answer+" eingefügt");
	     }catch (SQLException e) {
	    	 System.out.println(e);
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
	}
	
	//Löscht eine Lobby der DB mit PreparedStatment
	public void deleteLobby(String lobbyName) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		     stmt = this.cn.prepareStatement("DELETE FROM JASSHERB.Lobbys WHERE LobbyName=?");
		     stmt.setString(1, lobbyName);
		   	 int answer = stmt.executeUpdate();
			 System.out.println(answer+" gelöscht");
	     }catch (SQLException e) {
	    	 
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
	}
	
	//Gibt die Lobbys der DB mit PreparedStatment zurück
	public ArrayList<String> showLobbys(){
		ArrayList<String> tempList = new ArrayList<>();
		PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String tempLobbyName = null;
	     
	     try {
		     stmt = this.cn.prepareStatement("SELECT * FROM JASSHERB.Lobbys");
		   	 rs = stmt.executeQuery();
		   	 while(rs.next()) {
        	 
		 		tempLobbyName = rs.getString(1);
		 		tempList.add(tempLobbyName);
         	}		  
	     }catch (SQLException e) {
	    	 System.out.println(e);
	     }finally {
	            if (rs != null) try {
	                if (!rs.isClosed()) rs.close();
	            } catch (Exception e) {}
	            if (stmt != null) try {
	                if (!stmt.isClosed()) stmt.close();
	            } catch (Exception e) {}
	     }
		return tempList;

		}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}
