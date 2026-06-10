package com.example._0zo.model.players;

import com.example._0zo.model.Card;
import com.example._0zo.model.exceptions.InvalidMoveException;

public class MachinePlayer extends Player{
    public MachinePlayer(String name){
        super(name);
    }

    @Override
    public Card selectCard(int currentSum) throws InvalidMoveException {
        Card best=null;
        int lowestResult= Integer.MAX_VALUE;
        for (Card card:hand){
            int value= card.getValue(currentSum);
            int result=currentSum+value;
            if (result<=50 && result<lowestResult){
                lowestResult=result;
                best=card;
            }
        }
        if (best==null){
            throw new InvalidMoveException(name + " has no valid card to play.");
        }
        return best;
    }

}
