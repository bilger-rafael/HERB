package herb.server.ressources;

import java.util.Map;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Trick extends TrickBase {
	public boolean played;

	public Trick(PlayerBase[] players, PlayerBase startingPlayer) {
		super(players, startingPlayer);
	}

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

	@Override
	public void addCardtoTrick(CardBase c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clearTrick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTrickPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PlayerBase getStaringPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<PlayerBase, CardBase> getPlayedCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardBase getPlayedCard(PlayerBase p) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	@Override
	public PlayerBase getWinner() {
		this.winningPlayer = this.startingPlayer;
		// Ort des Startspielers
		for (int i = 0; i < this.players.length; i++) {
			if (this.startingPlayer == this.players[i]) {
				// je nach dem welcher Spieler der Startspieler ist, wird in einer anderen
				// Reihenfolge verglichen
				switch (i) {
				case (0):
					if (playedCards.get(this.players[1]).compareTo(playedCards.get(this.players[0])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[2]).compareTo(playedCards.get(this.players[1])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[3]).compareTo(playedCards.get(this.players[2])) > 0) {
						this.winningPlayer = this.players[0];
					}
					break;
				case (1):
					if (playedCards.get(this.players[2]).compareTo(playedCards.get(this.players[1])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[3]).compareTo(playedCards.get(this.players[2])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[0]).compareTo(playedCards.get(this.players[3])) > 0) {
						this.winningPlayer = this.players[0];
					}
					break;
				case (2):
					if (playedCards.get(this.players[3]).compareTo(playedCards.get(this.players[2])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[0]).compareTo(playedCards.get(this.players[3])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[1]).compareTo(playedCards.get(this.players[0])) > 0) {
						this.winningPlayer = this.players[0];
					}
					break;
				case (3):
					if (playedCards.get(this.players[0]).compareTo(playedCards.get(this.players[3])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[1]).compareTo(playedCards.get(this.players[0])) > 0) {
						this.winningPlayer = this.players[0];
					}
					if (playedCards.get(this.players[2]).compareTo(playedCards.get(this.players[1])) > 0) {
						this.winningPlayer = this.players[0];
					}
					break;
				}
			}
		}
		return this.winningPlayer;
	}

	@Override
	public PlayerBase getNextPlayer(PlayerBase p) {
		PlayerBase temp = p;

		for (int i = 0; i < this.players.length; i++) {
			if (temp == this.players[i]) {
				try {
					temp = this.players[i + 1];
				} catch (ArrayIndexOutOfBoundsException e) {
					temp = this.players[0];
				}
				break;
			}
		}
		return temp;
	}

	@Override
	public PlayerBase getPrivousPlayer(PlayerBase p) {
		PlayerBase temp = p;

		for (int i = 0; i < this.players.length; i++) {
			if (temp == this.players[i]) {
				try {
					temp = this.players[i - 1];
				} catch (ArrayIndexOutOfBoundsException e) {
					temp = this.players[3];
				}
				break;
			}
		}
		return temp;
	}

	@Override
	protected PlayerBase setNextCurrentPlayer() {
		for (int i = 0; i < this.players.length; i++) {
			if (this.currentPlayer == this.players[i]) {
				try {
					this.currentPlayer = this.players[i + 1];
				} catch (ArrayIndexOutOfBoundsException e) {
					this.currentPlayer = this.players[0];
				}
				break;
			}
		}
		return this.currentPlayer;
	}

	@Override
	protected void clearTrick() {
		this.playedCards.clear();
		this.currentPlayer = this.startingPlayer;

	}

	@Override
	public void addCardtoTrick(CardBase c) {
		this.playedCards.put(this.currentPlayer, c);
		setNextCurrentPlayer();
	}

	@Override
	public int getTrickPoints() {
		int trickPoints = 0;

		for (int i = 0; i < playedCards.size(); i++) {
			trickPoints += playedCards.get(this.players[i]).getPoints();
		}

		return trickPoints;
	}

	@Override
	public PlayerBase getStaringPlayer() {
		return this.startingPlayer;
	}

	@Override
	public Map<PlayerBase, CardBase> getPlayedCards() {
		return this.playedCards;
	}

	@Override
	public CardBase getPlayedCard(PlayerBase p) {
		return this.playedCards.get(p);
	}
	*/

}
