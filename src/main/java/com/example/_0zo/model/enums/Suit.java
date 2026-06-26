package com.example._0zo.model.enums;

/**
 * Enumerates the four suits of a standard playing card deck.
 *
 * <p>Each suit carries a Unicode symbol used for display purposes in both
 * the UI and log messages.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see Rank
 */
public enum Suit {

    /** The Hearts suit, displayed as ♥. */
    HEARTS("♥"),

    /** The Diamonds suit, displayed as ♦. */
    DIAMONDS("♦"),

    /** The Clubs suit, displayed as ♣. */
    CLUBS("♣"),

    /** The Spades suit, displayed as ♠. */
    SPADES("♠");

    /** The Unicode symbol representing this suit. */
    private final String symbol;

    /**
     * Constructs a {@code Suit} constant with the given Unicode symbol.
     *
     * @param symbol the Unicode character string for this suit
     */
    Suit(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the Unicode symbol for this suit (e.g. {@code "♥"}).
     *
     * @return the suit's display symbol; never {@code null}
     */
    public String getSymbol() {
        return symbol;
    }
}