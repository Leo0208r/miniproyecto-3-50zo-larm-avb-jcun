package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.players.MachinePlayer;
import com.example._0zo.model.exceptions.InvalidMoveException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachinePlayerTest {

    @Test
    void selectCardChoosesLowestResultUnderLimit() throws InvalidMoveException {
        MachinePlayer m = new MachinePlayer("AI");
        Card c1 = new Card(Suit.CLUBS, Rank.TEN); // +10
        Card c2 = new Card(Suit.HEARTS, Rank.SEVEN); // +7
        m.receiveCard(c1);
        m.receiveCard(c2);

        Card chosen = m.selectCard(30); // results: 40 and 37 -> choose 37 (SEVEN)
        assertEquals(c2, chosen);
    }

    @Test
    void selectCardThrowsWhenNoValid() {
        MachinePlayer m = new MachinePlayer("AI");
        // only high cards that will overflow
        m.receiveCard(new Card(Suit.CLUBS, Rank.TEN));
        assertThrows(InvalidMoveException.class, () -> m.selectCard(45));
    }
}

