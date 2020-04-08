package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmartPlayer extends Player
{
    boolean noBlanksLeft=false;

    public SmartPlayer(String name, Game game)
    {
        super(name, game);
    }

    @Override
    public int drawMethod()
    {
        if(!noBlanksLeft) //Pentru a face prioritate pe piesele blank(care pot lua orice valoare)
        {
            for (int i = 0; i < game.gameBoard.tokens.size(); i++)
                if (game.gameBoard.tokens.get(i).isBlank) return i;
            noBlanksLeft=true; //cand nu se mai gasesc piese blank tine minte pentru a nu mai cauta pe viitor si
            // selecteaza o piesa din cea mai mare progresie de pe masa
            int[] temp=getMaxAP(game.gameBoard.tokens);
            int selectIndex=1;
            int previousValueOfAP=game.gameBoard.tokens.get(temp[2]).value;
            if (temp[0]==2 || temp[0]==3)return temp[2];
            for (int i=temp[2]+1;i<game.gameBoard.tokens.size();i++) //se alege un element din "mijlocul" progresiei
            {
                if (game.gameBoard.tokens.get(i).value-previousValueOfAP==temp[1])
                {
                    selectIndex++;
                    previousValueOfAP=game.gameBoard.tokens.get(i).value;
                }
                if (selectIndex==temp[0]/2) return i;
            }
        }
        else
        {   /*
            *Descriere strategie:
            *jucatorul face o lista cu tokenurile ramase pe masa si cele din mana lui care nu sunt blank
            *vede cea mai mare progresie de pe lista noua
            *cauta o piesa care se afla pe masa in progresia maxima gasita, si daca poate trage o piesa apropiata de o piesa proprie din aceeasi progresie
            *il all else fails, trage prima piesa de pe masa
            */
            ArrayList<Token>boardCopy =new ArrayList<>(game.gameBoard.tokens);

            Iterator<Token> it=myTokens.iterator();
            while(it.hasNext())
            {
                Token tk=it.next();
                if(!tk.isBlank) boardCopy.add(tk);
            }
            boardCopy.sort(Token::compareTo);//combina si sorteaza tokenurile
            int[] temp=getMaxAP(boardCopy);
            int previousValueOfAP=boardCopy.get(temp[2]).value;//cand se cauta PA se tine minte ultimul element
            int previousIndex=temp[2];
            int safeIndex=0;//un index (din copie) care reprezinta o piesa de pe masa si nu din mana
            if (!myTokens.contains(boardCopy.get(temp[2]))) safeIndex=temp[2];
            for (int i=temp[2]+1;i<boardCopy.size();i++) //se parcurge si se cauta progresia aritmetica
            {
                if (boardCopy.get(i).value-previousValueOfAP==temp[1]) //cand se gaseste un nou element
                {
                    if (!myTokens.contains(boardCopy.get(i)))safeIndex=i;
                    //piesa apropiata de o piesa proprie din aceeasi progresie
                    if (myTokens.contains(boardCopy.get(i)) && !myTokens.contains(boardCopy.get(previousIndex)))//
                    {
                        for (int j=0;j<game.gameBoard.tokens.size();j++)
                        {
                            if(game.gameBoard.tokens.get(j).value==boardCopy.get(previousIndex).value) return j;
                        }
                        //return previousIndex;
                    }
                    //piesa apropiata de o piesa proprie din aceeasi progresie
                    if (!myTokens.contains(boardCopy.get(i)) && myTokens.contains(boardCopy.get(previousIndex)))
                    {
                        for (int j=0;j<game.gameBoard.tokens.size();j++)
                        {
                            if(game.gameBoard.tokens.get(j).value==boardCopy.get(i).value) return j;
                        }
                        //return i;
                    }
                    previousValueOfAP=boardCopy.get(i).value;//update la ultimul element gasit
                    previousIndex=i;
                }
            }
            for (int j=0;j<game.gameBoard.tokens.size();j++)
            {
                if(game.gameBoard.tokens.get(j).value==boardCopy.get(safeIndex).value) return j;
            }
            //return safeIndex;
        }
        return 0;//if pepega return 0
    }

    public int[] getMaxAP(List<Token> l) // functie care determina cea mai mare progresie aritmetica si returneaza lungimea,ratia si
            //indexul primului element din progresie
    {
        int maxLength=0;
        int length;
        int ratio=0;
        int firstIndex=0;
        for (int i=0;i<l.size()-1;i++)
        {
            for (int j=i+1;j<l.size();j++)//for dublu care selecteaza toate progresiile aritmetice (primele doua elemente)
            {
                int currentRatio=l.get(j).value-l.get(i).value;
                length=2;
                int previousValue=l.get(j).value;
                for (int k=j+1;k<l.size();k++)//avand ratia de la primele 2 elemente, se parcurge restul sirului si se determina care elemente fac parte din PA
                {
                    if(l.get(k).value-previousValue==currentRatio)
                    {
                        length++;
                        previousValue=l.get(k).value;
                    }
                }
                if (length>maxLength)
                {
                    maxLength=length;
                    ratio=currentRatio;
                    firstIndex=i;
                }
            }
        }
        int aux[]={maxLength,ratio,firstIndex};
        return aux;
    }
}
