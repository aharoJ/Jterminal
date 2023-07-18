package io.aharoj;

public class Dummy 
{
    public boolean isPositive(int number) 
    {
        boolean result = false;
        if (number >= 0) 
        {
            result = true;
        }
        return result;
        
    }

    public String myFunction(String s)
    {
        String string= "lala";
        if (string == s){
            System.out.println("they match");
        }

        return s;
    }
}