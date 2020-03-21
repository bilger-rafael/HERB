package herb.server.ressources;

import java.util.UUID;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.HandBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;

//Etter
public class Player extends PlayerBase{
	
	public Player(String username, String authToken) {
		super(username, authToken);
	}
	
	@Override
	public void play(CardBase card) {
		//Aus Hand entfernen
		this.hand.play(card);
		//Karte dem Trick hinzufügen
		this.getRound().getTricks().getLast().addCardtoTrick(card);
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

	@Override
	public boolean PlayerNoCards() {
		if (this.hand.cardsEmpty()) {
		return true;
		}else {
		return false;
		}
	}

	public void sortHand() {
		this.hand.sortCards();
	}

	@Override
	public PlayerBase getCurrentStartingPlayer() {
		return this.getRound().getTricks().getLast().getStaringPlayer();
		
	}

	@Override
	public CardBase[] getPlayableCards() {
		// TODO Gibt die Karten zurück, die gespielt werden dürfen
		return null;
	}

}
