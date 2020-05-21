package herb.server.ressources.core;

//Etter Vorlage SE Herbstsemester 19
public enum Suit { Clubs, Diamonds, Hearts, Spades;
    @Override
    public String toString() {
        String suit = "";
        switch (this) {
        case Clubs: suit = "clubs"; break;
        case Diamonds: suit = "diamonds"; break;
        case Hearts: suit = "hearts"; break;
        case Spades: suit = "spades"; break;
        }
        return suit;
    }
}
