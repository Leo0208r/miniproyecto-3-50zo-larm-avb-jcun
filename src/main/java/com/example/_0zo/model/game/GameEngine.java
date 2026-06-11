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
    private final Deck deck;
    private final Table table;
    public GameEngine(List<Player> players){
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Player list must not be null or empty.");
        }
        this.deck  = new Deck();
        this.table = new Table();
        this.state = new GameState(players);
    }
    public void setupGame() throws InvalidMoveException {
        for (Player player : state.getAllPlayers()) {
            for (int i = 0; i < 4; i++) {
                Card card = drawFromDeckWithRefill();
                player.receiveCard(card);
            }
        }
        Card initialCard = drawFromDeckWithRefill();
        table.placeInitialCard(initialCard);
    }
    public void playHumanCard(HumanPlayer player, Card card)
            throws InvalidMoveException, GameOverException {
        if (!player.getHand().contains(card)) {
            throw new InvalidMoveException(
                    "Card " + card + " is not in " + player.getName() + "'s hand.");
        }
        if (!card.isPlayable(table.getSum())) {
            throw new InvalidMoveException(
                    "Playing " + card + " would exceed 50. Current sum: " + table.getSum(),
                    table.getSum(),
                    card.getValue(table.getSum()));
        }
        table.placeInitialCard(card);
        player.removeCard(card);
    }
    public void playMachineTurn(MachinePlayer machine)
            throws InvalidMoveException, GameOverException {
        Card chosen = machine.selectCard(table.getSum());
        table.placeInitialCard(chosen);
        machine.removeCard(chosen);
    }
    public void drawCard(Player player) {
        Card drawn = drawFromDeckWithRefill();
        player.receiveCard(drawn);
    }
    public boolean eliminateCurrentPlayer() throws GameOverException {
        Player current = state.getCurrentPlayer();

        if (current == null || current.canPlay(table.getSum())) {
            return false;
        }
        current.eliminate();
        List<Card> surrendered = current.surrenderHand();
        deck.refill(surrendered);

        if (state.isGameOver()) {
            Player winner = state.getActivePlayers().isEmpty()
                    ? null
                    : state.getActivePlayers().get(0);
            state.setWinner(winner);
            state.setGameRunning(false);
            throw new GameOverException(
                    "Game over! Winner: " + (winner != null ? winner.getName() : "nobody"),
                    winner,
                    state.getTotalRounds());
        }

        return true;
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
    public int getTableSum() {
        return table.getSum();
    }
    public Card getTopCard() {
        return table.getTopCard();
    }
    public int getDeckSize() {
        return deck.size();
    }
    public GameState getState() {
        return state;
    }
    public Player getCurrentPlayer() {
        return state.getCurrentPlayer();
    }
}
