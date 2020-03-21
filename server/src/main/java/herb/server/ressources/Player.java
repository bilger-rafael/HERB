package herb.server.ressources;

import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import herb.server.Datastore;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;

//Etter
public class Player extends PlayerBase {
	
	public Player(String username, String password) {
		super(username, password);
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

	public static PlayerBase login(String username, String password)
			throws PlayerNotFoundException, PlayerLoginFailedException {
		Player player = Datastore.getInstance().players.get(username);

		if (player == null)
			throw new PlayerNotFoundException();

		String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

		if (!player.getPassword().equals(encodedPassword))
			throw new PlayerLoginFailedException();

		// return player without password
		return new Player(player.getUsername(), "");
	}

	public static PlayerBase register(String username, String password) throws PlayerAlreadyExistsException {
		if (Datastore.getInstance().players.containsKey(username))
			throw new PlayerAlreadyExistsException();

		// TODO check username and password length, if needed
		
		String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

		Player player = new Player(username, encodedPassword);

		Datastore.getInstance().players.put(username, player);

		// return player without password
		return new Player(player.getUsername(), "");
	}

}
