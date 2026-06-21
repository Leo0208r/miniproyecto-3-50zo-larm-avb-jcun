package com.example._0zo.controller;

import com.example._0zo.model.Card;
import com.example._0zo.model.players.Player;

public interface GameEventListener {
    void onTurnStarted(Player player);
    void onCardPlayed(Player player, Card card, int newSum);
    void onCardDrawn(Player player, Card card, int deckSize);
    void onPlayerEliminated(Player player);
    void onGameOver(Player winner, int totalRounds);
    void onInvalidMove(String message);



}
