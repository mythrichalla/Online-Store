/* The Validator class implements the Acceptable interface. It's purpose is to allow for 
 * all input validation to be "hidden" inside a class in order to only call methods in the
 * main program, allowing for a more succint main program.
 */

import java.util.*;

public class Validator implements Acceptable
{
	// Method that checks for non empty string 
	public boolean isNonEmptyString(String s)
	{
		if (s.trim().length() == 0)
			return false; 
		else
			return true;
	}
	
	// method that checks for positive or negative input
	public boolean isPositiveInput (double d)
	{
		if (d <= 0)
			return false; 
		else 
			return true;
	}
	
	// Method that checks for whether the input was A, B, or C
	public boolean isMainMenuChoice (char letterChoice)
	{
		if (letterChoice != 'A' || letterChoice != 'B' || letterChoice != 'C')
			return false;
		else
			return true;
	}
	
	// Method checks if the input is anything other than an integer
	public int isValidInteger()
	{
		Scanner input = new Scanner(System.in);
		int choice = 0;
		
		while (!(input.hasNextInt()))
		{
				System.out.println("Please enter an integer: ");
				input.next();
		}
		choice = input.nextInt();

		return choice;
	}
	
	// Method that checks if input is anything other than a Double
	public double isValidDouble()
	{
		Scanner input = new Scanner(System.in);
		double userInput = 0.0;
		while (!(input.hasNextDouble()))
		{
			System.out.print("Please enter an integer or double value: "); 
			input.nextLine();
		}
		userInput = input.nextDouble();
		return userInput;
	}
	
}
