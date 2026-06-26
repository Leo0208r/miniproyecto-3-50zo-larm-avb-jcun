package com.example._0zo.model.exceptions;

import com.example._0zo.model.players.Player;

/**
 * Thrown to signal that the Cincuentazo game has ended.
 *
 * <p>This checked exception carries optional metadata about the game's
 * outcome: the winning {@link Player} and the total number of rounds
 * played. If the game ends without a clear winner (e.g. all players are
 * simultaneously eliminated), these fields default to {@code null} and
 * {@code -1} respectively.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see InvalidMoveException
 * @see EmptyDeckException
 */
public class GameOverException extends Exception {

    /** The player who won the game, or {@code null} if there is no winner. */
    private final Player winner;

    /** The total number of rounds completed when the game ended, or {@code -1} if unknown. */
    private final int totalRounds;

    /**
     * Constructs a {@code GameOverException} with a message but no winner or round count.
     *
     * <p>Use this constructor when the game ends in an unresolved state.</p>
     *
     * @param message a description of why the game ended
     */
    public GameOverException(String message) {
        super(message);
        this.winner = null;
        this.totalRounds = -1;
    }

    /**
     * Constructs a {@code GameOverException} with full outcome information.
     *
     * @param message     a description of why the game ended
     * @param winner      the {@link Player} who won; may be {@code null} if no winner
     * @param totalRounds the total number of rounds played; use {@code -1} if unknown
     */
    public GameOverException(String message, Player winner, int totalRounds) {
        super(message);
        this.winner = winner;
        this.totalRounds = totalRounds;
    }

    /**
     * Returns the player who won the game.
     *
     * @return the winning {@link Player}, or {@code null} if there is no winner
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Returns the total number of rounds that were completed.
     *
     * @return the round count, or {@code -1} if not set
     */
    public int getTotalRounds() {
        return totalRounds;
    }
}