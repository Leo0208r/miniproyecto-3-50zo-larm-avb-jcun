package com.example._0zo.model;

import com.example._0zo.model.enums.Rank;
import com.example._0zo.model.enums.Suit;

public class Card {
    private final Suit suit;
    private final Rank rank;
    public Card(Suit suit, Rank rank){
        this.suit=suit;
        this.rank=rank;
    }
    public int getValue(int currentSum){
        return switch (rank){
            case TWO -> 2;
            case THREE -> 3;
            case FOUR  -> 4;
            case FIVE  -> 5;
            case SIX   -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE  -> 0;
            case TEN   -> 10;
            case JACK, QUEEN, KING -> -10;
            case ACE   -> resolveAce(currentSum);
        };
    }
    private int resolveAce(int currentSum) {
        if (currentSum + 10 <= 50) {
            return 10;
        }
        return 1;
    }
    public boolean isPlayable(int currentSum) {
        return currentSum + getValue(currentSum) <= 50;
    }
    @Override
    public String toString() {
        return rank.name() + " of " + suit.name();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card other)) return false;
        return suit == other.suit && rank == other.rank;
    }

    @Override
    public int hashCode() {
        return 31 * suit.hashCode() + rank.hashCode();
    }
    public Suit getSuit(){return suit;}
    public Rank getRank(){return rank;}

}
