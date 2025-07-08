package employees;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * Employees_Info_Program is a console-based Java application that connects to an Oracle database
 * and allows users to interact with the C_EMPLOYEE table.
 *
 * Key Features:
 * - Add new employee records with validation
 * - Look up existing employee records by Employee ID
 * - Interactive text-based menu for user navigation
 * 
 * The program uses JDBC with prepared statements to ensure security and efficiency.
 */
public class Employees_Info_Program {
    /**
     * Main method that connects to the Oracle DB and handles user interaction.
     * Prompts for DB credentials, sets up SQL queries, and enters a menu loop.
     */
	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your URL]"; 
	    int choice = 0;
	    boolean Repeat = true;

	    try(Connection conn = DriverManager.getConnection(url, user, password);
		    Statement stmt = conn.createStatement()) 
		{
			System.out.println("\nConnected successfully.");
			
            // Prepare SQL queries for insertion and employee lookups
		    String EmployeeQuery = "SELECT * FROM C_EMPLOYEE WHERE EMPLOYEE_ID = ? ";
		    String insertQuery = "INSERT INTO C_EMPLOYEE (EMPLOYEE_ID, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME, SSN, BIRTH_DATE, CURRENT_ADDRESS, GENDER, "+
		    		"SALARY, SUPERVISOR_ID, DEPT_ID) "+
                    "VALUES (SEQ_EMPLOYEE_ID.NEXTVAL, ?, ?, ?, ?, TO_DATE(?, 'DD-MON-YYYY'), ?, ?, ?, ?, ?)"; //ORA-01858: a non-numeric character was found where a numeric was expected
		    
            // PreparedStatements for secure parameterized queries
            PreparedStatement EQ = conn.prepareStatement (EmployeeQuery);
            PreparedStatement insertEmployee = conn.prepareStatement(insertQuery);
            
            // Interactive menu loop
		    do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, EQ, insertEmployee); // Handle logic for selected choice
                
            }while(Repeat);       
		}catch (SQLException e) {
	    	System.out.println("Connection failed:");
	    	e.printStackTrace();
	    }
	}
	
//////////////////////////////////////////////////////////////////////////////
    /**
     * Executes logic for a selected menu choice.
     *
    * @param choice         menu selection (1, 2, or 3)
     * @param EQ             prepared statement for employee search
     * @param insertEmployee prepared statement to insert a new employee
     * @return true if the program should continue running
     * @throws SQLException if database operations fail
     */
	 static boolean process(int choice, PreparedStatement EQ, PreparedStatement insertEmployee ) throws SQLException
		{
		    boolean Repeat = true;
		    
		    switch(choice) {
	    	case 1: // Add grade report
		    	System.out.println("Chosen to add New Employee....\n");
		    	/*EMPLOYEE_ID, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME, SSN, BIRTH_DATE, CURRENT_ADDRESS, GENDER, "+
		    		"SALARY, SUPERVISOR_ID, DEPT_ID)*/
		    	String fName = readEntry("Enter First Name: ");
		    	String mName = readEntry("Enter Middle Initial (Can be empty): ");
            	String lName = readEntry("Enter Last Name: ");
            	
	            int ssn = validSSN();
	            
	            String bDate = readEntry("Enter Birthdate (ex: DD-MON-YYYY): ");
            	while(!validDate(bDate)) {
            		System.out.println("Invalid date format. Please use DD-MON-YYYY (e.g., 25-JUN-2002)");
            	    bDate = readEntry("Enter Birthdate (ex: DD-MON-YYYY): ");
            	}
            	
            	String currAddress = readEntry("Enter Current Address: ");
            	
                String genderInput  = readEntry("Enter Gender (M/F): ");
	            char gender = genderInput.isEmpty() ? 'U' : genderInput.charAt(0);
	            
                int salary = Integer.parseInt(readEntry("Enter Employee Salary: "));
                
                // Supervisor ID (nullable)
                String supervisorInput = readEntry("Enter Supervisor ID (Can be empty): "); 
            	Integer supervisorID = supervisorInput.isEmpty() ? null : Integer.parseInt(supervisorInput); // Use null-safe parsing and setNull() 
            																								 // if left empty it'll crash
            	
                int deptID = Integer.parseInt(readEntry("Enter Department ID: "));
                
                // Bind values
                insertEmployee.setString(1, fName);
                insertEmployee.setString(2, mName);
                insertEmployee.setString(3, lName);
                insertEmployee.setInt(4, ssn);
                insertEmployee.setString(5, bDate);
                insertEmployee.setString(6, currAddress);
                insertEmployee.setString(7, String.valueOf(gender));
                insertEmployee.setInt(8, salary);
                
                if (supervisorID == null) {
                    insertEmployee.setNull(9, java.sql.Types.INTEGER);
                } else {
                    insertEmployee.setInt(9, supervisorID);
                }
                
                insertEmployee.setInt(10, deptID);

                
                int rows = insertEmployee.executeUpdate();
                
                if (rows > 0) {
                    System.out.println("New Employee added successfully.");
                } else {
                    System.out.println("Insert failed.");
                }
                
	    		break;	    				  
                
	    	case 2: // view section info
		    	System.out.println("Chosen to look for employee by Employee ID....\n");

		    	boolean found = false;
	    		String employeeIDStr = readEntry("Enter Employee ID: ");
	    		
	    		if (!employeeIDStr.matches("\\d+")) {
	    			System.out.println("Invalid input. Employee number must be numeric.");
	                break;
	            }
	    		 
	    		int employeeID = Integer.parseInt(employeeIDStr);
	    		
	    		EQ.setInt(1, employeeID);
	    		
	            ResultSet employeeRs = EQ.executeQuery();
	                
	            while (employeeRs.next()) {
	            	found = true;

                    System.out.println("\nEmployee ID: " + employeeRs.getInt("EMPLOYEE_ID"));
                    System.out.println("First Name: " + employeeRs.getString("FIRST_NAME"));
                    System.out.println("Middle Initial: " + employeeRs.getString("MIDDLE_INITIAL"));
                    System.out.println("Last Name: " + employeeRs.getString("LAST_NAME"));
                    System.out.println("SSN: " + employeeRs.getInt("SSN"));
                    System.out.println("Birthdate: " + employeeRs.getString("BIRTH_DATE"));
                    System.out.println("Current Address: " + employeeRs.getString("CURRENT_ADDRESS"));
                    System.out.println("Gender: " + employeeRs.getString("GENDER"));
                    System.out.println("Salary: " + employeeRs.getInt("SALARY"));
                    System.out.println("Supervisor ID: " + employeeRs.getInt("SUPERVISOR_ID"));
                    System.out.println("Department ID: " + employeeRs.getInt("DEPT_ID"));
                    System.out.println("---------------------------");
	            } 
	            
	            if(!found) 
	            {
	                System.out.println("No employee found with that number.");
	            }
	            
	            employeeRs.close();
	            break;	             	
	    		
	    	case 3: // exit program
	            System.out.println("Exiting, Thank you for you for using the app!");
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
        System.out.println("1. Add New Employee");
        System.out.println("2. Search for Employee by Employee ID");
        System.out.println("3. Exit");
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
     * Validates if a string follows the "dd-MMM-yyyy" date format.
     *
     * @param date the input string
     * @return true if valid, false otherwise
     */
      static boolean validDate(String date){
    	  try {
              new SimpleDateFormat("dd-MMM-yyyy").parse(date);
              return true;
          } 
    	  catch (Exception e) {
              return false;
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
     * Prompts the user to enter a valid 9-digit SSN.
     *
     * @return the SSN as an integer
     */
     static int validSSN(){
    	while(true)
    	{
    		String input = readEntry("Enter student's SSN (9 digits): ");
    	    if (input.matches("\\d{9}")) {
    	    	return( Integer.parseInt(input));
    	   }     
    	    
    	  System.out.println("Invalid input. SSN must be 9 numbers long.");
    	}
     }
	
}
