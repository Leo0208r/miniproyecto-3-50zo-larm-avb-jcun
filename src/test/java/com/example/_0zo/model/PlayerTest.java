package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.players.HumanPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void canPlayDetectsPlayableCard() {
        HumanPlayer p = new HumanPlayer("A");
        p.receiveCard(new Card(Suit.CLUBS, Rank.TWO));
        assertTrue(p.canPlay(49) == false); // 49+2 >50 -> false
        assertTrue(p.canPlay(48)); // 48+2 <=50 -> true
    }

    @Test
    void surrenderHandEmptiesAndReturnsCards() {
        HumanPlayer p = new HumanPlayer("B");
        Card a = new Card(Suit.DIAMONDS, Rank.THREE);
        p.receiveCard(a);
        assertEquals(1, p.getHandSize());
        var surrendered = p.surrenderHand();
        assertEquals(1, surrendered.size());
        assertEquals(0, p.getHandSize());
    }

    @Test
    void eliminateSetsStatus() {
        HumanPlayer p = new HumanPlayer("C");
        assertTrue(p.isActive());
        p.eliminate();
        assertFalse(p.isActive());
    }
}

