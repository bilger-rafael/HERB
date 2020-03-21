package herb.server.ressources;

import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

import herb.server.Datastore;
import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;

//Etter
public class Player extends PlayerBase {
	private final String encodedPassword;

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public Player(String username, String password) {
		super(username, UUID.randomUUID().toString().toUpperCase());
		this.encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
	}

	@Override
	public void play(CardBase card) {
		// Aus Hand entfernen
		this.hand.play(card);
		// Karte dem Trick hinzufügen
		this.getRound().getTricks().getLast().addCardtoTrick(card);
	}

	public static PlayerBase login(String username, String password)
			throws PlayerNotFoundException, PlayerLoginFailedException {
		Player player = Datastore.getInstance().players.get(username);

		if (player == null)
			throw new PlayerNotFoundException();

		String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		
		if (!player.getEncodedPassword().equals(encodedPassword))
			throw new PlayerLoginFailedException();
		
		// make sure only base, which does not contain password, is returned
		PlayerBase playerBase = (PlayerBase)player;
		return playerBase;
	}

	public static PlayerBase register(String username, String password) throws PlayerAlreadyExistsException {
		if (Datastore.getInstance().players.containsKey(username))
			throw new PlayerAlreadyExistsException();

		// TODO check username and password length, if needed

		Player player = new Player(username, password);

		Datastore.getInstance().players.put(username, player);

		// make sure only base, which does not contain password, is returned
		PlayerBase playerBase = (PlayerBase)player;
		return playerBase;
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
