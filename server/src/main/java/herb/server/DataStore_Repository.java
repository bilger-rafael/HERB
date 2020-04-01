package herb.server;

import java.sql.Connection;
import java.sql.DriverManager;
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
		 Statement stmt = null;
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
	
	public void addPlayerToDB(String playername, String password) {
		 Statement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
         // Query ausführen Player der Liste hinzufügen, falls noch nicht verhanden
		 String query = "INSERT INTO Players (Name, Password) VALUES ( '"+ playername +"' , '"+ password +"' ) ON DUPLICATE KEY UPDATE Name='"+playername+"'";
         System.out.println(query);
		 stmt.execute(query);
         stmt.close();
		 
		 
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
	
	public void deletePlayerToDB(String playername) {
		 Statement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
        // Query ausführen
		String query = "DELETE FROM Jass.Players WHERE Name= '"+ playername +"'";
		System.out.println(query);
        stmt.execute(query);
        stmt.close();
		 
		 
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
	
	public String showPlayerfromDB(String playername) {
		 Statement stmt = null;
	     ResultSet rs = null;
	     String password = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
         // Query ausführen Player der Liste hinzufügen, falls noch nicht verhanden
		 String query = "SELECT * FROM Players WHERE name = '" + playername +"'";
		 System.out.println(query);
		 rs = stmt.executeQuery(query);
         rs.next();
         password = rs.getString(2);
       
         stmt.close();
		 
		 
	     }catch (SQLException e) {
	    	 
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
	
	public void addPlayertoHighScore(String playername, String points) {
		 Statement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
         // Query ausführen Player der Liste hinzufügen, falls noch nicht verhanden
		 String query = "INSERT HighScores  (PlayerName, Points) VALUES ( '"+ playername +"' , '"+ points +"' ) ON DUPLICATE KEY UPDATE PlayerName='"+playername+"'";
		 System.out.println(query);
		 stmt.execute(query);
         stmt.close();
		 
		 
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
	
	public void deletePlayerfromHighScore(String playername) {
		 Statement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
       // Query ausführen
		String query = "DELETE FROM Jass.HighScore WHERE Name= '"+ playername +"'";
		System.out.println(query);
		stmt.execute(query);
		stmt.close();
		 
		 
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
	
	public void addLobby(String lobbyName) {
		 Statement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
         // Query ausführen Player der Liste hinzufügen, falls noch nicht verhanden
		 String query = "INSERT INTO Lobbys (LobbyName) VALUES ( '"+ lobbyName +"' ) ON DUPLICATE KEY UPDATE LobbyName='"+lobbyName+"'";
		 System.out.println(query);
		 stmt.execute(query);
         stmt.close();
		 
		 
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

	public void deleteLobby(String lobbyName) {
		 Statement stmt = null;
	     ResultSet rs = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
        // Query ausführen
		String query = "DELETE FROM Jass.Lobbys WHERE LobbyName= '"+ lobbyName +"'";
		System.out.println(query);
		stmt.execute(query);
        stmt.close();
		 
		 
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
	
	public ArrayList<String> showLobbys(){
		ArrayList<String> tempList = new ArrayList<>();
		Statement stmt = null;
	    ResultSet rs = null;
	    String tempLobbyName = null;
	     
	     try {
		 stmt = this.cn.createStatement();
		 
         // Query ausführen Player der Liste hinzufügen, falls noch nicht verhanden
		 String query = "SELECT * FROM Lobbys";
		 System.out.println(query);
		 rs = stmt.executeQuery(query);
         while(rs.next()) {
        	 
        	 tempLobbyName = rs.getString(1);
        	 tempList.add(tempLobbyName);
         	}
       
         stmt.close();
		 
		 
	     }catch (SQLException e) {
	    	 
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
