package herb.server.ressources;

import com.fasterxml.jackson.annotation.JsonIgnore;

import herb.server.ressources.core.TrickBase;

//Etter
public class Trick extends TrickBase<Player, Card> {

	public class PlayerNode {
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

		this.playedCards = new Card[4];
		this.nodeCurrentPlayer = buildCircularLinkedList();

		this.setCurrentPlayer(startingPlayer);
	}

	public PlayerNode buildCircularLinkedList() {
		PlayerNode pnStarting = null;
		PlayerNode pnPrevious = null;
		for (int i = 0; i < this.getPlayers().length; i++) {
			PlayerNode pn = new PlayerNode(this.getPlayers()[i]);

			if (pn.data.equals(this.getStartingPlayer())) {
				pnStarting = pn;
			}

			if (pnPrevious != null) {
				pnPrevious.next = pn;
			}

			pnPrevious = pn;
		}
		pnPrevious.next = pnStarting;
		return pnStarting;
	}

	// Bilger
	public Player playTrick() {
		Player winner = null;

		for (int i = 0; i < this.getPlayers().length; i++) {

			this.played = false;
			this.getCurrentPlayer().setPlayListener(() -> {
				this.played = true;
			});

			while (!played) {
				try {
					Thread.sleep(1000);
					// this.wait(); //is it possible to wait on Object Round?
				} catch (InterruptedException e) {
				}
			}

			this.determinNextPlayer();
		}

		winner = getWinner();

		return winner;
	}

	@JsonIgnore
	public Player getWinner() {
		PlayerNode pnStarting = buildCircularLinkedList();
		
		// set first player as winner
		this.winningPlayer = pnStarting.data;

		// compare card of winner with next player
		for (int i = 0; i < 3; i++) {
			pnStarting = pnStarting.next;
			if (this.getPlayedCard(this.winningPlayer).compareTo(this.getPlayedCard(pnStarting.data)) > 0) {
				this.winningPlayer = pnStarting.data;
			}
		}

		return this.winningPlayer;
		/*
		 * // Ort des Startspielers for (int i = 0; i < this.getPlayers().length; i++) {
		 * if (this.getStartingPlayer() == this.getPlayers()[i]) { // je nach dem
		 * welcher Spieler der Startspieler ist, wird in einer anderen // Reihenfolge
		 * verglichen switch (i) { case (0): if
		 * (getPlayedCards().get(this.getPlayers()[1])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[0])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[2])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[1])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[3])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[2])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } break; case (1): if
		 * (getPlayedCards().get(this.getPlayers()[2])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[1])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[3])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[2])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[0])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[3])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } break; case (2): if
		 * (getPlayedCards().get(this.getPlayers()[3])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[2])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[0])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[3])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[1])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[0])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } break; case (3): if
		 * (getPlayedCards().get(this.getPlayers()[0])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[3])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[1])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[0])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } if
		 * (getPlayedCards().get(this.getPlayers()[2])
		 * .compareTo(getPlayedCards().get(this.getPlayers()[1])) > 0) {
		 * this.winningPlayer = this.getPlayers()[0]; } break; } } } return
		 * this.winningPlayer;
		 */
	}

	public void addCardtoTrick(Card c) {
		for (int i = 0; i < this.getPlayers().length; i++) {
			if (this.getPlayers()[i].equals(this.getCurrentPlayer()))
				this.playedCards[i] = c;
		}
	}

	@JsonIgnore
	public int getTrickPoints() {
		int trickPoints = 0;

		for (int i = 0; i < getPlayedCards().length; i++) {
			trickPoints += getPlayedCards()[i].getPoints();
		}

		return trickPoints;
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

	public void determinNextPlayer() {
		this.nodeCurrentPlayer = this.nodeCurrentPlayer.next;
		this.setCurrentPlayer(nodeCurrentPlayer.data);
	}

	@Override
	public void setCurrentPlayer(Player p) {
		super.setCurrentPlayer(p);
		for (int i = 0; i < this.getPlayers().length; i++) {
			this.getPlayers()[i].updatePlayableHand(this);
		}
	}
}
