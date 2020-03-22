package herb.client.ressources;

import java.util.Map;

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
}
