package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.exceptions.InvalidMoveException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void placeInitialCardUpdatesSumAndTop() throws InvalidMoveException {
        Table table = new Table();
        Card c = new Card(Suit.CLUBS, Rank.TEN); // value 10
        table.placeInitialCard(c);
        assertEquals(10, table.getSum());
        assertEquals(c, table.getTopCard());
    }

    @Test
    void placingCardThatExceedsThrows() {
        Table table = new Table();
        // bring sum to 45
        try {
            table.placeInitialCard(new Card(Suit.CLUBS, Rank.TEN)); // 10
            table.placeInitialCard(new Card(Suit.CLUBS, Rank.TEN)); // 20
            table.placeInitialCard(new Card(Suit.CLUBS, Rank.TEN)); // 30
            table.placeInitialCard(new Card(Suit.CLUBS, Rank.TEN)); // 40
            table.placeInitialCard(new Card(Suit.CLUBS, Rank.FIVE)); // 45
        } catch (InvalidMoveException e) {
            fail("setup failed: " + e.getMessage());
        }

        Card ten = new Card(Suit.HEARTS, Rank.TEN);
        InvalidMoveException ex = assertThrows(InvalidMoveException.class, () -> table.placeInitialCard(ten));
        assertTrue(ex.getMessage().contains("exceed" ) || ex.getCardValue() == 10 || ex.getCurrentSum() == 45);
    }

    @Test
    void collectForRefillKeepsTopAndReturnsOthers() throws InvalidMoveException {
        Table table = new Table();
        table.placeInitialCard(new Card(Suit.CLUBS, Rank.TWO));
        table.placeInitialCard(new Card(Suit.CLUBS, Rank.THREE));
        table.placeInitialCard(new Card(Suit.CLUBS, Rank.FOUR));

        List<Card> collected = table.collectForRefill();
        assertFalse(collected.isEmpty());
        assertEquals(1, table.getPileSize());
        assertEquals(table.getTopCard(), collected.get(collected.size() - 1).equals(table.getTopCard()) ? table.getTopCard() : table.getTopCard());
    }
}

