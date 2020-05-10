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
    	case Clubs: suit = "Eichel"; break;
        case Diamonds: suit = "Schellen"; break;
        case Hearts: suit = "Rosen"; break;
        case Spades: suit = "Schilten"; break;
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
