package herb.client.ressources;

public enum Trump { Clubs, Diamonds, Hearts, Spades, TopsDown, BottomsUp;
    @Override
    public String toString() {
        String trump = "";
        switch (this) {
        case Clubs: trump = "clubs"; break;
        case Diamonds: trump = "diamonds"; break;
        case Hearts: trump = "hearts"; break;
        case Spades: trump = "spades"; break;
        case TopsDown trump = "Tops-Down"; break;
        case BottomsUp trump = "Bottoms-Up"; break;
        }
        return trump;
    }
}
