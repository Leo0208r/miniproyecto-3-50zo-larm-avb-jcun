package com.example._0zo.model.game;

import com.example._0zo.model.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the state of the poker game during play.
 * 
 * This class tracks active players, manages turn progression, counts rounds,
 * and determines when the game is over. It maintains the player list and
 * cycles through active players for turn management.
 * 
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */
public class GameState {
    /** The list of all players in the game */
    private final List<Player> players;
    /** The index of the current turn within active players */
    private int currentTurnIndex;
    /** The total number of rounds completed in the game */
    private int totalRounds;
    /** Flag indicating if the game is currently running */
    private boolean gameRunning;
    /** The player who won the game, or null if still in progress */
    private Player winner;

    /**
     * Constructs a GameState with the specified players.
     *
     * @param players the list of players in the game
     */
    public GameState(List<Player> players) {
        this.players= new ArrayList<>(players);
        this.currentTurnIndex = 0;
        this.totalRounds= 0;
        this.gameRunning= true;
        this.winner= null;
    }

    /**
     * Gets a list of all currently active players.
     * 
     * Active players are those who have not been eliminated from the game.
     *
     * @return a list of active players
     */
    public List<Player> getActivePlayers() {
        return players.stream()
                .filter(Player::isActive)
                .toList();
    }

    /**
     * Checks if the game is over.
     * 
     * The game is over when only one or fewer players remain active.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return getActivePlayers().size() <= 1;
    }

    /**
     * Gets the current player whose turn it is.
     * 
     * The current player is determined by cycling through active players.
     * Returns null if there are no active players.
     *
     * @return the current player, or null if no active players
     */
    public Player getCurrentPlayer() {
        List<Player> active = getActivePlayers();
        if (active.isEmpty()) return null;
        return active.get(currentTurnIndex % active.size());
    }

    /**
     * Advances to the next player's turn and increments the round count.
     * 
     * If there are no active players, does nothing.
     */
    public void advanceTurn() {
        List<Player> active = getActivePlayers();
        if (!active.isEmpty()) {
            currentTurnIndex = (currentTurnIndex + 1) % active.size();
            totalRounds++;
        }
    }

    /**
     * Gets all players in the game, including eliminated ones.
     *
     * @return the list of all players
     */
    public List<Player> getAllPlayers() { 
        return players; 
    }

    /**
     * Gets the current turn index within active players.
     *
     * @return the current turn index
     */
    public int getCurrentTurnIndex() { 
        return currentTurnIndex; 
    }

    /**
     * Gets the total number of rounds completed.
     *
     * @return the total rounds
     */
    public int getTotalRounds() { 
        return totalRounds; 
    }

    /**
     * Checks if the game is currently running.
     *
     * @return true if the game is running, false otherwise
     */
    public boolean isGameRunning() { 
        return gameRunning; 
    }

    /**
     * Gets the winning player.
     *
     * @return the winner, or null if the game has not ended or there is no winner
     */
    public Player getWinner() { 
        return winner; 
    }

    /**
     * Sets whether the game is running.
     *
     * @param running the game running state
     */
    public void setGameRunning(boolean running) { 
        this.gameRunning = running; 
    }

    /**
     * Sets the winning player.
     *
     * @param winner the player who won, or null if there is no winner
     */
    public void setWinner(Player winner) { 
        this.winner = winner; 
    }
}
