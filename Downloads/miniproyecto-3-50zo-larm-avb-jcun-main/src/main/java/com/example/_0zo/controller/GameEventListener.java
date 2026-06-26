package com.example._0zo.controller;

import com.example._0zo.model.Card;
import com.example._0zo.model.players.Player;

/**
 * Event-listener interface for game state changes in Cincuentazo.
 *
 * <p>Implementations (typically a JavaFX controller) receive callbacks on the
 * JavaFX Application Thread so they can update the UI directly without
 * extra {@code Platform.runLater()} wrappers at the call site.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public interface GameEventListener {

    /**
     * Called when a new turn starts for the given player.
     *
     * @param player the player whose turn is beginning
     */

    void onTurnStarted(Player player);

    /**
     * Called after a card has been successfully placed on the table.
     *
     * @param player the player who played the card
     * @param card   the card that was played
     * @param newSum the updated cumulative sum after the play
     */

    void onCardPlayed(Player player, Card card, int newSum);

    /**
     * Called after a player draws a replacement card from the deck.
     *
     * @param player   the player who drew the card
     * @param card     the card that was drawn
     * @param deckSize the number of cards remaining in the deck after the draw
     */

    void onCardDrawn(Player player, Card card, int deckSize);

    /**
     * Called when a player has been eliminated because they cannot play.
     *
     * @param player the player who was eliminated
     */

    void onPlayerEliminated(Player player);

    /**
     * Called when the game ends (one player remains or all are eliminated).
     *
     * @param winner      the winning player, or {@code null} if there is no winner
     * @param totalRounds the total number of rounds played
     */

    void onGameOver(Player winner, int totalRounds);

    /**
     * Called when the human player attempts an illegal move.
     *
     * @param message a human-readable description of why the move is invalid
     */

    void onInvalidMove(String message);



}
