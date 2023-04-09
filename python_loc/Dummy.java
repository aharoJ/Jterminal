public class Dummy 
{
    // LOC -->  ?
    public boolean isPositive(int number)
    {
        boolean result = false;
        if (number >= 0) 
        {
            result = true;
        }
        return result;
    }

    // LOC -->  ?
    public String myFunction(String s) 
    {
        String string = "lala";
        if (string.equals(s)) 
        {
            System.out.println("they match");
        }
        return s;
    }
    
}