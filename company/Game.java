package com.company;

import java.util.*;

public class Game
{
    long gameTimer=0;
    long gameTimeCap=15;
    int nrOfTokens;
    int progressionLengthGoal;
    int tokenValueRange;
    int playerTurn;
    Board gameBoard;
    Player[] players;
    Thread[] threads;
    Timer timerDaemon;
    TimerTask displayTime;
    TimerTask timesUP;
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
        System.out.println(gameBoard.tokens);
        //start player threads
        timesUP=new TimerTask()
        {
            @Override
            public void run()
            {
                timerDaemon.cancel();
            }
        };
        displayTime=new TimerTask()
        {
            @Override
            public void run()
            {
                gameTimer+=5;
                if (gameTimer%60<10)System.out.println("Time elapsed: 0"+gameTimer/60+":0"+gameTimer%60);
                else
                System.out.println("Time elapsed: 0"+gameTimer/60+":"+gameTimer%60);
            }
        };
        timerDaemon=new Timer(true);
        timerDaemon.schedule(displayTime,0,5000);
        timerDaemon.schedule(timesUP,gameTimeCap*1000);
        for (int i=0;i<players.length;i++)
        {
            threads[i]=new Thread(players[i]);
            players[i].setGameOrder(i);
            threads[i].start();
        }

        while (gameBoard.tokens.size()>0 && gameTimer<gameTimeCap) {
            System.out.flush();
        }
        try
        {
            Thread.sleep(200);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if(gameBoard.tokens.size()>0) System.out.println("Time's up!");
            else System.out.println("GameFinished!");
        for(int i=0;i<players.length;i++)
        {
            System.out.println(players[i].name+" "+players[i].myTokens);
        }
        System.out.flush();
        System.exit(0);
    }
}
