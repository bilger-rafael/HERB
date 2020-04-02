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
	String ipAdress = "jdbc:mysql://localhost/JASS?useLegacyDatetimeCode=false&serverTimezone=UTC";
	String userDB = "root";
	String pwDB = "A01051991";
	
	public DataStore_Repository(){
		connectDatabase();
	}
		

	public void stop() {
	        if (cn != null) try {
	            if (!cn.isClosed()) cn.close();
	        } catch (Exception e) {}
	}



	public void connectDatabase() {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;

        
        try {
            this.cn = DriverManager.getConnection(ipAdress, userDB, pwDB);
           
            
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
	
	//Fügt einen Player der DB mit PreparedStatment hinzu
	public void addPlayerToDB(String playername, String password) {
		 PreparedStatement stmt = null;
	     ResultSet rs = null;
	     
	     try {
	    	 stmt = this.cn.prepareStatement("INSERT IGNORE INTO JASS.Players (Name, Password) VALUES (?,?)");
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
	    	 stmt = this.cn.prepareStatement("DELETE FROM Jass.Players WHERE Name=?");
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
		     stmt = this.cn.prepareStatement("SELECT * FROM Players WHERE name=?");
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
			 stmt = this.cn.prepareStatement("INSERT IGNORE INTO JASS.HighScores (PlayerName, Points) VALUES (?,?)");
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
		     stmt = this.cn.prepareStatement("DELETE FROM JASS.HighScores WHERE PlayerName=?");
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
			 stmt = this.cn.prepareStatement("INSERT IGNORE INTO JASS.Lobbys (LobbyName) VALUES (?)");
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
		     stmt = this.cn.prepareStatement("DELETE FROM JASS.Lobbys WHERE LobbyName=?");
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
		     stmt = this.cn.prepareStatement("SELECT * FROM Lobbys");
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
	}
