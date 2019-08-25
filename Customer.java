// Homework 2 -- Mythri Challa
/* 
 * This program is a simulation of an online store that contains inventory of books and dvds. 
 * The program displays a main menu of 8 options for the user, and continually takes in user input for the 
 * menu choice until the user chooses to end the program. Each menu choice implements different methods in order
 * to display inventory items for books and DVDs, and also adds the user's choices to a "cart", and finally, calculates
 * the total amount of money in the cart. All methods in this program other than main are declared private, as there is 
 * no particular need for the methods to be public (no other classes need to access them).
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Customer
{
	static Scanner input = new Scanner (System.in); // Reading input
	static Validator validCheck = new Validator();
	public void runCustomer () 
	{
		// Declaration of arrays for the items in a cart and the corresponding prices
		ArrayList<Book> bookCatalog = new ArrayList<>();
		ArrayList<DVDs> dvdCatalog = new ArrayList<>();
		ArrayList<Book> bookItems = new ArrayList<Book>();
		ArrayList<DVDs> dvdItems = new ArrayList<DVDs>();
		
        for(int i = 0; i < Manager.BooksList.size(); i++)
        {
            bookCatalog.add(Manager.BooksList.get(i));
        }

        for(int i = 0; i < Manager.DVDsList.size(); i++)
        {
            dvdCatalog.add(Manager.DVDsList.get(i));
        }
        int menuChoice = 0; 
		
		do
		{
			displayMenu();
			menuChoice = validCheck.isValidInteger();
			
			while (menuChoice < 1 || menuChoice > 9)
			{
				System.out.println("This is invalid. Please enter an integer between 1 to 8.");
				menuChoice = validCheck.isValidInteger();	// Taking in input again if invalid
			}

			// Begin switch case
			switch (menuChoice)
			{
				case 1: 
					browseBooks(bookCatalog);
					break;
					
				case 2: 
					browseDVDs(dvdCatalog); 
					break;
					
				case 3:
					addBook(bookCatalog, bookItems); 
					break;
					
				case 4: 
					addDVD(dvdCatalog, dvdItems);
					break;
					
				case 5: 
					removeBook(bookItems);
					//browseBooks(bookCatalog);
					break;
					
				case 6: 
					removeDVD(dvdItems);
					//browseDVDs(dvdCatalog); 
					break;
					
				case 7:
					double total = getTotal(bookItems, dvdItems);
					displayCart(bookItems, dvdItems, total);
					
					if (bookItems.size() == 0 && dvdItems.size() == 0)
					{
						System.out.println("Your cart is empty.");
					}
					break;
					
				case 8: 
					double displayTotal = getTotal(bookItems, dvdItems); 
					System.out.print("Checkout: Your total is: $");
					System.out.printf("%.2f", displayTotal);
					
					clearCart(bookItems, dvdItems); 
					break;
					
				default: 
					break;
			}
		}while(menuChoice != 9);
	}	
	
	// Method that prints menu each time
	private static void displayMenu()
	{
		System.out.println("\n**Welcome to the Comets Books and DVDs Store**\n");
		System.out.println("Choose from the following options:");
		System.out.println("1 - Browse books inventory (price low to high)");
		System.out.println("2 - Browse DVDs inventory (price low to high)");
		System.out.println("3 - Add a book to the cart");
		System.out.println("4 - Add a DVD to the cart");
		System.out.println("5 - Delete a book from cart");
		System.out.println("6 - Delete a DVD from cart");
		System.out.println("7 - View cart");
		System.out.println("8 - Checkout");
		System.out.println("9 - Done Shopping");
		
		return;
	}
	
	// The method browseBooks returns nothing, and passes in the bookCatalog array in order to implement 
	// Collections.sort to sort all the books and display to the user
	private static void browseBooks(ArrayList<Book> bookCatalog)
	{
		Collections.sort(bookCatalog);
		for (int i = 0; i < bookCatalog.size(); i++)
		{
			if (!(bookCatalog.get(i) instanceof AudioBook))
			{
				System.out.println(bookCatalog.get(i).toString());
			}
			if (bookCatalog.get(i) instanceof AudioBook)
			{
				System.out.println(bookCatalog.get(i).toString());
			}
		}
	}
	
	// The method browseDVDs returns nothing, and passes in the dvdCatalog array in order to implement 
	// Collections.sort to sort all the DVDs and display to the user
	private static void browseDVDs(ArrayList<DVDs> dvdCatalog)
	{
		Collections.sort(dvdCatalog);
		for (int i = 0; i < dvdCatalog.size(); i++)
		{
			System.out.println(dvdCatalog.get(i).toString());
		}
	}
	
	private static void addBook(ArrayList<Book> bookCatalog, ArrayList<Book> bookItems)
	{
		int code = 0;
		// Prompting for ISBN and validating 
		System.out.print("Please enter the book's ISBN: ");
		code = validCheck.isValidInteger();		
		int bookCheck = bookMatch(bookCatalog, code);
		
		// If bookCheck is -1 due to the returned result of bookMatch, then that means there is a currently existing 
		// ISBN, so the user is asked to re enter a valid ISBN
		while (bookCheck  != -1)
		{
			System.out.print("This book is not in the catalog. Please enter another ISBN: ");
			code = input.nextInt(); 
			input.nextLine();
			bookCheck = bookMatch(bookCatalog, code);
		} 
		
		// Adds corresponding book to cart
		for (int i = 0; i < bookCatalog.size(); i++)
		{
			if (code == (bookCatalog.get(i).getISBN()))
			{
				bookItems.add(bookCatalog.get(i));
			}
		}
	}

	// Book match method checks to see if a book exists or doesn't in the catalog 
	private static int bookMatch(ArrayList<Book> BooksList, int code)
	{
		int check = 0; 
		for (int i = 0; i < BooksList.size(); i++)
		{
			if (BooksList.get(i).getISBN() == code)
				check = -1;
		}
		return check;
	}
	
	private static void addDVD(ArrayList<DVDs> dvdCatalog, ArrayList<DVDs> dvdItems)
	{
		int code = 0;
		// Prompting for ISBN and validating 
		System.out.print("Please enter the DVD's code: ");
		code = validCheck.isValidInteger();
		int dvdCheck = dvdMatch(dvdCatalog, code);
		
		// If bookCheck is -1 due to the returned result of bookMatch, then that means there is a currently existing 
		// ISBN, so the user is asked to re enter a valid ISBN
		while (dvdCheck  != -1)
		{
			System.out.print("This DVD is not in the catalog. Please enter another code: ");
			code = input.nextInt(); 
			input.nextLine();
			dvdCheck = dvdMatch(dvdCatalog, code);
		} 
		
		// Goes through and adds the corresponding DVD to the catalog
		for (int i = 0; i < dvdCatalog.size(); i++)
		{
			if (code == (dvdCatalog.get(i).getDvdCode()))
			{
				dvdItems.add(dvdCatalog.get(i));
			}
		}
	}
	
	// Checks to see if a DVD does or doesn't exist in the list
	private static int dvdMatch(ArrayList<DVDs> DVDsList, int code)
	{
		int check = 0; 
		for (int i = 0; i < DVDsList.size(); i++)
		{
			if (DVDsList.get(i).getDvdCode() == code)
			check = -1;
		}
		return check;
	}
	
	private static void removeBook(ArrayList<Book> bookItems)
	{
		int code = 0; 
		int bookCheck = 0; 
		// If the ArrayList is empty, an error statement will return, and the main menu will be displayed
		if (bookItems.isEmpty())
		{
			System.out.println("There are no books in the catalog; cannot remove anything.");
			return;
		}	
		
		System.out.println("Please enter the ISBN of the book to remove: ");
		code = validCheck.isValidInteger();
		bookCheck = bookCartMatch(bookItems, code);
		
		if (bookCheck  != -1)
		{
			System.out.println("This book doesn't exist in the cart.");
			return;
		}
		
		else 
		{
			for (int i = 0; i < bookItems.size(); i++)
			{
				if (bookItems.get(i).getISBN() == code)
				{
					bookItems.remove(i);
					System.out.println("Book removed.");
				}
			}
		}
	}

	// Checks if a book is in the specific book cart
	private static int bookCartMatch(ArrayList<Book> bookItems, int code)
	{
		int check = 0; 
		for (int i = 0; i < bookItems.size(); i++)
		{
			if (bookItems.get(i).getISBN() == code)
				check = -1;
		}
		return check;
	}
	
	private static void removeDVD(ArrayList<DVDs> dvdItems)
	{
		int code = 0; 
		int bookCheck = 0; 
		Scanner inputDRem = new Scanner (System.in); // Reading input
		
		// If the ArrayList is empty, an error statement will return, and the main menu will be displayed
		if (dvdItems.isEmpty())
		{
			System.out.println("There are no DVDs in the catalog; cannot remove anything.");
			return;
		}	
		
		System.out.println("Please enter the code of the DVD to remove: ");
		code = validCheck.isValidInteger();
		bookCheck = dvdCartMatch(dvdItems, code);
		
		if (bookCheck  != -1)
		{
			System.out.println("This DVD doesn't exist in the cart.");
			return;
		}
		
		else 
		{
			for (int i = 0; i < dvdItems.size(); i++)
			{
				if (dvdItems.get(i).getDvdCode() == code)
				{
					dvdItems.remove(i);
					System.out.println("Book removed.");
				}
			}
		}	
	}
	
	// Method checks if a DVD is in the DVD cart
	private static int dvdCartMatch(ArrayList<DVDs> dvdItems, int code)
	{
		int check = 0; 
		for (int i = 0; i < dvdItems.size(); i++)
		{
			if (dvdItems.get(i).getDvdCode() == code)
				check = -1;
		}
		return check;
	}
	
	private static double getTotal (ArrayList<Book> bookItems, ArrayList<DVDs> dvdItems)
	{
		// Initializing temporary variables
		double subtotal = 0; 
		double total = 0;
		
		// Enhanced for loop to loop through cartPrices
		for (int i = 0; i < bookItems.size(); i++)
		{
			subtotal += bookItems.get(i).getBookPrice(); 
		}
		
		for (int i = 0; i < dvdItems.size(); i++)
		{
			subtotal += dvdItems.get(i).getDvdPrice(); 
		}
		
		total = Math.round(subtotal * 1.0825); // Calculating total with tax
		
		return total; 
	}
	
	private static void displayCart(ArrayList<Book> bookItems, ArrayList<DVDs> dvdItems, double total)
	{
			System.out.println("Items             Prices");
			System.out.println("------------------------");
			for (int i = 0; i < bookItems.size(); i++)
			{
				System.out.println(bookItems.get(i).toString());
			}
			
			for (int i = 0; i < dvdItems.size(); i++)
			{
				System.out.println(dvdItems.get(i).toString());
			}
			
			System.out.println("------------------------");
			System.out.println(total);	
			return; 
	}
	
	private static void clearCart(ArrayList<Book> bookItems, ArrayList<DVDs> dvdItems)
	{
		bookItems.clear();
		dvdItems.clear();
	}
}
