package com.company;

import java.util.Scanner;

public class ManualPlayer extends Player
{

    public ManualPlayer(String name, Game game)
    {
        super(name, game);
    }

    @Override
    public int drawMethod()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println(name+" ,pick the value that you want(0 for blank tokens):");
        System.out.println(game.gameBoard);
        int value = keyboard.nextInt();
        int selectedTokenIndex=-1;
        for (int i=0;i<game.gameBoard.tokens.size();i++)
        {
            if (value==0 && game.gameBoard.tokens.get(i).isBlank)
            {
                selectedTokenIndex=i;
                i=game.gameBoard.tokens.size();
            }
            if (value!=0 && game.gameBoard.tokens.get(i).value==value)
            {
                selectedTokenIndex=i;
                i=game.gameBoard.tokens.size();
            }
        }
        return selectedTokenIndex;
    }
}
