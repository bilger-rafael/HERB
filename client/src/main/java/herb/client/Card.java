package herb.client;

public class Card {
    public enum Suit { };
    public enum Rank { };
    
    private final Suit suit;
    private final Rank rank;
    
    public Card(Suit suit, Rank rank) {
    	this.suit = suit;
    	this.rank = rank;
    }
    
    public void getPoints() {}
}
