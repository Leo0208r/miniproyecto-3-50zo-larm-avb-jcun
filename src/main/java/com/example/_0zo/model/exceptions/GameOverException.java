package com.example._0zo.model.exceptions;

import com.example._0zo.model.players.Player;

public class GameOverException extends Exception{
    private final Player winner;
    private final int totalRounds;
    public GameOverException(String message){
        super(message);
        this.winner=null;
        this.totalRounds=-1;
    }
    public GameOverException(String message, Player winner, int totalRounds){
        super(message);
        this.winner=winner;
        this.totalRounds=totalRounds;
    }
    public Player getWinner(){
        return winner;
    }
    public int getTotalRounds(){
        return totalRounds;
    }

}
