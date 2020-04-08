package com.company;

public class RandomPlayer extends Player
{

    public RandomPlayer(String name, Game game)
    {
        super(name, game);
    }

    @Override
    public int drawMethod()
    {
        int nr =(int) (Math.random() * 10000) % (game.gameBoard.tokens.size());
        return nr;
    }
}
