package com.example._0zo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void initialDeckHas52Cards() {
        Deck deck = new Deck();
        assertEquals(52, deck.size());
    }

    @Test
    void drawReducesSize() {
        Deck deck = new Deck();
        int before = deck.size();
        Card c = deck.draw();
        assertNotNull(c);
        assertEquals(before - 1, deck.size());
    }
}

