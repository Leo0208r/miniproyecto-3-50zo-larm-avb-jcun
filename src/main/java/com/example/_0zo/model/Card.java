package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;

/**
 * Represents a single playing card in the Cincuentazo game.
 *
 * <p>Each card has a {@link Suit} and a {@link Rank}, and its numeric value
 * depends on the current table sum (relevant for the {@code ACE} rank, which
 * resolves to 10 or 1 to avoid exceeding the 50-point limit).</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 */
public class Card {

    /** The suit of this card (HEARTS, DIAMONDS, CLUBS, or SPADES). */
    private final Suit suit;

    /** The rank of this card (TWO through ACE). */
    private final Rank rank;

    /**
     * Constructs a {@code Card} with the given suit and rank.
     *
     * @param suit the {@link Suit} of the card; must not be {@code null}
     * @param rank the {@link Rank} of the card; must not be {@code null}
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the point value of this card given the current table sum.
     *
     * <ul>
     *   <li>Number cards (2–8, 10) contribute their face value.</li>
     *   <li>NINE contributes 0 (neutral).</li>
     *   <li>JACK, QUEEN, and KING subtract 10.</li>
     *   <li>ACE contributes 10 if {@code currentSum + 10 <= 50}, otherwise 1.</li>
     * </ul>
     *
     * @param currentSum the current accumulated sum on the table
     * @return the integer value this card adds (or subtracts) from the table sum
     */
    public int getValue(int currentSum) {
        return switch (rank) {
            case TWO   -> 2;
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

    /**
     * Determines the value of an ACE card based on the current table sum.
     *
     * <p>Returns 10 if adding 10 does not exceed 50; otherwise returns 1.</p>
     *
     * @param currentSum the current accumulated sum on the table
     * @return {@code 10} if {@code currentSum + 10 <= 50}, otherwise {@code 1}
     */
    private int resolveAce(int currentSum) {
        if (currentSum + 10 <= 50) {
            return 10;
        }
        return 1;
    }

    /**
     * Checks whether this card can be legally played given the current table sum.
     *
     * <p>A card is playable if playing it keeps the table sum at or below 50.</p>
     *
     * @param currentSum the current accumulated sum on the table
     * @return {@code true} if playing this card would not exceed 50; {@code false} otherwise
     */
    public boolean isPlayable(int currentSum) {
        return currentSum + getValue(currentSum) <= 50;
    }

    /**
     * Returns a human-readable description of this card in the format
     * {@code "RANK of SUIT"} (e.g. {@code "ACE of HEARTS"}).
     *
     * @return a {@code String} representation of this card
     */
    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }

    /**
     * Checks equality between this card and another object.
     *
     * <p>Two cards are equal if they share the same {@link Suit} and {@link Rank}.</p>
     *
     * @param o the object to compare with
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
     * @return an integer hash code derived from the card's suit and rank
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
    public Suit getSuit() { return suit; }

    /**
     * Returns the rank of this card.
     *
     * @return the {@link Rank} of this card
     */
    public Rank getRank() { return rank; }
}