package herb.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import herb.server.ressources.Lobby;
import herb.server.ressources.core.ExceptionBase;

//ETTER 
//fast Singleton, Unterschied ist resetDB
public class DataStore_Repository {
	private Connection cn = null;
	private static DataStore_Repository db; // singleton

	// Input Parameter
	private String ip = null;
	private String user = null;
	private String pw = null;

	// Idee von Factory Methode App aus SE HS19 übernommen
	/**
	 * Factory method for returning the singleton
	 */
	public static DataStore_Repository getDB() {
		if (db == null)
			db = new DataStore_Repository();
		return db;
	}

	// Falls Anmelde-Infos falsch sind, zurücksetzen
	protected static void resetDB() {
		db = null;
	}

	public void stop() {
		if (cn != null)
			try {
				if (!cn.isClosed())
					cn.close();
			} catch (Exception e) {
			}
	}

	// Verbindet mit der DB und gibt eine Rückmeldung
	protected boolean connectDatabase() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			this.cn = DriverManager.getConnection(this.ip, this.user, this.pw);
			return true;

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			return false;
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
	}

	protected void dbInitialize() {
		try {
			// Teste, ob es DB bereits gibt
			if (this.dbExist()) {
				// Tabellen wenn nötig erstellen
				this.createLoginTable();
				this.createLobbyTable();
				this.createHighScoresTable();
			} else {
				// DB mit Tabellen erstellen
				this.createSchema();
				this.createLoginTable();
				this.createLobbyTable();
				this.createHighScoresTable();

				// Grunddaten erstellen
				this.addLoginToDB("Etter", "UlhSMFpYST0=");
				this.addLoginToDB("Bilger", "UW1sc1oyVnk=");
				this.addLoginToDB("Rösti", "VXNPMmMzUnA=");
				this.addLoginToDB("Herren", "U0dWeWNtVnU=");
				this.addLobby("BaseLobby");
				this.addLobby("LobbySonntag");
				this.addPlayertoHighScore("Etter", 123);
				this.addPlayertoHighScore("Herren", 130);
			}

			// Lobbys aus DB in Datastore laden
			ArrayList<String> list = this.showLobbys();
			for (String s : list) {
				Lobby temp;
				try {
					temp = Lobby.createLobby(s);
					Datastore.getInstance().lobbys.put(s, temp);
				} catch (ExceptionBase e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Testet, ob es die DB bereits gibt, Idee von StackOverflow
	private boolean dbExist() {
		ResultSet rs = null;
		String dbName = "jassherb";
		try {
			this.cn = DriverManager.getConnection(this.ip, this.user, this.pw);
			rs = this.cn.getMetaData().getCatalogs();

			while (rs.next()) {

				String databaseName = rs.getString(1);
				if (databaseName.equals(dbName)) {
					return true;
				}
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	// Idee von Stackoverflow:
	// https://stackoverflow.com/questions/11909324/creating-a-database-table-if-it-does-not-exist-in-java-production-code-and-confi
	private void createSchema() throws SQLException {
		String sqlCreate = "CREATE SCHEMA jassherb";

		Statement stmt = this.cn.createStatement();
		stmt.execute(sqlCreate);
	}

	private void createLoginTable() throws SQLException {
		String sqlCreate = "CREATE TABLE IF NOT EXISTS jassherb.Login (" + "   Name VARCHAR(45) NOT NULL,"
				+ "   Password VARCHAR(45) NOT NULL," + "   PRIMARY KEY (Name))";

		Statement stmt = this.cn.createStatement();
		stmt.execute(sqlCreate);
	}

	private void createLobbyTable() throws SQLException {
		String sqlCreate = "CREATE TABLE IF NOT EXISTS jassherb.Lobby (" + "   LobbyName VARCHAR(45) NOT NULL,"
				+ "   PRIMARY KEY (LobbyName))";

		Statement stmt = this.cn.createStatement();
		stmt.execute(sqlCreate);
	}

	private void createHighScoresTable() throws SQLException {
		String sqlCreate = "CREATE TABLE IF NOT EXISTS jassherb.HighScore (" + "   PlayerName VARCHAR(45) NOT NULL,"
				+ "   Points INT NOT NULL," + "   PRIMARY KEY (PlayerName))";

		Statement stmt = this.cn.createStatement();
		stmt.execute(sqlCreate);
	}

	// Fügt einen Player der DB mit PreparedStatment hinzu gibt 1 zurück falls
	// erfolgreich
	public int addLoginToDB(String playername, String password) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int answer = 0;

		try {
			stmt = this.cn.prepareStatement("INSERT IGNORE INTO jassherb.Login (Name, Password) VALUES (?,?)");
			stmt.setString(1, playername);
			stmt.setString(2, password);
			answer = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return answer;
	}

	// Löscht einen Player der DB mit PreparedStatment, gibt 1 zurück wenn gelöscht
	public int deleteLoginFromDB(String playername) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int answer = 0;

		try {
			stmt = this.cn.prepareStatement("DELETE FROM jassherb.Login WHERE Name=?");
			stmt.setString(1, playername);
			answer = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return answer;
	}

	// Gibt Player PW zurück der DB mit PreparedStatment
	public String showLoginPasswordfromDB(String playername) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String password = null;

		try {
			stmt = this.cn.prepareStatement("SELECT * FROM jassherb.Login WHERE name=?");
			stmt.setString(1, playername);
			rs = stmt.executeQuery();
			rs.next();
			password = rs.getString(2);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return password;
	}

	// Überprüft, ob ein Spieler existiert
	public boolean checkLoginExist(String playername) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean b = false;

		try {
			stmt = this.cn.prepareStatement("SELECT COUNT(Name) FROM jassherb.Login WHERE Name = ?");
			stmt.setString(1, playername);
			rs = stmt.executeQuery();
			rs.next();
			int i = rs.getInt(1);
			if (i == 1)
				b = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return b;
	}

	// Fügt einen Player HighScore der DB mit PreparedStatment hinzu, gibt 1 zurück
	// (nur wenn höher überschreiben)
	// wenn hinzugefügt
	public int addPlayertoHighScore(String playername, int points) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int answer = 0;
		int oldScore = 0;
		if (points > showHighScoreOfPlayer(playername)) {
			try {
				//Fügt hinzu falls noch nicht vorhanden
				stmt = this.cn.prepareStatement("INSERT IGNORE INTO jassherb.HighScore (PlayerName, Points) VALUES (?,?)");
				stmt.setString(1, playername);
				stmt.setInt(2, points);
				answer = stmt.executeUpdate();
				//Jetzt gibt es den Player in der Liste => Scores sicher updaten
				updateHighScoreOfPlayer(playername,points);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null)
					try {
						if (!rs.isClosed())
							rs.close();
					} catch (Exception e) {
					}
				if (stmt != null)
					try {
						if (!stmt.isClosed())
							stmt.close();
					} catch (Exception e) {
					}
			}
		}
		return answer;
	}
	
	//Update des Tables HighScore for a Player
	private int updateHighScoreOfPlayer(String playername, int points) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int answer = 0;
		try {
			//Update des Scores, falls höher bestehender HighScore
			stmt = this.cn.prepareStatement("UPDATE jassherb.HighScore SET Points = ? WHERE PlayerName = ?");
			stmt.setInt(1, points);
			stmt.setString(2, playername);
			answer = stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return answer;
	}
		

	// Gibt den HighScore eines Spielers zurück
	public int showHighScoreOfPlayer(String playername) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = 0;

		try {
			stmt = this.cn.prepareStatement("SELECT Points FROM jassherb.HighScore WHERE PlayerName=?");
			stmt.setString(1, playername);
			rs = stmt.executeQuery();
			rs.next();
			i = rs.getInt(1);
		} catch (SQLException e) {
			return 0;
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return i;
	}

	// Löscht einen Player HighScore der DB mit PreparedStatment, gibt 1 zurück wenn
	// gelöscht
	public int deletePlayerfromHighScore(String playername) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int answer = 0;

		try {
			stmt = this.cn.prepareStatement("DELETE FROM jassherb.HighScore WHERE PlayerName=?");
			stmt.setString(1, playername);
			answer = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return answer;
	}

	// Gibt die Player und HighScores der DB mit PreparedStatment zurück
	public HashMap<String,Integer> showHighScores() {
		HashMap<String,Integer> tempList = new HashMap<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tempHS = null;
		try {
			stmt = this.cn.prepareStatement("SELECT * FROM jassherb.HighScore");
			rs = stmt.executeQuery();
			while (rs.next()) {
				tempList.put(rs.getString(1), rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return tempList;
	}

	// Fügt eine Lobby der DB mit PreparedStatment hinzu, gibt 1 zurück wenn
	// hinzugefügt
	public int addLobby(String lobbyName) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int answer = 0;

		try {
			stmt = this.cn.prepareStatement("INSERT IGNORE INTO jassherb.Lobby (LobbyName) VALUES (?)");
			stmt.setString(1, lobbyName);
			answer = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return answer;
	}

	// Löscht eine Lobby der DB mit PreparedStatment, gibt 1 zurück wenn gelöscht
	public int deleteLobby(String lobbyName) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int answer = 0;

		try {
			stmt = this.cn.prepareStatement("DELETE FROM jassherb.Lobby WHERE LobbyName=?");
			stmt.setString(1, lobbyName);
			answer = stmt.executeUpdate();
		} catch (SQLException e) {

		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return answer;
	}

	// Gibt die Lobbys der DB mit PreparedStatment zurück
	public ArrayList<String> showLobbys() {
		ArrayList<String> tempList = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tempLobbyName = null;

		try {
			stmt = this.cn.prepareStatement("SELECT * FROM jassherb.Lobby");
			rs = stmt.executeQuery();
			while (rs.next()) {

				tempLobbyName = rs.getString(1);
				tempList.add(tempLobbyName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					if (!rs.isClosed())
						rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					if (!stmt.isClosed())
						stmt.close();
				} catch (Exception e) {
				}
		}
		return tempList;

	}

	public String getIp() {
		return ip;
	}

	protected void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser() {
		return user;
	}

	protected void setUser(String user) {
		this.user = user;
	}

	public String getPw() {
		return pw;
	}

	protected void setPw(String pw) {
		this.pw = pw;
	}
}
