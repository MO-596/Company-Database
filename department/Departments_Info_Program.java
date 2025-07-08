package departments;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Departments_Info_Program {
    /**
     * Departments_Info_Program is a console-based Java application that connects to an Oracle database
     * and allows users to interact with the C_DEPARTMENT table.
 	*
 	* Key Features:
 	* - Add new department with validation
 	* - Look up existing departments records by department ID
 	* - Interactive text-based menu for user navigation
 	* 
 	* The program uses JDBC with prepared statements to ensure security and efficiency.
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
            String departmentQuery = "select * FROM C_DEPARTMENT WHERE DEPT_ID = ?";
            String insertDepQuery = "INSERT INTO C_DEPARTMENT (DEPT_NAME, DEPT_ID, MANAGER_ID, OFFICE_NUMBER, PHONE_NUMBER)"
            		+ "VALUES (?, SEQ_DEPT_ID.NEXTVAL, ?, ?, ?)";
            
            PreparedStatement deptQuery = conn.prepareStatement (departmentQuery);
            PreparedStatement insertQuery = conn.prepareStatement(insertDepQuery);
            
            // Interactive menu loop
            do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, deptQuery, insertQuery); // Handle logic for selected choice
                
            }while(Repeat);       
         
            
	    }catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
        }
	}
	
	//////////////////////////////////////////////////////////////////////////////
	
	static boolean process(int choice, PreparedStatement deptQuery, PreparedStatement insertQuery) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Department
		    	System.out.println("Chosen to add a new department....\n");
		    	
		    	String deptName = readEntry("Enter Department Name: ");
                //int deptID = Integer.parseInt(readEntry("Enter Department ID: "));
                
                // Check if department already exists
                if (departmentExists(deptQuery.getConnection(), deptName)) {
                    System.out.println("Department with that name already exist. Insert aborted.");
                    break;
                }
                
                String managerIDInput = readEntry("Enter Mangaer ID: ");
            	Integer managerID = managerIDInput.isEmpty() ? null : Integer.parseInt(managerIDInput);

                
                int officeNum = Integer.parseInt(readEntry("Enter Office Number: "));
                String phoneNum = readEntry("Enter Department Phone Number (Need to be 10 numbers): ");
	    		
                insertQuery.setString(1, deptName);
                //insertQuery.setInt(2, deptID);
                
                if (managerID == null) {
                	insertQuery.setNull(2, java.sql.Types.INTEGER);
                } else {
                	insertQuery.setInt(2, managerID);
                }
                
                insertQuery.setInt(3, officeNum);        
                insertQuery.setString(4, phoneNum);
                
                int rows = insertQuery.executeUpdate();
                
                if (rows > 0){
                    System.out.println("New Department added successfully.");
                } 
                else{
                    System.out.println("Insert failed.");
                }
                
	    		break;	
	    		
	    	case 2:
		    	System.out.println("Chosen to look for a department by department ID....\n");
	    		String deptIDStr = readEntry("Enter Department ID: ");
            	
            	// Input validation: ensure input is numeric
                if (!deptIDStr.matches("\\d+")) {
                    System.out.println("Invalid input. Please enter numeric values only.");
                    break;
                }
                
                int DeptID = Integer.parseInt(deptIDStr);

                deptQuery.clearParameters();
                deptQuery.setInt(1,DeptID);
            	
            	ResultSet rs = deptQuery.executeQuery();
            	
            	if (rs.next()) {
            		System.out.println("Department Name: " + rs.getString("DEPT_NAME"));
            		System.out.println("Department ID: " + rs.getString("DEPT_ID"));
            		System.out.println("Manager ID: " + rs.getString("MANAGER_ID"));

            		System.out.println("Office Number: " + rs.getString("OFFICE_NUMBER"));
                
            		System.out.println("Phone Number: " + rs.getString("PHONE_NUMBER"));
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
        System.out.println("1. Add New Department.");
        System.out.println("2. View Departments by Department ID.");
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
     * @param deptName  department name
     * @param deptCode  department code
     * @return true if the department exists; false otherwise
     */
	static boolean departmentExists(Connection conn, String deptName) {
  	  try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM C_DEPARTMENT WHERE DEPT_NAME = ? ")) {
  		  ps.setString(1, deptName);
          ResultSet rs = ps.executeQuery();
          return rs.next();// true if any row is returned
          
  	  }catch (SQLException e) {
          System.out.println("Failed to check department existence.");
          e.printStackTrace();
          return false;
  	  }
    }
	

}
