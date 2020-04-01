package com.company;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Board
{
    List<Token> tokens;
    Game game;
    public Board(List<Token> tokens,Game game)
    {
        this.game=game;
        this.tokens = tokens;
    }

    public List<Token> getTokens()
    {
        return tokens;
    }

    public void setTokens(List<Token> tokens)
    {
        this.tokens = tokens;
    }
    public synchronized  void drawPiece(Player p)
    {
        while(p.gameOrder!=game.playerTurn && tokens.size()>0)
        {
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace();}
        }
        if(tokens.size()>0)
        {
            int nr = (int) (Math.random() * 10000) % (tokens.size());
            Token tk = tokens.get(nr);
            tokens.remove(nr);
            game.nextPlayer();
            notifyAll();
            System.out.println(p.name+" draws.");
            p.myTokens.add(tk);
        }
    }
}
