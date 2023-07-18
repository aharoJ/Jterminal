package io.aharoj;
import java.util.*;
 
public class App 
{
    public static void main( String[] args )
    {
		//Get input of the three sides
		int side1;
		int side2;
		int side3;
		Scanner sc = new Scanner(System.in);

		side1 = sc.nextInt();
		side2 = sc.nextInt();
		side3 = sc.nextInt();
 
		String triangleType;
		triangleType = typeOfTriangle(side1, side2, side3);
		System.out.println(triangleType);
    }
 
    public static String typeOfTriangle(int side1, int side2, int side3)
    {	
		// A triangle has three sides larger than 0
		// for a triangle to be connected no side may be larger than the other two sides combined
		// An equilateral triangle has three sides of equal length
		// An isosceles triangle has two sides of equal length
		// A Scalene triangle has three sides of different length
 
		if (side1 <= 0 || side2 <= 0 || side3 <= 0){
			return "Not a triangle";
		}
		else if (side1 + side2 <= side3 || side3 + side1 <= side2 || side2 + side3 <= side1){
			return "Not a triangle";
		}
		else if (side1 == side2 && side2 == side3){
			return "Equilateral";
		}
		else if (side1 == side2 || side2 == side3 || side3 == side1){
			return "Isosceles";
		}
		else{
			return "Scalene";
		}
	}


}
