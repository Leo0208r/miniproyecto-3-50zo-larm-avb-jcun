package com.example._0zo.model.exceptions;

/**
 * Unchecked exception thrown when an attempt is made to draw a card from an empty deck.
 *
 * <p>This is a {@link RuntimeException} because exhausting the deck is an
 * exceptional condition that the game engine handles through deck refilling.
 * The optional {@code deckSize} field can be used to verify the deck state
 * at the time of the error (it will normally be 0).</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see com.example._0zo.model.Deck
 */
public class EmptyDeckException extends RuntimeException {

    /**
     * The number of cards in the deck when the exception was thrown.
     * Expected to be 0 in most cases; defaults to 0 when not provided.
     */
    private final int deckSize;

    /**
     * Constructs an {@code EmptyDeckException} with a message and an implicit deck size of 0.
     *
     * @param message a description of the error condition
     */
    public EmptyDeckException(String message) {
        super(message);
        this.deckSize = 0;
    }

    /**
     * Constructs an {@code EmptyDeckException} with a message and an explicit deck size.
     *
     * @param message  a description of the error condition
     * @param deckSize the number of cards in the deck at the time of the error
     */
    public EmptyDeckException(String message, int deckSize) {
        super(message);
        this.deckSize = deckSize;
    }

    /**
     * Returns the number of cards in the deck when the exception was thrown.
     *
     * @return the deck size (typically 0)
     */
    public int getDeckSize() {
        return deckSize;
    }
}