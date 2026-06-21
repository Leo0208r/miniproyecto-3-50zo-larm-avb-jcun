package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.InvalidMoveException;

public class HumanPlayer extends Player{
    public HumanPlayer (String name){
        super(name);
    }
    @Override
    public Card selectCard(int currentSum) throws InvalidMoveException {
        throw new InvalidMoveException("No valid card selected by human.");
    }
}
