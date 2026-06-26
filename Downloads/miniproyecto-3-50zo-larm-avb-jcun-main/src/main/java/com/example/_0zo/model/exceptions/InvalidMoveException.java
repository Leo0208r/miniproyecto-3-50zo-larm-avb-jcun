package com.example._0zo.model.exceptions;

/**
 * Thrown when a player attempts to play a card that would violate the game rules.
 *
 * <p>Common causes:</p>
 * <ul>
 *   <li>The card's value would cause the cumulative sum to exceed 50.</li>
 *   <li>The card is not in the player's hand.</li>
 *   <li>A human player's {@code selectCard()} is called directly (always invalid).</li>
 * </ul>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class InvalidMoveException extends Exception{

    /** Cumulative sum at the time of the invalid move. */
    private final int currentSum;

    /** Value the card would have contributed. */
    private final int cardValue;

    /**
     * Constructs an {@code InvalidMoveException} without sum/value context.
     *
     * @param message detail message describing why the move is invalid
     */
    public InvalidMoveException(String message){
        super(message);
        this.cardValue=-1;
        this.currentSum=-1;
    }
    /**
     * Constructs an {@code InvalidMoveException} with diagnostic sum and card-value data.
     *
     * @param message    detail message describing why the move is invalid
     * @param currentSum the cumulative sum at the time of the invalid attempt
     * @param cardValue  the value the card would have contributed
     */
    public InvalidMoveException(String message, int currentSum, int cardValue){
        super(message);
        this.cardValue=cardValue;
        this.currentSum=currentSum;
    }
    /**
     * Returns the cumulative sum at the time the invalid move was attempted.
     *
     * @return the table sum, or {@code -1} if not recorded
     */
    public int getCurrentSum(){return currentSum;}
    /**
     * Returns the value the played card would have added to the sum.
     *
     * @return the card value, or {@code -1} if not recorded
     */
    public int getCardValue(){return cardValue;}
}
