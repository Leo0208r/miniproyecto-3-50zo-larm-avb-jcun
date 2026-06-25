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

public class GameEngine {

    private final GameState state;
    private final Deck      deck;
    private final Table     table;

    public GameEngine(List<Player> players) {
        if (players == null || players.isEmpty())
            throw new IllegalArgumentException("Player list must not be null or empty.");
        this.deck  = new Deck();
        this.table = new Table();
        this.state = new GameState(players);
    }

    public void setupGame() throws InvalidMoveException {
        for (Player player : state.getAllPlayers()) {
            for (int i = 0; i < 4; i++) {
                player.receiveCard(drawFromDeckWithRefill());
            }
        }
        Card initial = drawFromDeckWithRefill();
        table.placeInitialCard(initial);
    }

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

    public void playMachineTurn(MachinePlayer machine)
            throws InvalidMoveException, GameOverException {
        Card chosen = machine.selectCard(table.getSum());
        table.placeInitialCard(chosen);
        machine.removeCard(chosen);
        checkGameOver();
    }

    /**
     * Eliminates the current player if they cannot play.
     * Returns true if eliminated, false if they can still play.
     * Throws GameOverException if only one player remains after elimination.
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

    public void drawCard(Player player) {
        player.receiveCard(drawFromDeckWithRefill());
    }

    public void advanceTurn() {
        state.advanceTurn();
    }

    private Card drawFromDeckWithRefill() {
        if (deck.isEmpty()) {
            List<Card> tableCards = table.collectForRefill();
            deck.refill(tableCards);
            deck.shuffle();
        }
        return deck.draw();
    }

    // Getters
    public int     getTableSum()     { return table.getSum(); }
    public Card    getTopCard()      { return table.getTopCard(); }
    public int     getDeckSize()     { return deck.size(); }
    public GameState getState()      { return state; }
    public Player  getCurrentPlayer(){ return state.getCurrentPlayer(); }
}
