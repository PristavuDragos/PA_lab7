package com.company;

import java.util.*;

public class Game
{
    int nrOfTokens;
    int progressionLengthGoal;
    int tokenValueRange;
    int playerTurn;
    Board gameBoard;
    Player[] players;
    Thread[] threads;

    public Game(int nrOfTokens, int progressionLengthGoal, int tokenValueRange)
    {
        this.nrOfTokens = nrOfTokens;
        this.progressionLengthGoal = progressionLengthGoal;
        this.tokenValueRange = tokenValueRange;
    }

    public int getNrOfTokens()
    {
        return nrOfTokens;
    }

    public int getProgressionLengthGoal()
    {
        return progressionLengthGoal;
    }

    public int getTokenValueRange()
    {
        return tokenValueRange;
    }

    public Board getGameBoard()
    {
        return gameBoard;
    }

    public Player[] getPlayers()
    {
        return players;
    }

    public void setPlayers(Player[] players)
    {
        this.players = players;
        this.threads=new Thread[players.length];
        startGame();
    }

    public void nextPlayer()
    {
        playerTurn=(playerTurn+1)%players.length;
    }

    private void startGame()
    {
        playerTurn=0;
        System.out.println("The game has started:");
        //init game board
        List<Token> gameTokens=new ArrayList<>();
        List<Token> allTokens=new ArrayList<>();
        for (int i=1;i<=tokenValueRange;i++)
            allTokens.add(new Token(i));
        for (int i=0;i<tokenValueRange/10;i++)
            allTokens.add(new Token());
        while(gameTokens.size()<nrOfTokens)
        {
            Token tk;
            int tempNR=(int) (Math.random() * 10000)%(allTokens.size());
            gameTokens.add(allTokens.get(tempNR));
            allTokens.remove(tempNR);
        }
        gameBoard=new Board(gameTokens,this);
        //start player threads

        for (int i=0;i<players.length;i++)
        {
            threads[i]=new Thread(players[i]);
            players[i].setGameOrder(i);
            threads[i].start();
        }

        while (gameBoard.tokens.size()>0) { }
        try
        {
            Thread.sleep(100);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("GameFinished");
        System.exit(0);
    }
}
