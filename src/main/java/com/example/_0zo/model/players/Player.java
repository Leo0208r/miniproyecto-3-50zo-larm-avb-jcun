package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.enums.PlayerStatus;
import com.example._0zo.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a player in the Cincuentazo game.
 *
 * <p>Concrete subclasses ({@link HumanPlayer} and {@link MachinePlayer}) must
 * implement {@link #selectCard(int)} to define their card-selection strategy.
 * All common state — name, hand, and status — is managed here.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see HumanPlayer
 * @see MachinePlayer
 */
public abstract class Player {

    /** The display name of this player. */
    protected final String name;

    /** The cards currently held in this player's hand. */
    protected final List<Card> hand;

    /** The current activity status of this player (ACTIVE or ELIMINATED). */
    protected PlayerStatus status;

    /**
     * Constructs a player with the given name.
     * The player starts with an empty hand and {@link PlayerStatus#ACTIVE} status.
     *
     * @param name the display name of the player; must not be {@code null} or blank
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.status = PlayerStatus.ACTIVE;
    }

    /**
     * Selects a card from the player's hand to play on the table.
     *
     * <p>Implementations must choose a card whose value, added to
     * {@code currentSum}, does not exceed 50.</p>
     *
     * @param currentSum the current accumulated sum on the table
     * @return the {@link Card} chosen to be played
     * @throws InvalidMoveException if no valid card can be selected
     */
    public abstract Card selectCard(int currentSum) throws InvalidMoveException;

    /**
     * Adds a card to this player's hand.
     *
     * @param card the {@link Card} to add; must not be {@code null}
     */
    public void receiveCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes a specific card from this player's hand.
     *
     * <p>Has no effect if the card is not present in the hand.</p>
     *
     * @param card the {@link Card} to remove
     */
    public void removeCard(Card card) {
        hand.remove(card);
    }

    /**
     * Returns {@code true} if the player has at least one card that can
     * legally be played without exceeding the 50-point limit.
     *
     * @param currentSum the current accumulated sum on the table
     * @return {@code true} if a valid play exists; {@code false} otherwise
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
     * Marks this player as eliminated and sets their status to
     * {@link PlayerStatus#ELIMINATED}.
     */
    public void eliminate() {
        this.status = PlayerStatus.ELIMINATED;
    }

    /**
     * Removes and returns all cards from this player's hand.
     *
     * <p>Typically called when a player is eliminated so their cards
     * can be returned to the deck.</p>
     *
     * @return a new {@link List} containing all cards that were in the hand
     */
    public List<Card> surrenderHand() {
        List<Card> surrendered = new ArrayList<>(hand);
        hand.clear();
        return surrendered;
    }

    /**
     * Returns {@code true} if this player is still active in the game.
     *
     * @return {@code true} when status is {@link PlayerStatus#ACTIVE}; {@code false} otherwise
     */
    public boolean isActive() {
        return status == PlayerStatus.ACTIVE;
    }

    /**
     * Returns the display name of this player.
     *
     * @return the player's name
     */
    public String getName() { return name; }

    /**
     * Returns an unmodifiable view of the player's current hand.
     *
     * @return the list of {@link Card}s in hand
     */
    public List<Card> getHand() { return hand; }

    /**
     * Returns the current status of this player.
     *
     * @return the {@link PlayerStatus} (ACTIVE or ELIMINATED)
     */
    public PlayerStatus getStatus() { return status; }

    /**
     * Returns the number of cards currently in this player's hand.
     *
     * @return the hand size (0 or more)
     */
    public int getHandSize() { return hand.size(); }
}