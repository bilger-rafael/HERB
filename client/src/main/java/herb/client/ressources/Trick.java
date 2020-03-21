package herb.client.ressources;

import herb.client.ressources.core.CardBase;
import herb.client.ressources.core.PlayerBase;
import herb.client.ressources.core.TrickBase;

public class Trick extends TrickBase{

	public Trick(PlayerBase[] players, PlayerBase startingPlayer) {
		super(players, startingPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlayerBase getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerBase getNextPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void clearTrick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerBase setNextCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerBase getPrivousPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCardtoTrick(CardBase c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerBase getStaringPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTrickPoints() {
		// TODO Auto-generated method stub
		return 0;
	}
}
