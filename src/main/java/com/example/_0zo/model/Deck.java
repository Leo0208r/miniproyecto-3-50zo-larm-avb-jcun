package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;
import com.example._0zo.model.exceptions.EmptyDeckException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private final LinkedList<Card> cards;
    public Deck(){
        cards= new LinkedList<>();
        build();
        shuffle();
    }
    private void build(){
        for (Suit suit: Suit.values()){
            for (Rank rank: Rank.values()){
                cards.add(new Card(suit, rank));
            }
        }
    }
    public Card draw(){
        if (cards.isEmpty()){
            throw new EmptyDeckException("Cannot draw from an empty deck");
        }
        return cards.removeFirst();
    }
    public void refill(List<Card> tableCards){
        cards.addAll(tableCards);
    }
    public void shuffle() { Collections.shuffle(cards); }
    public boolean isEmpty() { return cards.isEmpty(); }
    public int size() { return cards.size(); }
}
