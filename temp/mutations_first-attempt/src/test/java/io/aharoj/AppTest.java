package io.aharoj;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;


public class AppTest                     // Unit test for simple App.
{
    @Test                               // Rigorous Test :-)
    public void shouldAnswerWithTrue()
    {
        // assertEquals(App.typeOfTriangle(3, 4, 5), "Scalene");
        // assertEquals(App.typeOfTriangle(1, 1, 1), "Equilateral");
        // assertEquals(App.typeOfTriangle(-1, 4, 4), "Not a triangle");
        // assertEquals(App.typeOfTriangle(3, 3, 4), "Isosceles");
        
        //Scalene
        assertEquals(App.typeOfTriangle(3,4,5), "Scalene");
        
        //Equilateral
        assertEquals(App.typeOfTriangle(1,1,1), "Equilateral");
        
        //Isosceles
        assertEquals(App.typeOfTriangle(3,3,4), "Isosceles");
        assertEquals(App.typeOfTriangle(3,4,3), "Isosceles");
        assertEquals(App.typeOfTriangle(4,3,3), "Isosceles");
        
        //non-triangles
        assertEquals(App.typeOfTriangle(-1,4, 4), "Not a triangle");
        assertEquals(App.typeOfTriangle(4,-1, 4), "Not a triangle");
        assertEquals(App.typeOfTriangle(4, 4, -1), "Not a triangle");
        
        assertEquals(App.typeOfTriangle(0, 0, 0), "Not a triangle");
        
        assertEquals(App.typeOfTriangle(3, 1, 1), "Not a triangle");
        assertEquals(App.typeOfTriangle(1, 3, 1), "Not a triangle");
        assertEquals(App.typeOfTriangle(1, 1, 3), "Not a triangle");
    
    }


	public void testMainMethod() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
	
		String input = "3 4 5\n"; // set the input to be read by the Scanner object
		ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
		System.setIn(inputStream);
	
		App.main(new String[]{});
	
		assertEquals("Scalene\n", outputStream.toString()); // ensure that the correct triangle type is outputted
	}
}
