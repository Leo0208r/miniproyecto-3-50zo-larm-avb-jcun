package com.example._0zo.model.enums;

/**
 * Represents the participation status of a player during the game.
 *
 * <ul>
 *   <li>{@link #ACTIVE} — the player is still in the game and can take turns.</li>
 *   <li>{@link #ELIMINATED} — the player could not play a valid card and has
 *       been removed; their hand was returned to the deck.</li>
 * </ul>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public enum PlayerStatus {
    /** The player is active and can take turns. */

    ACTIVE("Active"),

    /** The player has been eliminated and cannot play. */

    ELIMINATED("Eliminated");
    private final String symbol;
    PlayerStatus(String symbol){
        this.symbol=symbol;
    }

    /**
     * Returns the human-readable label for this status.
     *
     * @return {@code "Active"} or {@code "Eliminated"}
     */
    public String getSymbol(){
        return symbol;
    }

}
