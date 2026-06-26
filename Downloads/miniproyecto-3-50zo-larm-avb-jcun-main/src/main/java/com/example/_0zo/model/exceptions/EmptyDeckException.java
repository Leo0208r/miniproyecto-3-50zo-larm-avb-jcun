package com.example._0zo.model.exceptions;

/**
 * Thrown when an attempt is made to draw a card from an empty deck.
 *
 * <p>This is an unchecked exception ({@link RuntimeException}) because an
 * empty deck in normal gameplay indicates a programming error — the
 * {@link com.example._0zo.model.game.GameEngine} should always refill the deck
 * before drawing.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class EmptyDeckException extends RuntimeException{

    /** Deck size at the time of the exception. */
    private final int deckSize;

    /**
     * Constructs an {@code EmptyDeckException} with the given message and a deck size of 0.
     *
     * @param message detail message describing the error
     */
    public EmptyDeckException(String message){
        super(message);
        this.deckSize=0;
    }
    /**
     * Constructs an {@code EmptyDeckException} with the given message and recorded deck size.
     *
     * @param message  detail message describing the error
     * @param deckSize the size of the deck at the time of the exception (typically 0)
     */
    public EmptyDeckException(String message, int deckSize){
        super(message);
        this.deckSize=deckSize;
    }
    /**
     * Returns the size of the deck at the moment the exception was thrown.
     *
     * @return the deck size (expected to be 0 in normal circumstances)
     */

    public int getDeckSize(){
        return deckSize;
    }
}

