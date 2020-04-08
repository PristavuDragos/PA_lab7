package com.company;

public class Main
{

    public static void main(String[] args)
    {
	// write your code here
        Game testGame=new Game(52,5,100);
        Player[] players=new Player[5];
        for (int i=0;i<players.length-1;i++)
        {
            players[i]=new RandomPlayer("Player "+(i+1),testGame);
        }
        players[players.length-1]=new SmartPlayer("\"Smart Player\"",testGame);
        testGame.setPlayers(players);
    }
}
