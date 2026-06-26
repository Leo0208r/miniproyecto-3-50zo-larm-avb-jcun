package com.example._0zo.model;

import com.example._0zo.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents the central table (pile) in the Cincuentazo game.
 *
 * <p>The table maintains a stack of played cards and a running sum of their
 * values. The sum must never exceed 50; any attempt to place a card that would
 * cause an overflow throws an {@link InvalidMoveException}.</p>
 *
 * <p>When the deck is exhausted, all cards except the top one can be collected
 * via {@link #collectForRefill()} and returned to the deck.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 */
public class Table {

    /** Stack holding all cards placed on the table; the top element is the most recent card. */
    private final Stack<Card> pile;

    /** Running total of all card values placed on the table. */
    private int sum;

    /**
     * Constructs an empty table with a sum of zero.
     */
    public Table() {
        pile = new Stack<>();
        this.sum = 0;
    }

    /**
     * Places a card onto the table, updating the running sum.
     *
     * <p>This method is used both for the initial card of a round and for
     * subsequent plays. It validates that the new sum does not exceed 50.</p>
     *
     * @param card the {@link Card} to place; must not be {@code null}
     * @throws InvalidMoveException if placing the card would push the sum above 50
     */
    public void placeInitialCard(Card card) throws InvalidMoveException {
        int value = card.getValue(sum);
        int newSum = sum + value;
        if (newSum > 50) {
            throw new InvalidMoveException(
                    "Playing " + card + " would exceed 50. Current sum: " + sum, sum, value);
        }
        pile.push(card);
        sum = newSum;
    }

    /**
     * Removes all cards from the pile except the top card and returns them
     * so they can be shuffled back into the deck.
     *
     * <p>The top card is kept on the table to preserve the current sum state.</p>
     *
     * @return a {@link List} of all cards that were below the top card
     */
    public List<Card> collectForRefill() {
        Card lastCard = pile.peek();
        List<Card> collected = new ArrayList<>(pile);
        collected.remove(lastCard);
        pile.clear();
        pile.push(lastCard);
        return collected;
    }

    /**
     * Returns the current accumulated sum of all cards on the table.
     *
     * @return the table sum (0–50)
     */
    public int getSum() {
        return sum;
    }

    /**
     * Returns the card at the top of the pile without removing it.
     *
     * @return the top {@link Card}, or {@code null} if the pile is empty
     */
    public Card getTopCard() {
        if (pile.isEmpty()) return null;
        return pile.peek();
    }

    /**
     * Returns the number of cards currently in the pile.
     *
     * @return the pile size (0 or more)
     */
    public int getPileSize() {
        return pile.size();
    }

    /**
     * Returns {@code true} if the pile contains no cards.
     *
     * @return {@code true} when empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return pile.isEmpty();
    }
}