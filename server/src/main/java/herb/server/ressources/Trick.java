package herb.server.ressources;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Trick extends TrickBase <Player> {
	
	private class PlayerNode {
		public Player data;
		public PlayerNode next;

		public PlayerNode(Player players) {
			this.data = players;
		}
	}
	private PlayerNode nodeCurrentPlayer;
	public boolean played;

	public Trick(Player[] players, Player startingPlayer) {
		super(players, startingPlayer);
		
		PlayerNode pnPrevious = null;
		for (int i = 0; i < this.getPlayers().length; i++) {
			Player p = this.getPlayers()[i];
			PlayerNode pn = new PlayerNode(p);

			if (p.equals(this.getStartingPlayer())) {
				nodeCurrentPlayer = pn;
			}

			if (pnPrevious != null) {
				pnPrevious.next = pn;
			}

			pnPrevious = pn;
		}
	}
	
	//Bilger
	public Player playTrick() {
		Player winner = null;

		for (int i = 0; i < this.getPlayers().length; i++) {
			//TODO wait for currentPlayer to play (Create Event in Player)
			
			this.played = false;
			((Player) this.getCurrentPlayer()).addPlayListener(() -> {
				this.played = true;
			});
			
			while(!played) {
				try {
				Thread.sleep(1000);
				//this.wait(); //is it possible to wait on Object Round? 
				} catch (InterruptedException e) {
				}
			}
			
			this.setCurrentPlayer(this.determinNextPlayer());
		}

		winner = getWinner();

		return winner;
	}
	
	@JsonIgnore
	public Player getWinner() {
		this.winningPlayer = this.getStartingPlayer();
		// Ort des Startspielers
		for (int i = 0; i < this.getPlayers().length; i++) {
			if (this.getStartingPlayer() == this.getPlayers()[i]) {
				// je nach dem welcher Spieler der Startspieler ist, wird in einer anderen
				// Reihenfolge verglichen
				switch (i) {
				case (0):
					if (playedCards.get(this.getPlayers()[1]).compareTo(playedCards.get(this.getPlayers()[0])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[2]).compareTo(playedCards.get(this.getPlayers()[1])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[3]).compareTo(playedCards.get(this.getPlayers()[2])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					break;
				case (1):
					if (playedCards.get(this.getPlayers()[2]).compareTo(playedCards.get(this.getPlayers()[1])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[3]).compareTo(playedCards.get(this.getPlayers()[2])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[0]).compareTo(playedCards.get(this.getPlayers()[3])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					break;
				case (2):
					if (playedCards.get(this.getPlayers()[3]).compareTo(playedCards.get(this.getPlayers()[2])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[0]).compareTo(playedCards.get(this.getPlayers()[3])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[1]).compareTo(playedCards.get(this.getPlayers()[0])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					break;
				case (3):
					if (playedCards.get(this.getPlayers()[0]).compareTo(playedCards.get(this.getPlayers()[3])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[1]).compareTo(playedCards.get(this.getPlayers()[0])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					if (playedCards.get(this.getPlayers()[2]).compareTo(playedCards.get(this.getPlayers()[1])) > 0) {
						this.winningPlayer = this.getPlayers()[0];
					}
					break;
				}
			}
		}
		return this.winningPlayer;
	}

/*
	@Override
	protected void clearTrick() {
		this.playedCards.clear();
		this.currentPlayer = this.getStartingPlayer();

	}
*/
	
	public void addCardtoTrick(CardBase c) {
		this.playedCards.put(this.getCurrentPlayer(), c);
	}

	public int getTrickPoints() {
		int trickPoints = 0;

		for (int i = 0; i < playedCards.size(); i++) {
			trickPoints += playedCards.get(this.getPlayers()[i]).getPoints();
		}

		return trickPoints;
	}

	public Map<Player, CardBase> getPlayedCards() {
		return this.playedCards;
	}

	public CardBase getPlayedCard(Player p) {
		return this.playedCards.get(p);
	}
	
	public Player getNextPlayer(Player p) {
		Player temp = p;

		for (int i = 0; i < this.getPlayers().length; i++) {
			if (temp == this.getPlayers()[i]) {
				try {
					temp = this.getPlayers()[i + 1];
				} catch (ArrayIndexOutOfBoundsException e) {
					temp = this.getPlayers()[0];
				}
				break;
			}
		}
		return temp;
	}

	public Player getPrivousPlayer(Player p) {
		Player temp = p;

		for (int i = 0; i < this.getPlayers().length; i++) {
			if (temp == this.getPlayers()[i]) {
				try {
					temp = this.getPlayers()[i - 1];
				} catch (ArrayIndexOutOfBoundsException e) {
					temp = this.getPlayers()[3];
				}
				break;
			}
		}
		return temp;
	}

	@Override
	public Player determinNextPlayer() {
		this.nodeCurrentPlayer = this.nodeCurrentPlayer.next;
		return nodeCurrentPlayer.data;
	}



}
