package herb.client.ressources.core;

public enum Rank { Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
    @Override
    public String toString() {
        String str = "ace";  // Assume we have an ace, then cover all other cases
        // Get ordinal value, which ranges from 0 to 4
        int ordinal = this.ordinal();
        if (ordinal <= 4) {
            str = Integer.toString(ordinal+6);
        } else if (ordinal == 5) { // Jack
            str = "jack";
        } else if (ordinal == 6) { // Queen
            str = "queen";
        } else if (ordinal == 7) { // King
            str = "king";
        }
        return str;
    }
    
    public String toStringDE() {
    	String rank = "";
    	switch (this) {
    	case Six: rank = "Sechs"; break;
        case Seven: rank = "Sieben"; break;
        case Eight: rank = "Acht"; break;
        case Nine: rank = "Neun"; break;
        case Ten: rank = "Zehn"; break;
        case Jack: rank = "Bube"; break;
        case Queen: rank = "Dame"; break;
        case King: rank = "Koenig"; break;
        case Ace: rank = "Ass"; break;
        }
        return rank;
    }
}
