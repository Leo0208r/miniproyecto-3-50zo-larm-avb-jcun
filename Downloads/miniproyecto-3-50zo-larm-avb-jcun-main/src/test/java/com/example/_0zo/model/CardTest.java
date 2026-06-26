package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testGetValue_NumberAndFaceCards() {
        Card two = new Card(Suit.CLUBS, Rank.TWO);
        Card ten = new Card(Suit.HEARTS, Rank.TEN);
        Card jack = new Card(Suit.SPADES, Rank.JACK);

        assertEquals(2, two.getValue(0));
        assertEquals(10, ten.getValue(0));
        assertEquals(-10, jack.getValue(0));
    }

    @Test
    void testAceResolvesToTenWhenFitsOtherwiseOne() {
        Card ace = new Card(Suit.DIAMONDS, Rank.ACE);

        // fits as 10
        assertEquals(10, ace.getValue(30));

        // does not fit as 10 -> becomes 1
        assertEquals(1, ace.getValue(45));
    }

    @Test
    void testIsPlayable() {
        Card ten = new Card(Suit.HEARTS, Rank.TEN);
        Card ace = new Card(Suit.SPADES, Rank.ACE);

        assertTrue(ten.isPlayable(30)); // 30+10 <=50
        assertFalse(ten.isPlayable(45)); // 45+10 > 50

        // ace as 10 fits
        assertTrue(ace.isPlayable(30));
        // ace as 1 also fits when 50 would be exceeded by 10
        assertTrue(ace.isPlayable(50 - 1));
    }
}

