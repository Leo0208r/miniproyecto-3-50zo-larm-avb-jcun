package com.example._0zo.model.game;

import com.example._0zo.model.Card;
import com.example._0zo.model.Deck;
import com.example._0zo.model.Table;
import com.example._0zo.model.exceptions.GameOverException;
import com.example._0zo.model.exceptions.InvalidMoveException;
import com.example._0zo.model.players.HumanPlayer;
import com.example._0zo.model.players.MachinePlayer;
import com.example._0zo.model.players.Player;

import java.util.List;

/**
 * Core game engine managing game state and move validation.
 * 
 * This class is responsible for managing the game logic, including setting up the game,
 * validating and executing player moves, managing eliminations, and detecting game over conditions.
 * It maintains the deck, table, and game state throughout the entire game.
 * 
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */
public class GameEngine {

    private final GameState state;
    private final Deck      deck;
    private final Table     table;

    /**
     * Constructs a GameEngine with the specified players.
     * 
     * Initializes the game with a new deck, table, and game state.
     *
     * @param players the list of players in the game
     * @throws IllegalArgumentException if the players list is null or empty
     */
    public GameEngine(List<Player> players) {
        if (players == null || players.isEmpty())
            throw new IllegalArgumentException("Player list must not be null or empty.");
        this.deck  = new Deck();
        this.table = new Table();
        this.state = new GameState(players);
    }

    /**
     * Sets up the initial game state by dealing cards to all players.
     * 
     * Each player receives 4 cards, and a fifth card is placed on the table
     * to start the game.
     *
     * @throws InvalidMoveException if the initial card exceeds the sum limit
     */
    public void setupGame() throws InvalidMoveException {
        for (Player player : state.getAllPlayers()) {
            for (int i = 0; i < 4; i++) {
                player.receiveCard(drawFromDeckWithRefill());
            }
        }
        Card initial = drawFromDeckWithRefill();
        table.placeInitialCard(initial);
    }

    /**
     * Executes a move by a human player.
     * 
     * Validates that the card is in the player's hand and is playable, then places
     * it on the table and removes it from the player's hand.
     *
     * @param player the human player making the move
     * @param card the card to play
     * @throws InvalidMoveException if the card is not in the player's hand or exceeds the limit
     * @throws GameOverException if the move causes the game to end
     */
    public void playHumanCard(HumanPlayer player, Card card)
            throws InvalidMoveException, GameOverException {
        if (!player.getHand().contains(card))
            throw new InvalidMoveException("La carta no está en tu mano.");
        if (!card.isPlayable(table.getSum()))
            throw new InvalidMoveException(
                    "Jugar " + card.getRank().getSymbol() + card.getSuit().getSymbol()
                    + " excedería 50. Suma actual: " + table.getSum(),
                    table.getSum(), card.getValue(table.getSum()));
        table.placeInitialCard(card);
        player.removeCard(card);
        checkGameOver();
    }

    /**
     * Executes a move by a computer-controlled player.
     * 
     * The machine player automatically selects a card using its strategy and plays it.
     *
     * @param machine the machine player making the move
     * @throws InvalidMoveException if the player has no valid cards to play
     * @throws GameOverException if the move causes the game to end
     */
    public void playMachineTurn(MachinePlayer machine)
            throws InvalidMoveException, GameOverException {
        Card chosen = machine.selectCard(table.getSum());
        table.placeInitialCard(chosen);
        machine.removeCard(chosen);
        checkGameOver();
    }

    /**
     * Eliminates the current player if they cannot play.
     * 
     * If the player cannot play any valid cards, they are eliminated and their
     * hand is added back to the deck. Checks if the game is over after elimination.
     *
     * @return true if the player was eliminated, false if they can still play
     * @throws GameOverException if only one player remains after elimination
     */
    public boolean eliminateCurrentPlayer() throws GameOverException {
        Player current = state.getCurrentPlayer();
        if (current == null || current.canPlay(table.getSum())) return false;

        current.eliminate();
        List<Card> surrendered = current.surrenderHand();
        deck.refill(surrendered);
        deck.shuffle();

        checkGameOver();
        return true;
    }

    /**
     * Checks if the game is over and throws an exception if it is.
     * 
     * The game is over when only one player remains active. The remaining player is declared the winner.
     *
     * @throws GameOverException if the game has ended
     */
    private void checkGameOver() throws GameOverException {
        if (state.isGameOver()) {
            List<Player> active = state.getActivePlayers();
            Player winner = active.isEmpty() ? null : active.get(0);
            state.setWinner(winner);
            state.setGameRunning(false);
            throw new GameOverException(
                    "¡Fin! Ganador: " + (winner != null ? winner.getName() : "nadie"),
                    winner, state.getTotalRounds());
        }
    }

    /**
     * Gives a card to the specified player from the deck.
     * 
     * This is typically used when a player needs to draw a card during gameplay.
     *
     * @param player the player to receive a card
     */
    public void drawCard(Player player) {
        player.receiveCard(drawFromDeckWithRefill());
    }

    /**
     * Advances the turn to the next player.
     * 
     * This method is called at the end of each player's turn to move to the next active player.
     */
    public void advanceTurn() {
        state.advanceTurn();
    }

    /**
     * Draws a card from the deck with automatic refilling.
     * 
     * If the deck is empty, it is refilled with cards from the table (except the top card)
     * and shuffled before drawing.
     *
     * @return the card drawn from the deck
     */
    private Card drawFromDeckWithRefill() {
        if (deck.isEmpty()) {
            List<Card> tableCards = table.collectForRefill();
            deck.refill(tableCards);
            deck.shuffle();
        }
        return deck.draw();
    }

    /**
     * Gets the current sum on the table.
     *
     * @return the current sum
     */
    public int getTableSum() { 
        return table.getSum(); 
    }

    /**
     * Gets the top card on the table.
     *
     * @return the top card
     */
    public Card getTopCard() { 
        return table.getTopCard(); 
    }

    /**
     * Gets the number of cards remaining in the deck.
     *
     * @return the deck size
     */
    public int getDeckSize() { 
        return deck.size(); 
    }

    /**
     * Gets the current game state.
     *
     * @return the game state
     */
    public GameState getState() { 
        return state; 
    }

    /**
     * Gets the current player whose turn it is.
     *
     * @return the current player
     */
    public Player getCurrentPlayer(){ 
        return state.getCurrentPlayer(); 
    }
}
