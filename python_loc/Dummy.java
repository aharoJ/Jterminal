public class Dummy 
{
    
    public boolean isPositive(int number)
    { // ignore

        boolean result = false; // 1
        if (number >= 0)  // 2
        { // 3
            result = true; //4
        } // 5 
        return result; // 6

        String s = "hello"; // 7
        if (s == "hello"){ // 8
            return "World (:"; // 9
        
        } else {  // 10
            return "not hello :("; // 11
        } // 12

    } //ignore

    // LOC -->  ?
    public String myFunction(String s) 
    { // ignore

        String string = "lala"; // 1
        if (string.equals(s)) // 2
        { // 3
            System.out.println("they match"); // 4
        } // 5
        return s; // 6

    } //ignore
    
}