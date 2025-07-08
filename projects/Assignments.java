package projects;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Assignments is an interactive Java program that connects to an Oracle database
 * to allows users to interact with the C_ASSIGNMENTS table. 
 * Key Features:
 * 1. Assign employees to projects by recording hours and departments
 * 2. Query assignments by employee ID
 * 3. View all employee-project assignments
 * 
 * The program uses JDBC to connect to an Oracle SQL database and interact
 * with the C_ASSIGNMENTS table.
 */
public class Assignments {

	public static void main(String[] args) {
		// TODO: it'll do a two choice process where one is to enter info to assign employees to a project 
		// and the second option is to look for which employee is working on what
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
            String assigementQuery = "SELECT * FROM C_ASSIGNMENTS WHERE EMPLOYEE_ID = ? AND PROJECT_ID = ?";
            String GeneralQuery = "SELECT * FROM C_ASSIGNMENTS" ;
            String insertAssignQuery = "INSERT INTO C_ASSIGNMENTS (EMPLOYEE_ID, PROJECT_ID, HOURS_WORKED, DEPT_ID)"
            		+ "VALUES (?, ?, ?, ?)";

            PreparedStatement AssignQuery = conn.prepareStatement (assigementQuery);
            PreparedStatement insertQuery = conn.prepareStatement(insertAssignQuery);
            PreparedStatement GenQuery = conn.prepareStatement(GeneralQuery);
            // Interactive menu loop
            do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, AssignQuery, insertQuery, GenQuery); // Handle logic for selected choice
                
            }while(Repeat);       
         
            
	    }catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
        }
	}	
	
	//////////////////////////////////////////////////////////////////////////////
	/**
     * Handles logic for assignment insertion, lookup by employee ID,
     * and viewing all assignments.
     */
	static boolean process(int choice, PreparedStatement AssignQuery, PreparedStatement insertQuery, PreparedStatement GenQuery) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    case 1:
	    	System.out.print("Chosen to create new project.....\n");
	    	
	    	int employeeID = Integer.parseInt(readEntry("Enter Employee ID:"));
	    	if (!employeeExists(AssignQuery.getConnection(), employeeID)) {
	    		System.out.println("Employee ID does not exist. Insert aborted.");
                break;
            }
	    	 
	    	int projID = Integer.parseInt(readEntry("Enter Project ID: "));
            if (!projectExists(AssignQuery.getConnection(), projID)) {
                System.out.println("Project ID does not exist. Insert aborted.");
                break;
            }

	    	int hrWorked =Integer.parseInt(readEntry("Enter Hours Worked on Project: "));
	    	
	    	int deptID = Integer.parseInt(readEntry("Enter Department ID: "));
	    	// Check if department already exists
            if (!departmentExists(AssignQuery.getConnection(), deptID)) {
                System.out.println("Department ID doesn't exists. Insert aborted.");
                break;
            }
	    	
	    	insertQuery.setInt(1,employeeID);
	    	insertQuery.setInt(2, projID);
	    	insertQuery.setInt(3, hrWorked);
	    	insertQuery.setInt(4, deptID);
	    	
	    	int row = insertQuery.executeUpdate();;
	    	if(row > 0) {
	    		System.out.println("New Project successfully added!\n");
	    	}
	    	else {
	    		System.out.println("Insert has failed, try again!");
	    	}
	    	
	    	break;
	    	
	    case 2:
	    	System.out.println("Chosen to View Assignment by Employee ID and Project ID.....\n");
	    	
    		String employeeIDStr = readEntry("Enter Employee ID: ");
    		String projectIDStr = readEntry("Enter Project ID: ");
    		
    		if (!employeeIDStr.matches("\\d+") || !projectIDStr.matches("\\d+")) {
    			System.out.println("Invalid input. Please enter numeric values only.");
                break;
            }
    		 
    		int EmployeeID = Integer.parseInt(employeeIDStr);
    		int projectID = Integer.parseInt(projectIDStr);
 
    	    AssignQuery.clearParameters();
    		AssignQuery.setInt(1, EmployeeID);
    		AssignQuery.setInt(2, projectID);
    		
            ResultSet rs = AssignQuery.executeQuery();
                
            if (rs.next()) {
                System.out.println("\nEmployee ID: " + rs.getInt("EMPLOYEE_ID"));
                System.out.println("Project ID: " + rs.getString("PROJECT_ID"));
                System.out.println("Hours Work On: " + rs.getString("HOURS_WORKED"));
                System.out.println("Department ID: " + rs.getInt("DEPT_ID"));
                System.out.println("---------------------------");
            } else {
        		System.out.println("No assignment found for that Employee and Project combination.");
        	}           
            rs.close();
	    	break;
	    	
	    case 3:
	    	System.out.print("Chosen to view all assignments....\n");
	    	boolean found = false;
          	ResultSet grs = GenQuery.executeQuery();
          	
          	while (grs.next()) {
            	found = true;
                System.out.println("\nEmployee ID: " + grs.getInt("EMPLOYEE_ID"));
                System.out.println("Project ID: " + grs.getString("PROJECT_ID"));
                System.out.println("Hours Work On: " + grs.getString("HOURS_WORKED"));
                System.out.println("Department ID: " + grs.getInt("DEPT_ID"));
                System.out.println("---------------------------");  	}
          	
        	if(!found) {
              	System.out.println("No assignemnts found.");
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
        System.out.println("1. Assign Employee to a Project.");
        System.out.println("2. View Assignment by Employee ID and Project ID.");
        System.out.println("3. View All Assignemnts.");
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
            System.out.println("Invalid input. Please enter a number between 1 to 4");
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
	
	//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Verifies if a employee exists in the database based on EMPLOYEE_ID.
     *
     * @param conn    the database connection
     * @param employeeID  the employee ID to check
     * @return true if the department exists, false otherwise
     */
	static boolean employeeExists(Connection conn, int employeeID) {
  	  try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM C_EMPLOYEE WHERE EMPLOYEE_ID = ?")) {
  		  ps.setInt(1,employeeID);
          ResultSet rs = ps.executeQuery();
          return rs.next();// true if any row is returned
          
  	  }catch (SQLException e) {
          System.out.println("Failed to check employee existence.");
          e.printStackTrace();
          return false;
  	  }
    }
	
	//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Verifies if a project exists in the database based on PROJECT_ID.
     *
     * @param conn    the database connection
     * @param projectID  the project ID to check
     * @return true if the project exists, false otherwise
     */
	static boolean projectExists(Connection conn, int projectID) {
  	  try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM C_PROJECT WHERE PROJECT_ID = ?")) {
  		  ps.setInt(1,projectID);
          ResultSet rs = ps.executeQuery();
          return rs.next();// true if any row is returned
          
  	  }catch (SQLException e) {
          System.out.println("Failed to check project existence.");
          e.printStackTrace();
          return false;
  	  }
    }
}
