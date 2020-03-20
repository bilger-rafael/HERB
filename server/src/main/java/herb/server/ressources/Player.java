package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.HandBase;
import herb.server.ressources.core.PlayerBase;

//Etter
public class Player extends PlayerBase{
	private HandBase hand;
	private Round currentRound;
	

	public Player(String username, String authToken) {
		super(username, authToken);
		this.hand = new Hand();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void play(CardBase card) {
		//Aus Hand entfernen
		this.hand.play(card);
		//Karte dem Trick hinzufügen
		this.currentRound.getCurrentTrick().addCardtoTrick(card);
	}
	
	public static PlayerBase login(String username, String password) {
		//TODO save player instance 
		return new Player(username, UUID.randomUUID().toString());
	}

	@Override
	public void addCardtoHand(CardBase card) {
		this.hand.addCard(card);
		
	}

	public void clearHand() {
		this.hand.clearCards();
	}
	
	public void setCurrentRound(Round r) {
		this.currentRound = r;
	}

	@Override
	public boolean PlayerNoCards() {
		if (this.hand.cardsEmpty()) {
		return true;
		}else {
		return false;
		}
	}

	@Override
	public void SortMyCards() {
		this.hand.sortCards();
		
	}

	@Override
	public PlayerBase getCurrentStartingPlayer() {
		return this.currentRound.getCurrentTrick().getStaringPlayer();
		
	}

	@Override
	public CardBase[] getPlayableCards() {
		// TODO Gibt die Karten zurück, die gespielt werden dürfen
		return null;
	}


}
