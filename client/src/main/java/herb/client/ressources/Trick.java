package herb.client.ressources;

import java.util.Map;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.TrickBase;

public class Trick extends TrickBase<Player>{

	public Trick(Player[] players, Player startingPlayer) {
		super(players, startingPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Player getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getNextPlayer(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void clearTrick() {
		// TODO Auto-generated method stub
		
	}

	protected Player setNextCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPrivousPlayer(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCardtoTrick(CardBase c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getStaringPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTrickPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<Player, CardBase> getPlayedCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardBase getPlayedCard(Player p) {
		// TODO Auto-generated method stub
		return null;
	}
}
