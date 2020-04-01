package com.company;

public class Token implements Comparable<Token>
{
    int value;
    boolean isBlank;

    public Token()
    {
        this.isBlank=true;
    }

    public Token(int value)
    {
        this.value = value;
        this.isBlank = false;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public int compareTo(Token o)
    {
        if(this.isBlank && o.isBlank) return 1;
        if(this.isBlank) return 1;
        if(o.isBlank) return -1;
        if(this.value>o.value) return 1;
        if(o.value>this.value) return -1;
        return 0;
    }

    @Override
    public String toString()
    {
        return "Token{" +
                "value=" + value +
                ", isBlank=" + isBlank +
                '}';
    }
}
