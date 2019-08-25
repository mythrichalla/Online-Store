// The file action class handles all File I/O
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileAction
{	
	// The validManager class creates a new string that is the username and password, copies all the file contents
	// into an arrayList, and traverses the list to see if the credentials are valid
	public static boolean validManager(String userName, String password)
	{
		ArrayList<String> credList = new ArrayList<String>();
		boolean valid = false; 
		String infoString = userName + "," + password;
		File infoFile = new File ("credentials.txt");
		String line = "";
		
		try
		{
			Scanner managerData = new Scanner(infoFile);
			while (managerData.hasNextLine())
			{	
				line = managerData.nextLine();
				credList.add(line);
			}
			
			for(int i = 0; i < credList.size(); i++)
			{
				if (infoString.equals(credList.get(i)))
				{
					valid = true;
					break;
				}
				else 
					valid = false;
			}

			managerData.close();
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("File not found.");
		}
		
		return valid;
	}
	
	// This method creates a backupFile using the SimpleDateFormat, it will show the current items in the catalog
	public static void backupFile(ArrayList<Book> BooksList, ArrayList<DVDs> DVDsList)
	{
		SimpleDateFormat currentTime = new SimpleDateFormat("_YYYY_MM_dd_hh_mm_ss");
		String backupFileName = "catalog_backup_" + currentTime.format(new Date());
		File writeFile = new File(backupFileName);
		
		PrintWriter output = null;
		try 
		{
			output = new PrintWriter(writeFile);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        for(int i = 0; i < BooksList.size(); i++) {
            output.write(BooksList.get(i).toString() + "\r\n");
        }
        output.write("------------\r\n");
        
        for(int i = 0; i < DVDsList.size(); i++) {
            output.write(DVDsList.get(i).toString() + "\r\n");
        }
        output.close();
	}
}
