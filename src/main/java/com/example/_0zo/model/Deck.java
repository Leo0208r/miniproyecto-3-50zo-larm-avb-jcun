package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.exceptions.EmptyDeckException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a standard 52-card deck used in the Cincuentazo game.
 *
 * <p>On construction the deck is automatically built (one card per
 * {@link Suit}/{@link Rank} combination) and shuffled. Cards are drawn from
 * the front of the internal {@link LinkedList}. When the deck runs out, it can
 * be refilled with cards collected from the table via {@link #refill(List)}.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 */
public class Deck {

    /** Internal ordered collection of cards; the front is always the "top" of the deck. */
    private final LinkedList<Card> cards;

    /**
     * Constructs a new, fully built and shuffled 52-card deck.
     */
    public Deck() {
        cards = new LinkedList<>();
        build();
        shuffle();
    }

    /**
     * Populates the deck with all 52 cards (13 ranks × 4 suits).
     */
    private void build() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * Draws (removes and returns) the top card of the deck.
     *
     * @return the top {@link Card}
     * @throws EmptyDeckException if the deck contains no cards
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new EmptyDeckException("Cannot draw from an empty deck");
        }
        return cards.removeFirst();
    }

    /**
     * Refills the deck with a list of cards recovered from the table pile,
     * then shuffles the added cards into the deck.
     *
     * @param tableCards the cards to return to the deck; must not be {@code null}
     */
    public void refill(List<Card> tableCards) {
        cards.addAll(tableCards);
    }

    /**
     * Randomly shuffles all cards currently in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Returns {@code true} if the deck has no remaining cards.
     *
     * @return {@code true} when the deck is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the number of cards currently remaining in the deck.
     *
     * @return the card count (0 or more)
     */
    public int size() {
        return cards.size();
    }
}