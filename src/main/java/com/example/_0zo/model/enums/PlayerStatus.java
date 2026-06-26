package com.example._0zo.model.enums;

/**
 * Enumerates the possible activity states of a player during a game.
 *
 * <p>A player starts as {@link #ACTIVE} and transitions to
 * {@link #ELIMINATED} when they cannot make a legal move and are removed
 * from the current round.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see com.example._0zo.model.players.Player
 */
public enum PlayerStatus {

    /** The player is still participating in the current game. */
    ACTIVE("Active"),

    /** The player has been knocked out and can no longer take turns. */
    ELIMINATED("Eliminated");

    /** Human-readable label for this status. */
    private final String symbol;

    /**
     * Constructs a {@code PlayerStatus} with the given display label.
     *
     * @param symbol the readable label for this status
     */
    PlayerStatus(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the human-readable label for this status.
     *
     * @return the status label (e.g. {@code "Active"} or {@code "Eliminated"})
     */
    public String getSymbol() {
        return symbol;
    }
}