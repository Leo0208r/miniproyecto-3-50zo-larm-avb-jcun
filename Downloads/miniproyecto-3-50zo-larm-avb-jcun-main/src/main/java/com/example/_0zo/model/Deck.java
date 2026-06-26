package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.exceptions.EmptyDeckException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a standard 52-card deck used during the game.
 *
 * <p>On construction the deck is built with all combinations of {@link Suit} and
 * {@link Rank} and immediately shuffled. The deck supports drawing from the top,
 * refilling with discarded cards, and querying its current size.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class Deck {
    private final LinkedList<Card> cards;

    /**
     * Constructs a full 52-card deck, builds it, and shuffles it.
     */

    public Deck(){
        cards= new LinkedList<>();
        build();
        shuffle();
    }
    private void build(){
        for (Suit suit: Suit.values()){
            for (Rank rank: Rank.values()){
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * Draws and removes the top card from the deck.
     *
     * @return the top {@link Card}
     * @throws EmptyDeckException if the deck is empty when this method is called
     */

    public Card draw(){
        if (cards.isEmpty()){
            throw new EmptyDeckException("Cannot draw from an empty deck");
        }
        return cards.removeFirst();
    }

    /**
     * Adds a collection of cards back into the deck (used for refilling from the table).
     *
     * @param tableCards the list of cards to add; must not be {@code null}
     */

    public void refill(List<Card> tableCards){
        cards.addAll(tableCards);
    }

    /**
     * Shuffles the deck in place using a random permutation.
     */

    public void shuffle() { Collections.shuffle(cards); }

    /**
     * Returns whether the deck contains no cards.
     *
     * @return {@code true} if the deck is empty
     */

    public boolean isEmpty() { return cards.isEmpty(); }

    /**
     * Returns the number of cards currently in the deck.
     *
     * @return the card count (0 to 52)
     */

    public int size() { return cards.size(); }
}
