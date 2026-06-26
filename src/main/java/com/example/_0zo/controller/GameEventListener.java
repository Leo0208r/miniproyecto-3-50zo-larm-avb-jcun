package com.example._0zo.controller;

import com.example._0zo.model.Card;
import com.example._0zo.model.players.Player;

/**
 * Observer interface for game-state events in Cincuentazo.
 *
 * <p>Implementations of this interface (typically the {@code GameController})
 * register themselves with the {@code TurnManager} to receive notifications
 * whenever a significant game event occurs. This decouples the game engine
 * and turn management logic from the JavaFX view layer.</p>
 *
 * <p>All callback methods are invoked from background threads managed by
 * {@code TurnManager}. Implementations that update JavaFX nodes must
 * wrap their logic in {@link javafx.application.Platform#runLater(Runnable)}.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see com.example._0zo.controller.GameController
 */
public interface GameEventListener {

    /**
     * Called at the beginning of a player's turn, before any card is played.
     *
     * <p>Use this to highlight the active player in the UI or to enable
     * interactive controls for the human player.</p>
     *
     * @param player the {@link Player} whose turn is starting
     */
    void onTurnStarted(Player player);

    /**
     * Called immediately after a player successfully plays a card onto the table.
     *
     * @param player the {@link Player} who played the card
     * @param card   the {@link Card} that was played
     * @param newSum the updated accumulated sum on the table after the play
     */
    void onCardPlayed(Player player, Card card, int newSum);

    /**
     * Called after a player draws a card from the deck.
     *
     * <p>This happens when a player cannot make a valid play and must
     * draw instead (depending on game rules), or during the initial deal.</p>
     *
     * @param player   the {@link Player} who drew the card
     * @param card     the {@link Card} that was drawn
     * @param deckSize the number of cards remaining in the deck after the draw
     */
    void onCardDrawn(Player player, Card card, int deckSize);

    /**
     * Called when a player is eliminated from the current game.
     *
     * <p>A player is eliminated when they have no valid cards to play and
     * cannot escape the forced loss condition.</p>
     *
     * @param player the {@link Player} who was eliminated
     */
    void onPlayerEliminated(Player player);

    /**
     * Called when the game ends, either because only one player remains or
     * a terminal condition is reached.
     *
     * @param winner      the winning {@link Player}, or {@code null} if there is no winner
     * @param totalRounds the total number of rounds that were completed
     */
    void onGameOver(Player winner, int totalRounds);

    /**
     * Called when an attempted move is rejected as invalid.
     *
     * <p>Common causes include the human player clicking a card that would
     * push the sum above 50, or a machine player failing to find a valid card.</p>
     *
     * @param message a human-readable explanation of why the move was rejected
     */
    void onInvalidMove(String message);
}