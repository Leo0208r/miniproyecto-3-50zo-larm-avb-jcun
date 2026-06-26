package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;

/**
 * Represents a single playing card with a suit and rank.
 *
 * <p>Each card knows how to compute its numeric value depending on the current
 * table sum. Aces use a flexible value (10 or 1) to avoid exceeding 50.
 * Face cards (J, Q, K) subtract 10 from the current sum; Nines are neutral (0).</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class  Card {
    private final Suit suit;
    private final Rank rank;

    /**
     * Constructs a card with the given suit and rank.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(Suit suit, Rank rank){
        this.suit=suit;
        this.rank=rank;
    }
    /**
     * Returns the numeric contribution of this card to the table sum.
     *
     * <p>Special rules:</p>
     * <ul>
     *   <li>NINE → 0 (neutral)</li>
     *   <li>JACK, QUEEN, KING → −10</li>
     *   <li>ACE → 10 if {@code currentSum + 10 ≤ 50}, otherwise 1</li>
     *   <li>All other ranks → their face value (2–10)</li>
     * </ul>
     *
     * @param currentSum the current cumulative sum on the table
     * @return the integer value this card adds (or subtracts) from the sum
     */
    public int getValue(int currentSum){
        return switch (rank){
            case TWO -> 2;
            case THREE -> 3;
            case FOUR  -> 4;
            case FIVE  -> 5;
            case SIX   -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE  -> 0;
            case TEN   -> 10;
            case JACK, QUEEN, KING -> -10;
            case ACE   -> resolveAce(currentSum);
        };
    }
    private int resolveAce(int currentSum) {
        if (currentSum + 10 <= 50) {
            return 10;
        }
        return 1;
    }
    /**
     * Determines whether this card can legally be played given the current sum.
     *
     * <p>A card is playable if {@code currentSum + getValue(currentSum) ≤ 50}.</p>
     *
     * @param currentSum the current cumulative sum on the table
     * @return {@code true} if playing this card would not exceed 50; {@code false} otherwise
     */
    public boolean isPlayable(int currentSum) {
        return currentSum + getValue(currentSum) <= 50;
    }

    /**
     * Returns a human-readable string representation of this card.
     *
     * @return a string in the form {@code "RANK of SUIT"} (e.g., {@code "ACE of HEARTS"})
     */
    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }

    /**
     * Checks equality based on suit and rank.
     *
     * @param o the object to compare
     * @return {@code true} if {@code o} is a {@code Card} with the same suit and rank
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card other)) return false;
        return suit == other.suit && rank == other.rank;
    }
    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code derived from suit and rank
     */
    @Override
    public int hashCode() {
        return 31 * suit.hashCode() + rank.hashCode();
    }

    /**
     * Returns the suit of this card.
     *
     * @return the {@link Suit} of this card
     */

    public Suit getSuit(){return suit;}

    /**
     * Returns the rank of this card.
     *
     * @return the {@link Rank} of this card
     */

    public Rank getRank(){return rank;}

}
