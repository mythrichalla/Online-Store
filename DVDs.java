// DVds Class -- Mythri Challa

/* The DVDs class is used to create objects of type DVD. It has private attributes, but these 
 * can be accessed via the getter methods. The attributes are initialized only via the constructor.
 */

public class DVDs implements Comparable<DVDs>
{
	// Declaration of the private variables for director, title, price, year, and the DVD code
	private String director;
	private String dvdTitle;
	private double dvdPrice;
	private double dvdDiscount = 0.8;
	private int year; 
	private int dvdCode; 
	
	// Constructor for the DVDs class, the "this" operator is used for director, year, and DVD code
	// to refer particularly to the object's attributes
	public DVDs (String director, String title, int year, double price, int dvdCode)
	{
		this.director = director; 
		dvdTitle = title; 
		dvdPrice = price;
		this.year = year;
		this.dvdCode = dvdCode; 
	}
	
	// The following are all get methods that are declared public so that other classes can use them
	public String getDirector()
	{
		return director; 
	}
	
	public String getDvdTitle()
	{
		return dvdTitle; 
	}
	
	public double getDvdPrice()
	{
		return (dvdPrice * dvdDiscount); 		// getDVDPrice has a discount of 0.8
	}
	
	public int getYear()
	{
		return year; 
	}
	
	public int getDvdCode()
	{
		return dvdCode; 
	}
	
	// The DVD's toString method returns a variable of type string that has the DVD's values appended to labels 
	public String toString()
	{
		String roundedPrice = String.format("%.2f", this.getDvdPrice());
		String result = "Title: " + getDvdTitle() + " | Director: " + getDirector() + " | Price: $ " + roundedPrice + 
				" | Year: " + getYear() + " | DvdCode: " + getDvdCode();
	    return result; 
	}
	
	public int compareTo(DVDs compDVD) 
	{
	       if(this.getDvdPrice() > compDVD.getDvdPrice())
	            return 1;
	        else if (this.getDvdPrice() < compDVD.getDvdPrice()) // Returns -1 if less
	            return -1;
	        else
	            return 0;
	}
}

