package com.example._0zo.model;

import com.example._0zo.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents the table (pile) where cards are placed during the game.
 *
 * <p>The table maintains a stack of played cards and a running cumulative sum.
 * The sum must never exceed 50; any attempt to place a card that would do so
 * throws an {@link InvalidMoveException}. When the deck runs out, all cards
 * except the top one can be collected for refilling the deck.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class Table {
    private final Stack<Card> pile;
    private int sum;

    /**
     * Constructs an empty table with a cumulative sum of zero.
     */

    public Table(){
        pile=new Stack<>();
        this.sum=0;
    }

    /**
     * Places a card onto the table pile and updates the cumulative sum.
     *
     * @param card the card to place; must not be {@code null}
     * @throws InvalidMoveException if placing the card would cause the sum to exceed 50
     */

    public void placeInitialCard(Card card) throws InvalidMoveException {
        int value = card.getValue(sum);
        int newSum = sum + value;
        if (newSum > 50) {
            throw new InvalidMoveException("Playing " + card + " would exceed 50. Current sum: " + sum, sum, value);
        }
        pile.push(card);
        sum=newSum;
    }

    /**
     * Collects all cards from the pile except the top card, for deck refilling.
     *
     * <p>After this call the pile contains only the previously top card, and the
     * cumulative sum reflects only that card's value.</p>
     *
     * @return a list of cards removed from the pile (excluding the current top card)
     */

    public List<Card> collectForRefill(){
        Card lastCard=pile.peek();
        List<Card> collected= new ArrayList<>(pile);
        collected.remove(lastCard);
        pile.clear();
        pile.push(lastCard);
        return collected;
    }

    /**
     * Returns the current cumulative sum of all cards on the table.
     *
     * @return the cumulative sum (0–50)
     */

    public int getSum() { return sum; }

    /**
     * Returns the card currently on top of the pile without removing it.
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
     * @return the pile size
     */

    public int getPileSize() { return pile.size(); }

    /**
     * Returns whether the table pile is empty.
     *
     * @return {@code true} if no cards have been placed yet
     */

    public boolean isEmpty() { return pile.isEmpty(); }

}
