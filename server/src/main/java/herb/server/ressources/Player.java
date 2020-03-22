package herb.server.ressources;

import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import herb.server.Datastore;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;

//Etter
public class Player extends PlayerBase {
	
	public Player(String username, String authToken) {
		super(username, authToken);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(CardBase card) {
		// Aus Hand entfernen
		this.hand.play(card);
		// Karte dem Trick hinzufügen
		this.getRound().getTricks().getLast().addCardtoTrick(card);
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
		} else {
			return false;
		}
	}

	public void sortHand() {
		this.hand.sortCards();
	}

	@Override
	public CardBase[] getPlayableCards() {
		// TODO Gibt die Karten zurück, die gespielt werden dürfen
		return null;
	}
	
}
