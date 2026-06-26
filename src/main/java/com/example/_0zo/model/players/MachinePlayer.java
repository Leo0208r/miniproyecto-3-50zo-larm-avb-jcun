package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.InvalidMoveException;

/**
 * Represents an AI-controlled player in the Cincuentazo game.
 *
 * <p>{@code MachinePlayer} uses a greedy strategy to select its card:
 * it scans its entire hand and picks the playable card that results in
 * the lowest possible table sum after being played. This conservative
 * approach minimizes the risk of pushing the table sum towards 50.</p>
 *
 * <p>If no card in the hand can be played without exceeding 50, the player
 * cannot make a move and an {@link InvalidMoveException} is thrown.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see Player
 * @see HumanPlayer
 */
public class MachinePlayer extends Player {

    /**
     * Constructs a {@code MachinePlayer} with the given display name.
     *
     * @param name the display name shown in the UI; must not be {@code null}
     */
    public MachinePlayer(String name) {
        super(name);
    }

    /**
     * Selects the optimal card to play using a greedy minimum-sum strategy.
     *
     * <p>The algorithm iterates over all cards in the hand. For each card,
     * it computes the resulting table sum if that card were played. The card
     * that produces the smallest result while staying at or below 50 is
     * chosen. This strategy keeps the sum as low as possible, giving other
     * players a harder time being forced over 50.</p>
     *
     * @param currentSum the current accumulated sum on the table
     * @return the {@link Card} that results in the lowest valid table sum
     * @throws InvalidMoveException if no card in the hand can be played
     *                              without exceeding the 50-point limit
     */
    @Override
    public Card selectCard(int currentSum) throws InvalidMoveException {
        Card best = null;
        int lowestResult = Integer.MAX_VALUE;
        for (Card card : hand) {
            int value = card.getValue(currentSum);
            int result = currentSum + value;
            if (result <= 50 && result < lowestResult) {
                lowestResult = result;
                best = card;
            }
        }
        if (best == null) {
            throw new InvalidMoveException(name + " has no valid card to play.");
        }
        return best;
    }
}