package com.company;

import java.util.Set;
import java.util.TreeSet;

public class Player implements Runnable
{
    String name;
    Game game;
    int score;
    int gameOrder;
    Set<Token> myTokens;

    private Player(){ }

    public Player(String name, Game game)
    {
        this.name = name;
        this.game = game;
        this.score=0;
        this.myTokens=new TreeSet<>();
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setGameOrder(int gameOrder)
    {
        this.gameOrder = gameOrder;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void run()
    {
        while (game.gameBoard.tokens.size()>0)
        {
            game.gameBoard.drawPiece(this);
        }
        System.out.println(name+myTokens.toString());
    }
}
