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
	
	public Trick(PlayerBase[] players) {
		super();
		this.players = players;
		
		//TODO Startspieler ist der Gewinner der letzten Runde
		this.currentplayer= this.players[0];
		this.startingPlayer = this.players[0];
		//nicht Player0
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
		
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerBase getNextPlayer() {
		for (int i =0; i<this.players.length;i++) {
			if (this.currentplayer == this.players[i] ) {
				if(i==this.players.length-1) {
					getWinner();
				}
				this.currentplayer = this.players[i+1];
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
		this.currentplayer=this.players[0];
		
	}


}
