package com.example._0zo.model.enums;
/**
 * Enumerates the thirteen ranks of a standard playing card deck.
 *
 * <p>Each rank carries a display symbol used in the UI (e.g., {@code "J"} for Jack).
 * The numeric game value of each rank is computed dynamically by
 * {@link com.example._0zo.model.Card#getValue(int)} based on the current table sum.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */
public enum Rank {
    /** Rank with face value 2. */ TWO("2"),
    /** Rank with face value 3. */ THREE("3"),
    /** Rank with face value 4. */ FOUR("4"),
    /** Rank with face value 5. */ FIVE("5"),
    /** Rank with face value 6. */ SIX("6"),
    /** Rank with face value 7. */ SEVEN("7"),
    /** Rank with face value 8. */ EIGHT("8"),
    /** Neutral rank; contributes 0 to the sum. */ NINE("9"),
    /** Rank with face value 10. */ TEN("10"),
    /** Face card; subtracts 10 from the sum. */ JACK("J"),
    /** Face card; subtracts 10 from the sum. */ QUEEN("Q"),
    /** Face card; subtracts 10 from the sum. */ KING("K"),
    /** Flexible rank; contributes 10 or 1 depending on the current sum. */ ACE("A");

    private final String symbol;

    Rank(String symbol){
        this.symbol=symbol;
    }

    /**
     * Returns the single-character (or two-character for 10) display symbol for this rank.
     *
     * @return the symbol string (e.g., {@code "A"}, {@code "10"}, {@code "K"})
     */

    public String getSymbol(){
        return symbol;
    }
}
