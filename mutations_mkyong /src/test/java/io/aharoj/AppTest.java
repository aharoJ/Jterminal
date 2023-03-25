package io.aharoj;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AppTest 
{
    @Test
    public void testPositive() 
    {
        App obj = new App();
        assertEquals(true, obj.isPositive(10));

        //kill mutation #1
        assertEquals(true, obj.isPositive(0));
    }
}
