package com.example._0zo.model.enums;

/**
 * Enumerates the four suits of a standard playing card deck.
 *
 * <p>Each suit carries a Unicode symbol used for display in the UI.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public enum Suit {
    /** Hearts suit (♥). */
    HEARTS("♥") ,
    /** Diamonds suit (♦). */
    DIAMONDS("♦"),
    /** Clubs suit (♣). */
    CLUBS("♣"),
    /** Spades suit (♠). */
    SPADES("♠");
    private final String symbol;
    Suit(String symbol){
        this.symbol=symbol;
    }

    /**
     * Returns the Unicode symbol for this suit.
     *
     * @return {@code "♥"}, {@code "♦"}, {@code "♣"}, or {@code "♠"}
     */
    public String getSymbol(){
        return symbol;
    }

}
