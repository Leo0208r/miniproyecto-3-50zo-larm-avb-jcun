package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.enums.PlayerStatus;
import com.example._0zo.model.exceptions.InvalidMoveException;

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
    public abstract Card selectCard(int currentSum) throws InvalidMoveException;
    public void receiveCard(Card card) {
        hand.add(card);
    }
    public void removeCard(Card card) {
        hand.remove(card);
    }
    public boolean canPlay(int currentSum) {
        for (Card card : hand) {
            int value = card.getValue(currentSum);
            if (currentSum + value <= 50) {
                return true;
            }
        }
        return false;
    }
    public void eliminate() {
        this.status = PlayerStatus.ELIMINATED;
    }
    public List<Card> surrenderHand() {
        List<Card> surrendered = new ArrayList<>(hand);
        hand.clear();
        return surrendered;
    }
    public boolean isActive() {
        return status == PlayerStatus.ACTIVE;
    }
    public String getName()           { return name; }
    public List<Card> getHand()       { return hand; }
    public PlayerStatus getStatus()   { return status; }
    public int getHandSize()          { return hand.size(); }
}
