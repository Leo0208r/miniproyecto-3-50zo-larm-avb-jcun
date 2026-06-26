package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.InvalidMoveException;

/**
 * Represents a computer-controlled player in the Cincuentazo game.
 *
 * <p>The machine's strategy is conservative: it picks the playable card whose
 * contribution keeps the running sum as low as possible, thus minimising the
 * risk of future players being forced to exceed 50.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public class MachinePlayer extends Player{

    /**
     * Constructs a machine player with the given display name.
     *
     * @param name the player's display name (e.g., {@code "CPU 1"})
     */
    public MachinePlayer(String name){
        super(name);
    }

    /**
     * Selects the playable card that results in the lowest cumulative sum.
     *
     * <p>The algorithm iterates over the hand and picks the card whose value
     * produces the smallest {@code currentSum + value} that still does not
     * exceed 50. If no valid card exists an {@link InvalidMoveException} is thrown.</p>
     *
     * @param currentSum the current cumulative sum on the table
     * @return the best card to play according to the conservative strategy
     * @throws InvalidMoveException if the player has no card that keeps the sum ≤ 50
     */

    @Override
    public Card selectCard(int currentSum) throws InvalidMoveException {
        Card best=null;
        int lowestResult= Integer.MAX_VALUE;
        for (Card card:hand){
            int value= card.getValue(currentSum);
            int result=currentSum+value;
            if (result<=50 && result<lowestResult){
                lowestResult=result;
                best=card;
            }
        }
        if (best==null){
            throw new InvalidMoveException(name + " has no valid card to play.");
        }
        return best;
    }

}
