package com.example._0zo.model.exceptions;

import com.example._0zo.model.players.Player;

/**
 * Thrown to signal that the game has ended.
 *
 * <p>This exception is used as a control-flow mechanism to break out of the turn
 * loop in {@link com.example._0zo.model.game.TurnManager} when only one active
 * player remains. It carries the winner reference and the total round count so
 * the UI can display the results without additional state queries.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class GameOverException extends Exception{
    /** The winning player, or {@code null} if none. */

    private final Player winner;

    /** Total rounds played when the game ended. */
    private final int totalRounds;

    /**
     * Constructs a {@code GameOverException} with only a message (no winner recorded).
     *
     * @param message detail message describing the end condition
     */
    public GameOverException(String message){
        super(message);
        this.winner=null;
        this.totalRounds=-1;
    }
    /**
     * Constructs a {@code GameOverException} with full game-end context.
     *
     * @param message     detail message describing the end condition
     * @param winner      the player who won, or {@code null} if there is no winner
     * @param totalRounds the total number of rounds completed
     */
    public GameOverException(String message, Player winner, int totalRounds){
        super(message);
        this.winner=winner;
        this.totalRounds=totalRounds;
    }
    /**
     * Returns the winning player.
     *
     * @return the winner, or {@code null} if the game ended with no winner
     */
    public Player getWinner(){
        return winner;
    }

    /**
     * Returns the total number of rounds played when the game ended.
     *
     * @return the round count, or {@code -1} if not recorded
     */
    public int getTotalRounds(){
        return totalRounds;
    }

}
