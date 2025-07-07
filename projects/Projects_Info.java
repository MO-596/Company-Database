package projects;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Projects_Info is an interactive Java program that connects to an Oracle database
 * to allows users to interact with the C_PROJECT table. 
 * Key Features:
 * - Insert a new project entry (with name, location, and associated department ID)
 * - Retrieve a project by its ID
 * - List all projects
 * 
 * This class uses JDBC for database operations and supports prepared statements
 * for secure querying and data insertion.
 */
public class Projects_Info {
    /**
     * The main method initiates the database connection and handles the user interaction loop.
     * It prompts the user for Oracle DB credentials, then provides a menu interface to manage
     * project records in the C_PROJECT table.
     *
     * @param args unused
     */
	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your URL]"; 
		int choice = 0;
		
	    boolean Repeat = true;
	    try(Connection conn = DriverManager.getConnection(url, user, password);
		        Statement stmt = conn.createStatement()) {
	    	
            System.out.println("\nConnected successfully.");
            
            // Prepare SQL queries for course and section lookups
            String projectQuery = "SELECT * FROM C_PROJECT WHERE PROJECT_ID = ?";
            String GeneralQuery = "SELECT * FROM C_PROJECT" ;
            String insertProjQuery = "INSERT INTO C_PROJECT (PROJECT_NAME, PROJECT_ID, PROJECT_LOCATION, PROJECT_DEPT_ID)"
            		+ "VALUES (?, SEQ_PROJECT_ID.NEXTVAL, ?, ?)";

            PreparedStatement ProjQuery = conn.prepareStatement (projectQuery);
            PreparedStatement insertQuery = conn.prepareStatement(insertProjQuery);
            PreparedStatement GenQuery = conn.prepareStatement(GeneralQuery);
            // Interactive menu loop
            do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, ProjQuery, insertQuery, GenQuery); // Handle logic for selected choice
                
            }while(Repeat);       
         
            
	    }catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
        }
	}
	
	//////////////////////////////////////////////////////////////////////////////
    /**
     * Handles the logic for each menu choice including insert, search, and view-all operations.
     *
     * @param choice user-selected menu option
     * @param ProjQuery prepared statement for project lookup by ID
     * @param insertQuery prepared statement for inserting a new project
     * @param GenQuery prepared statement for retrieving all projects
     * @return true to continue the menu loop, false to exit
     * @throws SQLException if a database error occurs
     */
	static boolean process(int choice, PreparedStatement ProjQuery, PreparedStatement insertQuery, PreparedStatement GenQuery) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    case 1:
	    	System.out.print("Chosen to create new project.....\n");
	    	
	    	String projName = readEntry("Enter Project Name:");
	    	String projLoct = readEntry("Enter Project Location: ");
	    	
	    	int projDeptID = Integer.parseInt(readEntry("Enter Department ID: "));
	    	
	    	// Check if department already exists
            if (!departmentExists(ProjQuery.getConnection(), projDeptID)) {
                System.out.println("Department ID doesn't exists. Insert aborted.");
                break;
            }
	    	
	    	insertQuery.setString(1,projName);
	    	//insertQuery.setInt(2, projID);
	    	insertQuery.setString(2, projLoct);
	    	insertQuery.setInt(3, projDeptID);
	    	
	    	int row = insertQuery.executeUpdate();;
	    	if(row > 0) {
	    		System.out.println("New Project successfully added!\n");
	    	}
	    	else {
	    		System.out.println("Insert has failed, try again!");
	    	}
	    	
	    	break;
	    	
	    case 2:
	    	System.out.print("Chosen to look for a project by project ID.....\n");
    		String projIDStr = readEntry("Enter Project ID: ");
        	
        	// Input validation: ensure input is numeric
            if (!projIDStr.matches("\\d+")) {
                System.out.println("Invalid input. Please enter numeric values only.");
                break;
            }
            
            int projID = Integer.parseInt(projIDStr);
            
            ProjQuery.clearParameters();
            ProjQuery.setInt(1,projID);
            
          	ResultSet rs = ProjQuery.executeQuery();
        	
        	if (rs.next()) {
        		System.out.println("Project Name: " + rs.getString("PROJECT_NAME"));
        		System.out.println("Project ID: " + rs.getString("PROJECT_ID"));
        		System.out.println("Project Location: " + rs.getString("PROJECT_LOCATION"));
        		System.out.println("Project Department ID: " + rs.getString("PROJECT_DEPT_ID"));
                System.out.println("---------------------------");

        	}
        	
        	rs.close();

	    	break;
	    	
	    case 3: 
	    	System.out.print("Chosen to see all projects....\n");
	    	boolean found = false;
          	ResultSet grs = GenQuery.executeQuery();
          	
          	while (grs.next()) {
            	found = true;

          		System.out.println("Project Name: " + grs.getString("PROJECT_NAME"));
        		System.out.println("Project ID: " + grs.getString("PROJECT_ID"));
        		System.out.println("Project Location: " + grs.getString("PROJECT_LOCATION"));
        		System.out.println("Project Department ID: " + grs.getString("PROJECT_DEPT_ID"));
                System.out.println("---------------------------");        	        	}
          	
        	if(!found) {
              	System.out.println("No project found with that ID.");
            }
        	
        	grs.close();

	    	break;
	    	
	    case 4:
            System.out.println("Exiting!");
            System.out.println("\n------------------------\n");
            Repeat = false;
            break;
	    }
	    
	    return Repeat;
	}
	//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Displays menu options to the user.
     */
	static void menuOptions() {
        System.out.println("\n--- MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. Create New Project.");
        System.out.println("2. View Project by Project ID.");
        System.out.println("3. View All Projects.");
        System.out.println("4. Exit\n");
	}
	
//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Reads and validates the user's numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 3
     */
	static int readInput(int choice)
	{
		while(true)
		{
			String input = readEntry("Enter choice: ");
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= 1 && val <= 4) {
                	return val;
                }
            }     		
            System.out.println("Invalid input. Please enter a number between 1 to 3");
		}
	}		
	
//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Prompts the user and reads input from console.
     *
     * @param prompt the message to display
     * @return the trimmed string entered by the user
     */
	static String readEntry(String prompt) {
     	try{
     		StringBuffer buffer = new StringBuffer();
     		System.out.print(prompt);
     		System.out.flush();
     		int c =System.in.read();
     		while(c != '\n' && c != -1) {
     			buffer.append((char)c);
     			c = System.in.read();
     		}
     		return buffer.toString().trim();
     	}catch (IOException e) {
     		return "";
     		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Verifies if a department exists in the database based on DEPT_ID.
     *
     * @param conn    the database connection
     * @param deptID  the department ID to check
     * @return true if the department exists, false otherwise
     */
	static boolean departmentExists(Connection conn, int deptID) {
  	  try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM C_DEPARTMENT WHERE DEPT_ID = ?")) {
  		  ps.setInt(1,deptID);
          ResultSet rs = ps.executeQuery();
          return rs.next();// true if any row is returned
          
  	  }catch (SQLException e) {
          System.out.println("Failed to check department existence.");
          e.printStackTrace();
          return false;
  	  }
    }
}
