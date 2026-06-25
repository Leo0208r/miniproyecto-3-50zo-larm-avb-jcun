package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.exceptions.GameOverException;
import com.example._0zo.model.exceptions.InvalidMoveException;
import com.example._0zo.model.game.GameEngine;
import com.example._0zo.model.players.HumanPlayer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @Test
    void setupGameDealsAndPlacesInitialCard() throws InvalidMoveException {
        HumanPlayer p1 = new HumanPlayer("A");
        HumanPlayer p2 = new HumanPlayer("B");
        GameEngine engine = new GameEngine(List.of(p1, p2));

        engine.setupGame();

        assertEquals(4, p1.getHandSize());
        assertEquals(4, p2.getHandSize());
        assertNotNull(engine.getTopCard());
        assertEquals(52 - (2 * 4) - 1, engine.getDeckSize());
    }

    @Test
    void playHumanCardValidAndInvalid() throws InvalidMoveException, GameOverException {
        HumanPlayer p1 = new HumanPlayer("A");
        HumanPlayer p2 = new HumanPlayer("B");
        GameEngine engine = new GameEngine(List.of(p1, p2));

        // give p1 a series of cards to drive sum to 45
        p1.receiveCard(new Card(Suit.CLUBS, Rank.TEN)); // 10 -> 10
        p1.receiveCard(new Card(Suit.CLUBS, Rank.TEN)); // 10 -> 20
        p1.receiveCard(new Card(Suit.CLUBS, Rank.TEN)); // 10 -> 30
        p1.receiveCard(new Card(Suit.CLUBS, Rank.TEN)); // 10 -> 40
        p1.receiveCard(new Card(Suit.HEARTS, Rank.FIVE)); // 5  -> 45

        // play four tens and a five sequentially
        engine.playHumanCard(p1, p1.getHand().get(0));
        engine.playHumanCard(p1, p1.getHand().get(0));
        engine.playHumanCard(p1, p1.getHand().get(0));
        engine.playHumanCard(p1, p1.getHand().get(0));
        engine.playHumanCard(p1, p1.getHand().get(0));

        assertEquals(45, engine.getTableSum());

        // now playing a TEN should throw InvalidMoveException
        p1.receiveCard(new Card(Suit.DIAMONDS, Rank.TEN));
        Card ten = p1.getHand().stream().filter(c -> c.getRank() == Rank.TEN).findFirst().orElseThrow();
        InvalidMoveException ex = assertThrows(InvalidMoveException.class, () -> engine.playHumanCard(p1, ten));
        assertTrue(ex.getMessage().contains("excedería") || ex.getCurrentSum() == 45);
    }

    @Test
    void eliminateCurrentPlayerWhenCannotPlay() throws GameOverException {
        HumanPlayer p1 = new HumanPlayer("A");
        HumanPlayer p2 = new HumanPlayer("B");
        GameEngine engine = new GameEngine(List.of(p1, p2));

        // ensure current player has no cards
        assertEquals(0, engine.getState().getCurrentPlayer().getHandSize());

        boolean eliminated = engine.eliminateCurrentPlayer();
        assertTrue(eliminated);
        assertFalse(engine.getState().getAllPlayers().get(0).isActive());
    }
}

