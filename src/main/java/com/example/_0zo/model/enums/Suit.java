package com.example._0zo.model.enums;

public enum Suit {
    HEARTS("♥") ,
    DIAMONDS("♦"),
    CLUBS("♣"),
    SPADES("♠");
    private final String symbol;
    Suit(String symbol){
        this.symbol=symbol;
    }
    public String getSymbol(){
        return symbol;
    }

}
