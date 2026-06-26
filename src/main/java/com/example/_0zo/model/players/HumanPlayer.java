package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.InvalidMoveException;

/**
 * Represents a human-controlled player in the Cincuentazo game.
 *
 * <p>Card selection for a human player is driven entirely by the UI: the player
 * clicks a card, which is submitted to the {@link com.example._0zo.model.game.TurnManager}
 * via {@code submitHumanCard()}. Therefore {@link #selectCard(int)} always throws
 * {@link InvalidMoveException}; it should never be called directly for human turns.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class HumanPlayer extends Player{

    /**
     * Constructs a human player with the given display name.
     *
     * @param name the player's display name
     */
    public HumanPlayer (String name){
        super(name);
    }

    /**
     * Not used for human players — card selection is handled by the UI.
     *
     * @param currentSum the current cumulative sum on the table (unused)
     * @throws InvalidMoveException always, because humans select cards via the UI
     */

    @Override
    public Card selectCard(int currentSum) throws InvalidMoveException {
        throw new InvalidMoveException("No valid card selected by human.");
    }
}
