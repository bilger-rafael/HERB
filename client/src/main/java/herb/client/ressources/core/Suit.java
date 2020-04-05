package herb.client.ressources.core;

public enum Suit { Clubs, Diamonds, Hearts, Spades;
    @Override
    public String toString() {
        String suit = "";
        switch (this) {
        case Clubs: suit = "Clubs"; break;
        case Diamonds: suit = "Diamonds"; break;
        case Hearts: suit = "Hearts"; break;
        case Spades: suit = "Spades"; break;
        }
        return suit;
    }
    
    public String toStringDe() {
    	String suit = "";
    	switch (this) {
    	case Clubs: suit = "Schilten"; break;
        case Diamonds: suit = "Schelten"; break;
        case Hearts: suit = "Rosen"; break;
        case Spades: suit = "Schellen"; break;
        }
        return suit;
        }

public String toStringFr() {
	String suit = "";
	switch (this) {
	case Clubs: suit = "Kreuz"; break;
    case Diamonds: suit = "Ecke"; break;
    case Hearts: suit = "Herz"; break;
    case Spades: suit = "Schaufel"; break;
    }
    return suit;
    }
}
