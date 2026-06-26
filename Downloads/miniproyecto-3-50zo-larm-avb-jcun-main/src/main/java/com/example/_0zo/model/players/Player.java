package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.enums.PlayerStatus;
import com.example._0zo.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a participant in the Cincuentazo game.
 *
 * <p>Concrete subclasses ({@link HumanPlayer}, {@link MachinePlayer}) implement
 * the card-selection strategy. The base class handles hand management, elimination
 * logic, and common state shared by all player types.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */

public abstract class Player {
    /** The display name of this player. */
    protected final String name;
    /** The cards currently held in this player's hand. */
    protected final List<Card> hand;
    /** The current participation status of this player. */
    protected PlayerStatus status;

    /**
     * Constructs a player with the given display name.
     *
     * <p>The player starts with an empty hand and {@link PlayerStatus#ACTIVE} status.</p>
     *
     * @param name the player's display name; must not be {@code null}
     */

    public Player(String name){
        this.name=name;
        this.hand= new ArrayList<>();
        this.status=PlayerStatus.ACTIVE;
    }

    /**
     * Selects a card from this player's hand to play on the current turn.
     *
     * <p>Human players throw an exception here (selection happens via UI);
     * machine players use an internal strategy.</p>
     *
     * @param currentSum the current cumulative sum on the table
     * @return the chosen {@link Card}
     * @throws InvalidMoveException if no valid card can be selected
     */

    public abstract Card selectCard(int currentSum) throws InvalidMoveException;

    /**
     * Adds a card to this player's hand.
     *
     * @param card the card received; must not be {@code null}
     */

    public void receiveCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes a specific card from this player's hand.
     *
     * @param card the card to remove; must be present in the hand
     */

    public void removeCard(Card card) {
        hand.remove(card);
    }

    /**
     * Returns whether this player can legally play at least one card from their hand.
     *
     * @param currentSum the current cumulative sum on the table
     * @return {@code true} if at least one card in hand is playable
     */

    public boolean canPlay(int currentSum) {
        for (Card card : hand) {
            int value = card.getValue(currentSum);
            if (currentSum + value <= 50) {
                return true;
            }
        }
        return false;
    }
    /**
     * Marks this player as eliminated and sets their status to {@link PlayerStatus#ELIMINATED}.
     */
    public void eliminate() {
        this.status = PlayerStatus.ELIMINATED;
    }

    /**
     * Removes all cards from this player's hand and returns them.
     *
     * <p>Called when a player is eliminated so their cards can be returned to the deck.</p>
     *
     * @return the list of cards previously held by this player
     */

    public List<Card> surrenderHand() {
        List<Card> surrendered = new ArrayList<>(hand);
        hand.clear();
        return surrendered;
    }

    /**
     * Returns whether this player is still active (not eliminated).
     *
     * @return {@code true} if {@link PlayerStatus#ACTIVE}
     */

    public boolean isActive() {
        return status == PlayerStatus.ACTIVE;
    }

    /**
     * Returns the player's display name.
     *
     * @return the name string
     */

    public String getName()           { return name; }

    /**
     * Returns the player's current hand (live reference, not a copy).
     *
     * @return the list of cards in hand
     */

    public List<Card> getHand()       { return hand; }

    /**
     * Returns the player's current status.
     *
     * @return {@link PlayerStatus#ACTIVE} or {@link PlayerStatus#ELIMINATED}
     */

    public PlayerStatus getStatus()   { return status; }

    /**
     * Returns the number of cards currently in the player's hand.
     *
     * @return hand size (0 or more)
     */
    public int getHandSize()          { return hand.size(); }
}
