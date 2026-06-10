package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.enums.PlayerStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected final String name;
    protected final List<Card> hand;
    protected PlayerStatus status;
    public Player(String name){
        this.name=name;
        this.hand= new ArrayList<>();
        this.status=PlayerStatus.ACTIVE;
    }



}
