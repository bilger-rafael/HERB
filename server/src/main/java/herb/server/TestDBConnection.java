package herb.server;

import java.util.ArrayList;

public class TestDBConnection {

	public static void main(String[] args) {
		String playername = "Etter";
		String pw = "123";
		String lobbyName = "HERBJASSTEST";
		String points ="120";
		ArrayList<String> temp = new ArrayList<>();
		
		DataStore_Repository db = new DataStore_Repository();
		System.out.println("verbunden");
		
		db.addPlayerToDB(playername, pw);
		
		String s= db.showPlayerPasswordfromDB(playername);
		System.out.println(s);
		
		db.deletePlayerFromDB(playername);
		
		db.addLobby(lobbyName);
		
		
		db.addPlayertoHighScore(playername, points);
		
		
		db.deletePlayerfromHighScore(playername);
		
		temp = db.showLobbys();
		for(String string:temp) {
			System.out.println(string);
		}
		
		db.deleteLobby(lobbyName);
		
		
		db.stop();
		
		
	}

}
