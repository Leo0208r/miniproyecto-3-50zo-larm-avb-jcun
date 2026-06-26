package com.example._0zo.model.enums;

/**
 * Enumerates the thirteen possible ranks of a standard playing card,
 * from TWO through ACE, each carrying a display symbol.
 *
 * <p>The order of constants follows the natural card order used when
 * building a deck (see {@link com.example._0zo.model.Deck}).</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see Suit
 */
public enum Rank {

    /** Numeric rank 2. */
    TWO("2"),

    /** Numeric rank 3. */
    THREE("3"),

    /** Numeric rank 4. */
    FOUR("4"),

    /** Numeric rank 5. */
    FIVE("5"),

    /** Numeric rank 6. */
    SIX("6"),

    /** Numeric rank 7. */
    SEVEN("7"),

    /** Numeric rank 8. */
    EIGHT("8"),

    /** Numeric rank 9 (neutral — contributes 0 to the table sum). */
    NINE("9"),

    /** Numeric rank 10. */
    TEN("10"),

    /** Face card Jack (subtracts 10 from the table sum). */
    JACK("J"),

    /** Face card Queen (subtracts 10 from the table sum). */
    QUEEN("Q"),

    /** Face card King (subtracts 10 from the table sum). */
    KING("K"),

    /** Ace — contributes 10 or 1 depending on the current table sum. */
    ACE("A");

    /** The single-character (or two-character for TEN) display symbol of this rank. */
    private final String symbol;

    /**
     * Constructs a {@code Rank} constant with the given display symbol.
     *
     * @param symbol the display string for this rank (e.g. {@code "A"}, {@code "10"})
     */
    Rank(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the display symbol for this rank (e.g. {@code "A"}, {@code "J"}, {@code "10"}).
     *
     * @return the rank's display symbol; never {@code null}
     */
    public String getSymbol() {
        return symbol;
    }
}