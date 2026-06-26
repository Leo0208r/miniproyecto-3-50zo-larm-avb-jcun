package com.example._0zo.model.exceptions;

/**
 * Thrown to indicate that a player attempted an illegal move in Cincuentazo.
 *
 * <p>An invalid move typically occurs when the card a player tries to play
 * would push the table sum above 50, or when no card selection has been made
 * (e.g. for a {@link com.example._0zo.model.players.HumanPlayer} before the
 * UI provides input). Optionally carries the table's current sum and the
 * offending card's value for diagnostic purposes.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see GameOverException
 * @see EmptyDeckException
 */
public class InvalidMoveException extends Exception {

    /** The table sum at the time the invalid move was attempted, or {@code -1} if not set. */
    private final int currentSum;

    /** The value of the card that caused the violation, or {@code -1} if not set. */
    private final int cardValue;

    /**
     * Constructs an {@code InvalidMoveException} with a message only.
     *
     * <p>Use when the exact sum and card value are unavailable or irrelevant.</p>
     *
     * @param message a description of the illegal move
     */
    public InvalidMoveException(String message) {
        super(message);
        this.cardValue = -1;
        this.currentSum = -1;
    }

    /**
     * Constructs an {@code InvalidMoveException} with full diagnostic context.
     *
     * @param message    a description of the illegal move
     * @param currentSum the table sum at the time of the violation
     * @param cardValue  the value of the card that caused the violation
     */
    public InvalidMoveException(String message, int currentSum, int cardValue) {
        super(message);
        this.cardValue = cardValue;
        this.currentSum = currentSum;
    }

    /**
     * Returns the table sum at the time the invalid move was attempted.
     *
     * @return the current table sum, or {@code -1} if not available
     */
    public int getCurrentSum() { return currentSum; }

    /**
     * Returns the value of the card that caused the violation.
     *
     * @return the card value, or {@code -1} if not available
     */
    public int getCardValue() { return cardValue; }
}