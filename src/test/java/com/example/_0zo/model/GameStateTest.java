package com.example._0zo.model;

import com.example._0zo.model.game.GameState;
import com.example._0zo.model.players.HumanPlayer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void currentPlayerCyclesAndRoundsIncrease() {
        var p1 = new HumanPlayer("p1");
        var p2 = new HumanPlayer("p2");
        GameState state = new GameState(List.of(p1, p2));

        assertEquals(p1, state.getCurrentPlayer());
        state.advanceTurn();
        assertEquals(p2, state.getCurrentPlayer());
        assertEquals(1, state.getTotalRounds());
        state.advanceTurn();
        assertEquals(p1, state.getCurrentPlayer());
        assertEquals(2, state.getTotalRounds());
    }

    @Test
    void isGameOverWhenOneOrZeroActive() {
        var p1 = new HumanPlayer("p1");
        GameState state = new GameState(List.of(p1));
        assertTrue(state.isGameOver());
    }
}

