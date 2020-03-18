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
	
	public Trick(PlayerBase[] players) {
		super();
		this.players = players;
		this.currentplayer= this.players[0];
	}

	@Override
	public PlayerBase getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerBase getNextPlayer() {
		for (int i =0; i<this.players.length;i++) {
			if (this.currentplayer == this.players[i] ) {
				this.currentplayer = this.players[i+1];
				break;
			}
		}
		return null;
	}
	
	@Override
	public void addCardtoTrick(CardBase c) {
		this.playedCards.put(this.currentplayer, c);
		getNextPlayer();
	}

	@Override
	protected void clearTrick() {
		// TODO Auto-generated method stub
		
	}


}
