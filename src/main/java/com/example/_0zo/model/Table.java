package com.example._0zo.model;

import com.example._0zo.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Table {
    private final Stack<Card> pile;
    private int sum;
    public Table(){
        pile=new Stack<>();
        this.sum=0;
    }
    public void placeInitialCard(Card card) throws InvalidMoveException {
        int value = card.getValue(sum);
        int newSum = sum + value;
        if (newSum > 50) {
            throw new InvalidMoveException("Playing " + card + " would exceed 50. Current sum: " + sum, sum, value);
        }
        pile.push(card);
        sum=newSum;
    }
    public List<Card> collectForRefill(){
        Card lastCard=pile.peek();
        List<Card> collected= new ArrayList<>(pile);
        collected.remove(lastCard);
        pile.clear();
        pile.push(lastCard);
        return collected;
    }
    public int getSum() { return sum; }
    public Card getTopCard() {
        if (pile.isEmpty()) return null;
        return pile.peek();
    }
    public int getPileSize() { return pile.size(); }
    public boolean isEmpty() { return pile.isEmpty(); }

}
