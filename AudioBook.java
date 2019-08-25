// AudioBook Class -- Mythri Challa

/* The AudioBook class is used to create objects of type AudioBook. It is a subclass that inherits from
 * the superclass Book, because it has all the same attributes except for the additional run time. 
 */

public class AudioBook extends Book
{
	// The only new attribute is run time, this is declared private but can be accessed via getTime
	private double runningTime; 
	private double abDiscount = 0.5;
	
	// Constructor for AudioBook
	public AudioBook(String author, String title, double price, int ISBN, double time)
	{
		super(author, title, price, ISBN); 	// Inheriting the constructor from the super class
		runningTime = time; 				// Initializing the run time variable
	}
	
	// getTime method is public so that it can be accessed by other classes
	public double getTime()
	{
		return runningTime; 
	}
	
	// The overloaded getPrice method returns the new price of the AudioBook by pulling the original price from 
	// the book and taking a 10% discount from it. 
	public double getBookPrice()
	{
		return ((super.getBookPrice()) * (abDiscount)); 
	}
	
	// The audio book's toString method prints the new discounted price as well as the run time. 
	public String toString()
	{
		//String roundedPrice = String.format("%.2f", this.getBookPrice());
		String result = (super.toString()) + " | Running Time: " + getTime(); 
	    return result; 
	}
	
}
