package herb.server.ressources;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Trick extends TrickBase {
	public boolean played;

	public Trick(PlayerBase[] players, PlayerBase startingPlayer) {
		super(players, startingPlayer);
	}
	
	//Bilger
	public PlayerBase playTrick() {
		PlayerBase winner = null;

		for (int i = 0; i < this.getPlayers().length; i++) {
			//TODO wait for currentPlayer to play (Create Event in Player)
			
			((Player) this.currentPlayer.data).addPlayListener(() -> {
				this.played = true;
			});
			
			while(!played) {
				try {
				Thread.sleep(1000);
				//this.wait(); //is it possible to wait on Object Round? 
				} catch (InterruptedException e) {
				}
			}
			
			this.currentPlayer = this.currentPlayer.next;
		}

		winner = getWinner();

		return winner;
	}

	@Override
	public PlayerBase getWinner() {
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
	
	@Override
	public void addCardtoTrick(CardBase c) {
		this.playedCards.put(this.currentPlayer.data, c);
		setNextCurrentPlayer();
	}

	@Override
	public int getTrickPoints() {
		int trickPoints = 0;

		for (int i = 0; i < playedCards.size(); i++) {
			trickPoints += playedCards.get(this.getPlayers()[i]).getPoints();
		}

		return trickPoints;
	}

	@Override
	public Map<PlayerBase, CardBase> getPlayedCards() {
		return this.playedCards;
	}

	@Override
	public CardBase getPlayedCard(PlayerBase p) {
		return this.playedCards.get(p);
	}

	@Override
	protected void clearTrick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerBase getStaringPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerBase getNextPlayer(PlayerBase p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerBase getPrivousPlayer(PlayerBase p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PlayerBase setNextCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	@Override
	public PlayerBase getNextPlayer(PlayerBase p) {
		PlayerBase temp = p;

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

	@Override
	public PlayerBase getPrivousPlayer(PlayerBase p) {
		PlayerBase temp = p;

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
	protected PlayerBase setNextCurrentPlayer() {
		for (int i = 0; i < this.getPlayers().length; i++) {
			if (this.currentPlayer == this.getPlayers()[i]) {
				try {
					this.currentPlayer = this.getPlayers()[i + 1];
				} catch (ArrayIndexOutOfBoundsException e) {
					this.currentPlayer = this.getPlayers()[0];
				}
				break;
			}
		}
		return this.currentPlayer;
	}
*/
	

}
