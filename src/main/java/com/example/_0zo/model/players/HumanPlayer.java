package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.InvalidMoveException;

/**
 * Represents a human-controlled player in the Cincuentazo game.
 *
 * <p>{@code HumanPlayer} does not select cards autonomously. Its
 * {@link #selectCard(int)} method always throws {@link InvalidMoveException}
 * because card selection is driven externally through the UI via
 * {@code TurnManager.submitHumanCard(Card)}. The controller intercepts
 * the player's click event and injects the chosen card directly into
 * the turn pipeline.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see Player
 * @see MachinePlayer
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a {@code HumanPlayer} with the given display name.
     *
     * @param name the display name shown in the UI; must not be {@code null}
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Not used for human players; card selection is handled by the UI.
     *
     * <p>This method always throws {@link InvalidMoveException} to signal that
     * autonomous card selection is not applicable here. The actual card is
     * submitted from the controller when the user clicks a card on screen.</p>
     *
     * @param currentSum the current accumulated sum on the table (unused)
     * @return never returns normally
     * @throws InvalidMoveException always, indicating no card was selected programmatically
     */
    @Override
    public Card selectCard(int currentSum) throws InvalidMoveException {
        throw new InvalidMoveException("No valid card selected by human.");
    }
}