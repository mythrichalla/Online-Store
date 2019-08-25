import java.util.*; 

public class Manager 
{
	// Declaring the input buffer outside main so it can be used in all methods
	static Scanner input = new Scanner (System.in); 
	static Validator validCheck = new Validator();
	
	/* The main method initializes the ArrayLists that will be used, as well as the menu choice variable. The switch
	 * case is the primary portion of the menu function, and various methods are called in the switch case to run 
	 * the program. 
	 */

	static ArrayList<Book> BooksList = new ArrayList<Book>(); 		// BooksList will hold both books and audio books
	static ArrayList<DVDs> DVDsList = new ArrayList<DVDs>(); 		// DVDsList will hold DVDs
	
	public void runManager ()
	{
		// Initialization of the menuChoice variable and two array lists for books and DVDs
		int menuChoice = 0;
		// The do while loop executes so long as the menuChoice is not 9 (as long as user doesn't end program)
		do 
		{
			displayMenu();			// Initial call to display the menu
			// Input validation of menuChoice, where hasNextInt checks and returns an error if input is ANYTHING other than integer
			menuChoice = validCheck.isValidInteger();	// menuChoice is assigned the input only when it is tested to be valid
			
	        while (menuChoice < 1 || menuChoice > 9 || menuChoice == 8) 
	        {
	            System.out.println("Please enter a valid integer between 1-7, or 9: ");
	            menuChoice = validCheck.isValidInteger();
	        }
			
	        // Begin switch case
			switch (menuChoice)
			{
				// Cases 1, 2, and 3 are for adding books, audio books, and DVDs respectively
				case 1: 
					addBook(BooksList, false);		// False is passed in this call to addBook since only a book is added
					break;
							
				case 2: 
					addBook(BooksList, true);		// True is passed in this call to addBook since an audio book is added
					break;
				
				case 3: 
					addDvd(DVDsList);
					break;
					
				// Cases 4 and 5 are for removing books and DVDs; the catalog is displayed after the removal of an item
				case 4: 
					removeBook(BooksList);
					displayCatalog(BooksList, DVDsList);
					break;
					
				case 5: 
					removeDvd(DVDsList);
					displayCatalog(BooksList, DVDsList);
					break;
					
				// Case 6 displays the catalog for the user
				case 6: 
					displayCatalog(BooksList, DVDsList);
					break;	
				
				case 7: 
					FileAction.backupFile(BooksList, DVDsList);
					break;
					
				default:
					break;
			
			}
			 	// The program runs so long as the input is not 9; if it is, the program ends
		} while (menuChoice != 9);
	}
	
	/*
	 * The displayMenu of type void takes no arguments, and does not return anything. The purpose is to print the main menu for the user.
	 * The escape sequence \n is used for formatting, and all 7 options are shown. 
	 */
	private static void displayMenu()
	{
		System.out.println("\n**Welcome to the Comets Books and DVDs Store (Catalog Section)**\n");
		System.out.println("Choose from the following options:");
		System.out.println("1 - Add Book");
		System.out.println("2 - Add Audio Book");
		System.out.println("3 - Add DVD");
		System.out.println("4 - Remove book");
		System.out.println("5 - Remove DVD");
		System.out.println("6 - Display Catalog");
		System.out.println("7 - Create Backup File");
		System.out.println("9 - Exit Catalog Section");
		
		return;
	}
	
	/* The method addBook does not return anything, and takes in the BooksList array and a boolean variable for whether
	 * an audio book will be added. It's purpose is to prompt for all the parts of the book or audio book, and validate
	 * the entered information. If the input is invalid, the user will be prompted to re-enter. 
	 */
	private static void addBook(ArrayList<Book> BooksList, boolean AudioBook)
	{	
		// Initialization of necessary variables 
		int code = 0;
		int bookCheck = 0;
		double runTime; 
	
		// Prompting for ISBN and validating 
		System.out.print("Please enter the book's ISBN: ");
		code = validCheck.isValidInteger();
		bookCheck = bookMatch(BooksList, code);
		
		// If bookCheck is -1 due to the returned result of bookMatch, then that means there is a currently existing 
		// ISBN, so the user is asked to re enter a valid ISBN
		while (bookCheck  == -1)
		{
			System.out.print("A book with this ISBN already exists in the catalog. Please enter another ISBN: ");
			code = input.nextInt(); 
			input.nextLine();
			bookCheck = bookMatch(BooksList, code);
		} 
		
		// Prompting for book title and validating
		System.out.print("Please enter the book's title: ");
		String title = input.nextLine(); 
		
		// Checks that the entered string is not empty, and does not contain only whitespaces
		while (!validCheck.isNonEmptyString(title))
		{
			System.out.print("Please enter a non-empty string: "); 
			title = input.nextLine();
		}
		
		// Prompting for book author and validating
		System.out.print("Please enter the book's author: ");
		String author = input.nextLine();
		
		// Checks that the entered string is not empty, and does not contain only whitespaces
		while (!validCheck.isNonEmptyString(author))
		{
			System.out.print("Please enter a non-empty string: "); 
			author = input.nextLine();
		}
		
		// Prompting for book price, if the next value in the input buffer is not of type Double, user must re enter
		System.out.print("Please enter the book's price: $ ");
		double price = validCheck.isValidDouble();
		
		// Checking if price is negative, if not, error is returned
		while (validCheck.isPositiveInput(price) == false)
		{
			System.out.print("Please enter a positive value for the price: $ ");
			price = input.nextDouble();
		}
		
		// Creating a successful audio book object and adding it to the array list
		// The if statement only goes through if the boolean value of the audio book is true, based on initial call
		if (AudioBook)
		{
			// Prompting for the run time and validating
			System.out.print("Please enter the run time of the book: ");
			runTime = validCheck.isValidDouble();

		    // Checking if run time is negative, if not, error is returned
			while (validCheck.isPositiveInput(runTime) == false)
			{
				System.out.print("Please enter a positive value for the run time: ");
				runTime = input.nextDouble();
			}
			
			// Instantiating an Audio Book object and adding an audio book to bookList 
			AudioBook audioBook = new AudioBook (author, title, price, code, runTime); 
			BooksList.add(audioBook);
		}
		
		else 
		{
			// Instantiating a book object and adding it to the BooksList
			Book book = new Book (author, title, price, code);
			BooksList.add(book);
		}
		return;
	}
		
	/* The bookMatch function checks to see if an ISBN already exists in the BooksList by looping through
	 * the ArrayList, calling the getISBN method of the current book object in the ArrayList, and seeing if 
	 * it matches the code passed in from the addBook method. If there is a match, the check variable is set 
	 * to -1 to indicate a duplicate, and this is returned. 
	 */
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
	
	/* The method addDvd does not return anything, and takes in the DVDsList array. It's purpose is to prompt 
	 * for all the parts of the DVD, and validate the entered information. If the input is invalid, 
	 * the user will be prompted to re-enter. 
	 */
	
	private static void addDvd(ArrayList<DVDs> DVDsList)
	{
		// Initialization of necessary variables 
		int code = 0;
		int year = 0;
		int DvdCheck = 0;
		
		// Prompting for DVD code and validating, hasNextInt is used to check if an integer is in the buffer
		System.out.print("Please enter the book's DVD code: ");
		code = validCheck.isValidInteger();
		DvdCheck = DvdMatch(DVDsList, code);		// DvdCheck will be assigned -1 if there is a duplicate DVD code
		
		// As long as the DvdMatch function returns -1, the while loop will keep prompting for another code
		while (DvdCheck  == -1)
		{
			System.out.print("A DVD with this code already exists in the catalog. Please enter another code: ");
			code = input.nextInt();
			input.nextLine();
			DvdCheck = DvdMatch(DVDsList, code);
		} 	
		
		// Prompting for DVD title and validating
		System.out.print("Please enter the DVD's title: ");
		String title = input.nextLine();
		
		// Checks to make sure that the string is not empty 
		while (!validCheck.isNonEmptyString(title))
		{
			System.out.print("Please enter a non-empty string: "); 
			title = input.nextLine();
		} 
				
		// Prompting for DVD director and validating
		System.out.print("Please enter the DVD's director: ");
		String director = input.nextLine();
		
		// Checking that the string is not empty
		while (!validCheck.isNonEmptyString(director))
		{
			System.out.print("Please enter a non-empty string: "); 
			director = input.nextLine();
		}
		
		// Prompting for the year and validating
		System.out.print("Please enter the DVD's year: ");
		year = validCheck.isValidInteger();
		
		// Year must be positive, if not, error is returned
		while (validCheck.isPositiveInput(year) == false)
		{
			System.out.print("Please enter a valid year: ");
			year = input.nextInt();
		}
				
		// Prompting for DVD price, this value must be a Double in order to be valid
		System.out.print("Please enter the DVD's price: $ ");
		double price = validCheck.isValidDouble();
		
		// Checking if price is negative, if not, error is returned
		while (validCheck.isPositiveInput(price) == false)
		{
			System.out.println("Please enter a positive value for the price: $ ");
			price = input.nextDouble();
		}
		
		// Creating a successful DVD object and adding it to DVDsList
		DVDs Dvd = new DVDs (director, title, year, price, code);
		DVDsList.add(Dvd);
		
		return;
	}
	
	/* The DvdMatch function checks to see if a DvdCode already exists in the DVDsList by looping through
	 * the ArrayList, calling the getDvdCode method of the current DVD object in the ArrayList, and seeing if 
	 * it matches the code passed in from the addDVD method. If there is a match, the check variable is set 
	 * to -1 to indicate a duplicate, and this is returned. 
	 */
	private static int DvdMatch(ArrayList<DVDs> DVDsList, int code)
	{
		int check = 0; 
		for (int i = 0; i < DVDsList.size(); i++)
		{
			if (DVDsList.get(i).getDvdCode() == code)
			check = -1;
		}
		return check;
	}
	
	/* The removeBook method takes in parameter for the BooksList ArrayList and does not return anything.
	 * It's purpose is to check whether the user's request to remove a book is valid. The request will be
	 * invalid if there is no book or audio book in the catalog, or if the requested ISBN does not exist.
	 * If the request is valid, the correct ISBN is found and that object is removed from the list. 
	 */
	
	private static void removeBook(ArrayList<Book> BooksList)
	{
		// Initialization of necessary variables 
		int code = 0; 
		int bookCheck = 0; 
		
		// If the ArrayList is empty, an error statement will return, and the main menu will be displayed
		if (BooksList.isEmpty())
		{
			System.out.println("There are no books in the catalog; cannot remove anything.");
			return;
		}
		
		// Checking to make sure user enters a valid ISBN of type int
		System.out.println("Please enter the ISBN of the book to remove: ");
		code = validCheck.isValidInteger();		// The code variable is assigned the valid value of ISBN
		bookCheck = bookMatch(BooksList, code);		// Using the bookMatch method to see if the ISBN exists
		
		// If book doesn't exist, error statement is returned
		if (bookCheck  != -1)
		{
			System.out.println("This book doesn't exist in the catalog.");
			return;
		} 
		
		// Else, loop through the ArrayList by using the get method and getISBN functions, and remove the book
		else
		{
			for (int i = 0; i < BooksList.size(); i++)
			{
				if (BooksList.get(i).getISBN() == code)
				{
					BooksList.remove(i);
					System.out.println("Book removed.");
				}
			}
		}
		return;
	}
	
	/* The removeDvd method takes in parameter for the DVDsList ArrayList and does not return anything.
	 * It's purpose is to check whether the user's request to remove a DVD is valid. The request will be
	 * invalid if there is no DVD in the catalog, or if the requested DVD code does not exist.
	 * If the request is valid, the correct code is found and that object is removed from the list. 
	 */
	private static void removeDvd(ArrayList<DVDs> DVDsList)
	{
		// Initialization of necessary variables
		int code = 0; 
		int DvdCheck = 0; 
		
		// If the ArrayList is empty, an error statement will return, and the main menu will be displayed
		if (DVDsList.isEmpty())
		{
			System.out.println("There are no DVDs in the catalog; cannot remove anything.");
			return;
		}
		
		// Checks to see that a valid DVD code has been entered
		System.out.println("Please enter the code of the DVD to remove: ");
		code = validCheck.isValidInteger();				// The code variable is assigned the valid value of DVD code
		DvdCheck = DvdMatch(DVDsList, code);    // Using the DvdMatch method to see if the DVD code exists
		
		// If DVD doesn't exist, error statement is returned
		if (DvdCheck  != -1)
		{
			System.out.println("This DVD doesn't exist in the catalog.");
			return;
		} 
		
		// Else, loop through the ArrayList by using the get method and getDvdCode functions, and remove the DVD
		else
		{
			for (int i = 0; i < DVDsList.size(); i++)
			{
				if (DVDsList.get(i).getDvdCode() == code)
				{
					DVDsList.remove(i);
					System.out.println("DVD removed.");
				}
			}
		}
		return;
	}
	
	/* This method is of type void and takes in parameters for the BooksList and DVDsList. It's purpose is 
	 * simply to display the books and DVDs in the catalog, or return an error statement if the catalog is empty.
	 */
	public static void displayCatalog(ArrayList<Book> BooksList, ArrayList<DVDs> DVDsList)
	{
		// If both BooksList and DVDsList is empty, then the entire catalog is empty; use isEmpty method to check
		if ((BooksList.isEmpty() &&  DVDsList.isEmpty()))
			System.out.println("The catalog is empty.");
		
		// If the catalog is not empty, loop through the ArrayLists to display all the items
		else
		{
			System.out.println("*******************Catalog********************\n");
			System.out.println("--------------------Books---------------------\n");
			
			// While looping through BooksList, use the instanceof operator to check which version of
			// the toString method should be called; if current object is an instance of the audio book
			// class, the overridden version of the toString method will be printed
			for (int i = 0; i < BooksList.size(); i++)
			{
				if (!(BooksList.get(i) instanceof AudioBook))
				{
					System.out.println(BooksList.get(i).toString());
				}
				if (BooksList.get(i) instanceof AudioBook)
				{
					System.out.println(BooksList.get(i).toString());
				}
			}
			
			
			System.out.println("----------------------------------------------\n");
			System.out.println("---------------------DVDs---------------------\n");
			
			for (int i = 0; i < DVDsList.size(); i++)
			{
				System.out.println(DVDsList.get(i).toString());
			}
		}
		return;
	}

}
