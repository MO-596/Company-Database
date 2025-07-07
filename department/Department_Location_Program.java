package departments;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Department_Location_Program {
    /**
     * Starts the interactive session for managing courses and sections.
     * Prompts for user credentials, displays menu options, and processes
     * choices using prepared SQL statements.
     */
	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your url]"; 
		int choice = 0;
	    boolean Repeat = true;
	    
	    try(Connection conn = DriverManager.getConnection(url, user, password);
		        Statement stmt = conn.createStatement()) {
	    	
            System.out.println("\nConnected successfully.");
            
            // Prepare SQL queries for course and section lookups
            String deptLocationQuery = "select * FROM C_DEPARTMENT_LOCATION WHERE DEPT_ID = ?";
            String insertDeptQuery = "INSERT INTO C_DEPARTMENT_LOCATION (DEPT_ID, LOCATION)"
            		+ "VALUES (?, ?)";
            
            PreparedStatement deptLoctQuery = conn.prepareStatement (deptLocationQuery);
            PreparedStatement insertQuery = conn.prepareStatement(insertDeptQuery);
            
            // Interactive menu loop
            do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, deptLoctQuery, insertQuery); // Handle logic for selected choice
                
            }while(Repeat);       
         
            
	    }catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
        }
	}
		//////////////////////////////////////////////////////////////////////////////
	
	static boolean process(int choice, PreparedStatement deptLoctQuery, PreparedStatement insertQuery) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Department ocation
		    	System.out.println("Chosen to add new location for department....\n");
		    	
                int deptID = Integer.parseInt(readEntry("Enter Department ID: "));
                
                // Check if department already exists
                if (!departmentExists(deptLoctQuery.getConnection(), deptID)) {
                    System.out.println("Department ID doesn't exists. Insert aborted.");
                    break;
                }
                
                String location = readEntry("Enter Location for Department: ");
	    		
                insertQuery.setInt(1, deptID);       
                insertQuery.setString(2, location);        
                
                int rows = insertQuery.executeUpdate();
                
                if (rows > 0){
                    System.out.println("New Department Location added successfully.");
                } 
                else{
                    System.out.println("Insert failed.");
                }
                
	    		break;	
	    		
	    	case 2:
		    	System.out.println("Chosen to look for a department Location by department ID....\n");
	    		String deptIDStr = readEntry("Enter Department ID: ");
            	
            	// Input validation: ensure input is numeric
                if (!deptIDStr.matches("\\d+")) {
                    System.out.println("Invalid input. Please enter numeric values only.");
                    break;
                }
                
                int DeptNum = Integer.parseInt(deptIDStr);

                deptLoctQuery.clearParameters();
                deptLoctQuery.setInt(1,DeptNum);
            	
            	ResultSet rs = deptLoctQuery.executeQuery();
            	
            	if (rs.next()) {
            		System.out.println("Department ID: " + rs.getString("DEPT_ID"));
            		System.out.println("Department Location: " + rs.getString("LOCATION"));
                    System.out.println("---------------------------");

            	}
            	else {
            		System.out.println("No department found with that ID.");
            	}
            	
            	rs.close();
            	break;
            	
	    	case 3:	
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
        System.out.println("1. Add New location for Department.");
        System.out.println("2. View Departments Location by Department ID.");
        //System.out.println("3. View All Departments.");
        System.out.println("3. Exit\n");
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
                if (val >= 1 && val <= 3) {
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
     * Checks if a department already exists in the database using its name or code.
     *
     * @param conn      active database connection
     * @param deptCode  department code
     * @return true if the department exists; false otherwise
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
	
}
