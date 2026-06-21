package com.example._0zo.model.exceptions;

public class EmptyDeckException extends RuntimeException{
    private final int deckSize;
    public EmptyDeckException(String message){
        super(message);
        this.deckSize=0;
    }
    public EmptyDeckException(String message, int deckSize){
        super(message);
        this.deckSize=deckSize;
    }
    public int getDeckSize(){
        return deckSize;
    }
}
