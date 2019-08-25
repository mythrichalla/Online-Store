// The Book class implements the Comparable interface to allow for the sorting 

public class Book implements Comparable<Book>
{
	// Declaration of the private variables for author, title, price, and the book's ISBN
	private String author;
	private String bookTitle;
	private double bookPrice; 
	private int ISBN; 
	private double bookDiscount = 0.9;
	
	// Constructor for the Book class, the "this" operator is used for author and ISBN to refer particuarly 
	// to the object's attributes
	public Book (String author, String title, double price, int ISBN)
	{
		this.author = author; 
		bookTitle = title; 
		bookPrice = price; 
		this.ISBN = ISBN; 
	}
	
	// The following are all get methods that are declared public so that other classes can use them
	public String getBookTitle()
	{
		return bookTitle; 
	}
	
	public double getBookPrice()
	{
		return (bookPrice * bookDiscount); 	// getBookPrice gets the bookDiscount added with it
	}
	
	public int getISBN()
	{
		return ISBN; 
	}
	
	public String getAuthor()
	{
		return author; 
	}
	
	// The book's toString method returns a variable of type string that has the book's values appended to labels 
	public String toString()
	{
		String roundedPrice = String.format("%.2f", this.getBookPrice());
		String result = "Title: " + getBookTitle() + " | Author: " + getAuthor() + " | Price: $ " + roundedPrice + " | ISBN: " + getISBN();
	    return result; 
	}

	// Compare To method is overridden and allows for sorting of the books by price
	public int compareTo(Book compBook) 
	{
	       if(this.getBookPrice() > compBook.getBookPrice())
	            return 1;
	        else if (this.getBookPrice() < compBook.getBookPrice()) // Returns -1 if less
	            return -1;
	        else
	            return 0;
	}

}
