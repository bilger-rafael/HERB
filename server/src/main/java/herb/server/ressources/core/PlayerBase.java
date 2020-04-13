package herb.server.ressources.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class PlayerBase <Hand extends HandBase, Round extends RoundBase> {
	private final String username;
	private final String authToken;
	@JsonIgnoreProperties({ "currentStartingPlayer" })
	private Round round;
	private Hand hand;
	protected CardBase[] playableHandCards;

	public CardBase[] getPlayableHand() {
		return playableHandCards;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public Hand getHand() {
		return hand;
	}

	@JsonCreator
	public PlayerBase(String username, String authToken) {
		this.username = username;
		this.authToken = authToken;
	}

	@Override
	public boolean equals(Object o) {
		// self check
		if (this == o)
			return true;
		// null check
		if (o == null)
			return false;
		// type check and cast
		if (getClass() != o.getClass())
			return false;
		PlayerBase p = (PlayerBase) o;
		// field comparison
		return this.getUsername().equals(p.getUsername());
	}

	public abstract void play(CardBase card);

	public String getUsername() {
		return username;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Round getRound() {
		return round;
	}

}
