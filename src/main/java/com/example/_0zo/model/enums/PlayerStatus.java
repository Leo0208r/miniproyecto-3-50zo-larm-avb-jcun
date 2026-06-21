package com.example._0zo.model.enums;

public enum PlayerStatus {
    ACTIVE("Active"),
    ELIMINATED("Eliminated");
    private final String symbol;
    PlayerStatus(String symbol){
        this.symbol=symbol;
    }
    public String getSymbol(){
        return symbol;
    }

}
