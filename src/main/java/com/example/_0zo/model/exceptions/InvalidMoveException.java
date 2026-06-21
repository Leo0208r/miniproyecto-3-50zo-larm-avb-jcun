package com.example._0zo.model.exceptions;

public class InvalidMoveException extends Exception{
    private final int currentSum;
    private final int cardValue;
    public InvalidMoveException(String message){
        super(message);
        this.cardValue=-1;
        this.currentSum=-1;
    }
    public InvalidMoveException(String message, int currentSum, int cardValue){
        super(message);
        this.cardValue=cardValue;
        this.currentSum=currentSum;
    }
    public int getCurrentSum(){return currentSum;}
    public int getCardValue(){return cardValue;}
}
