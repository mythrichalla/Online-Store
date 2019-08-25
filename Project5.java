// Term Project -- Mythri Challa

/* This java program is a compilation of several smaller programs that simulate an online store, integrated into one.
 * It has a main menu that allows the user to choose between manager and customer roles, each of which display another
 * set of menu choices. The user can only have a manager role if credentials are valid, and the store updates across
 * all portions of the program. 
 */
import java.util.*;

public class Project5
{	
	public static void main (String[] args)
	{
		String letterChoice;
		Validator validCheck = new Validator();
		FileAction fileObject = new FileAction();
		
	    // Initializing Books
        Manager.BooksList.add(new Book("Daniel Liang", "Intro to Java", 45.99, 1));
        Manager.BooksList.add(new Book("Daniel Liang", "Intro to C++", 89.34, 2));
        Manager.BooksList.add(new Book("Daniel Liang", "Python", 100, 3));
        Manager.BooksList.add(new Book("Daniel Liang", "Perl", 25, 4));
        Manager.BooksList.add(new Book("Daniel Liang", "C#", 49.99, 5));

        // Initializing DVDs 
        Manager.DVDsList.add(new DVDs("Rupert Sanders", "Snow White", 2000, 19.99, 1));
        Manager.DVDsList.add(new DVDs("Kenneth Branagh", "Cinderella", 2002, 24.99, 2));
        Manager.DVDsList.add(new DVDs("Tim Burton", "Dumbo", 2004, 17.99, 3));
        Manager.DVDsList.add(new DVDs("David Hand", "Bambi", 2003, 21.99, 4));
        Manager.DVDsList.add(new DVDs("Jennifer Lee", "Frozen", 2013, 24.99, 5));
        Scanner input = new Scanner (System.in); 
        
		do
		{
			displayMainMenu(); 
			letterChoice = input.nextLine();
			while (!validCheck.isNonEmptyString(letterChoice) || (!letterChoice.equals("A") && !letterChoice.equals("B") && !letterChoice.equals("C")))
			{
				System.out.println("Please enter a valid menu choice (A, B, or C)");
				letterChoice = input.nextLine();
			}
			boolean isValidManager = false; 
		    switch (letterChoice)
		    {	
		    	case "A": 
		    	{	
		    		do {
		    		String usernameInput = "";
		    		String passwordInput = "";
		    		Scanner inputUName = new Scanner (System.in);
		    		
		    		System.out.print("Please enter your username: ");
		    		usernameInput = inputUName.nextLine();
		    		
	    			while(validCheck.isNonEmptyString(usernameInput) == false)	// Asking for username and password input
		    		{
		    			System.out.print("Please enter your username: ");
			    		usernameInput = inputUName.nextLine();
		    		} 
	    		
	    			System.out.print("Please enter your password: ");
		    		passwordInput = inputUName.nextLine();
		    		
	    			while (validCheck.isNonEmptyString(passwordInput) == false)
		    		{
		    			System.out.print("Please enter your password: ");
			    		passwordInput = inputUName.nextLine();
		    		} 
	    			
	    			isValidManager = fileObject.validManager(usernameInput, passwordInput);
					
	    			if (isValidManager == false)
						System.out.println("These credentials are invalid. Please try again.");
					
		    	  } while (isValidManager == false);
		    		

					if (isValidManager == true)
					{
						Manager manager = new Manager();
						manager.runManager();		// If the credentials are valid, run the menu
					}
		    	}
		    	break;
		    	
		    	case "B":
		    		Customer customer = new Customer();
		    		customer.runCustomer();
		    		break;
		    		
		    	default: 
		    		break;
		    }
			
		} while (!letterChoice.equals("C"));
	}

	private static void displayMainMenu()
	{
		System.out.println("\n**Welcome to the Comets Books and DVDs Store (Catalog Section)**\n");
		System.out.println("Please select your role:");
		System.out.println("A - store manager");
		System.out.println("B - customer");
		System.out.println("C - exit store");
	
		return;
	}
}