package com.example._0zo.model.game;

import com.example._0zo.model.players.Player;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final List<Player> players;
    private int currentTurnIndex;
    private int totalRounds;
    private boolean gameRunning;
    private Player winner;

    public GameState(List<Player> players) {
        this.players= new ArrayList<>(players);
        this.currentTurnIndex = 0;
        this.totalRounds= 0;
        this.gameRunning= true;
        this.winner= null;
    }
    public List<Player> getActivePlayers() {
        return players.stream()
                .filter(Player::isActive)
                .toList();
    }
    public boolean isGameOver() {
        return getActivePlayers().size() <= 1;
    }
    public Player getCurrentPlayer() {
        List<Player> active = getActivePlayers();
        if (active.isEmpty()) return null;
        return active.get(currentTurnIndex % active.size());
    }
    public void advanceTurn() {
        List<Player> active = getActivePlayers();
        if (!active.isEmpty()) {
            currentTurnIndex = (currentTurnIndex + 1) % active.size();
            totalRounds++;
        }
    }
    public List<Player> getAllPlayers()     { return players; }
    public int getCurrentTurnIndex()        { return currentTurnIndex; }
    public int getTotalRounds()             { return totalRounds; }
    public boolean isGameRunning()          { return gameRunning; }
    public Player getWinner()               { return winner; }

    public void setGameRunning(boolean running) { this.gameRunning = running; }
    public void setWinner(Player winner)        { this.winner = winner; }
}
