package herb.server.ressources;

import java.util.Map;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.TrickBase;

//Etter
public class Trick extends TrickBase{
	private Map<PlayerBase,CardBase> playedCards;
	protected PlayerBase[] players;
	private PlayerBase currentplayer;
	private PlayerBase startingPlayer;
	private PlayerBase currentWinner;
	
	public Trick(PlayerBase[] players, PlayerBase startingPlayer) {
		super(players, startingPlayer);

	}

	public PlayerBase playTrick() {
		PlayerBase winner = null;
		// TODO Logik wer ist dran und weitergabe implementieren!
		
		winner = getWinner();
		
		
		return winner;
		
	}
	

	@Override
	public PlayerBase getWinner() {
		this.currentWinner=this.startingPlayer;
		//Ort des Startspielers
		for (int i =0; i<this.players.length;i++) {
			if(this.startingPlayer==this.players[i]) {
				//je nach dem welcher Spieler der Startspieler ist, wird in einer anderen Reihenfolge verglichen
				switch (i) {
				case(0):
					if(playedCards.get(this.players[1]).compareTo(playedCards.get(this.players[0]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[2]).compareTo(playedCards.get(this.players[1]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[3]).compareTo(playedCards.get(this.players[2]))>0) {
						this.currentWinner=this.players[0];}
					break;
				case(1):
					if(playedCards.get(this.players[2]).compareTo(playedCards.get(this.players[1]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[3]).compareTo(playedCards.get(this.players[2]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[0]).compareTo(playedCards.get(this.players[3]))>0) {
						this.currentWinner=this.players[0];}
					break;
				case(2):
					if(playedCards.get(this.players[3]).compareTo(playedCards.get(this.players[2]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[0]).compareTo(playedCards.get(this.players[3]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[1]).compareTo(playedCards.get(this.players[0]))>0) {
						this.currentWinner=this.players[0];}
					break;
				case(3):
					if(playedCards.get(this.players[0]).compareTo(playedCards.get(this.players[3]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[1]).compareTo(playedCards.get(this.players[0]))>0) {
						this.currentWinner=this.players[0];}
					if(playedCards.get(this.players[2]).compareTo(playedCards.get(this.players[1]))>0) {
						this.currentWinner=this.players[0];}
					break;
				}
			}
		}
		return this.currentWinner;
	}

	@Override
	public PlayerBase getNextPlayer() {
		PlayerBase temp = this.currentplayer;
	
		for (int i =0; i<this.players.length;i++) {
			if (temp == this.players[i] ) {
				try{ temp = this.players[i+1];
				}catch (ArrayIndexOutOfBoundsException e) {
					temp = this.players[0]; 
				}
				break;
			}
		}
		return temp;
	}
	
	@Override
	public PlayerBase getPrivousPlayer() {
		PlayerBase temp = this.currentplayer;
		
		for (int i =0; i<this.players.length;i++) {
			if (temp == this.players[i] ) {
				try{ temp = this.players[i-1];
				}catch (ArrayIndexOutOfBoundsException e) {
					temp = this.players[3]; 
				}
				break;
			}
		}
		return temp;
	}

	@Override
	public PlayerBase setNextCurrentPlayer() {
		for (int i =0; i<this.players.length;i++) {
			if (this.currentplayer == this.players[i] ) {
				try{ this.currentplayer = this.players[i+1];
				}catch (ArrayIndexOutOfBoundsException e) {
					this.currentplayer = this.players[0]; 
				}
				break;
			}
		}
		return this.currentplayer;
	}
	
	@Override
	public void addCardtoTrick(CardBase c) {
		this.playedCards.put(this.currentplayer, c);
		getNextPlayer();
	}

	@Override
	protected void clearTrick() {
		this.playedCards.clear();
		this.currentplayer=this.startingPlayer;
		
	}

	@Override
	protected int getTrickPoints() {
		int trickPoints=0;
		
		for(int i=0; i<playedCards.size();i++) {
			trickPoints+=playedCards.get(this.players[i]).getPoints();
		}
		
		return trickPoints;
	}

	@Override
	public PlayerBase getStaringPlayer() {
		return this.startingPlayer;
	}


}


