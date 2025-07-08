package main;
import java.io.IOException;
import java.sql.SQLException;

import departments.Departments_Info_Program;
import departments.Department_Location_Program;
import employees.Dependents_Info;
import employees.Employees_Info_Program;
import projects.Assignments;
import projects.Projects_Info;

public class Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		int choice = 0;
	    boolean Repeat = true;            
           
	    // Interactive menu loop
        do {
        	menuOptions(); // Displays Menu options
            choice = readInput(choice); // Gets user input
            Repeat = process(choice); // Handle logic for selected choice
                
         }while(Repeat);	
	}
	
	//////////////////////////////////////////////////////////////////////////////
    /**
     * Handles logic based on the main menu user's selection.
     *
     * @param choice the menu option chosen by user
     * @return true if the menu should repeat, false to exit
     * @throws SQLException if an error occurs while running database operations
     */
	static boolean process(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Navigates to the students sub menu options
		    	System.out.println("Chosen to manage Employee/Dependents....\n");
		    	Employees();
	    		break;	    	

	    	case 2:
		    	System.out.println("Chosen to manage Departments....\n");
		    	Departments(); // Navigates to the departments sub menu & handles department-related tasks
	    		break;	    	

	    	case 3:
		    	System.out.println("Chosen to manage Projectss....\n");
		    	Projects(); // Navigates to the course / section sub menu & operations
	    		break;	    	

	    	case 4:	
	            System.out.println("Exiting, Thank you for you for using the app!");
	            System.out.println("\n------------------------\n");

	            Repeat = false;
	            break;
	    }
	    return Repeat;
	}
	
	//////////////////////////////////////////////////////////////////////////////

    /**
     * Handles the student submenu interactions.
     *
     * @throws SQLException if a database operation fails
     */
	static void Departments() throws SQLException {
		int choice = 0;
	    boolean Repeat = true;   
	    
    	do {
    		departmentMenuOptions(); // Display Departments-related options
    		choice = departmentsReadInput(choice); // Gets user input
    		Repeat = departmentsProcess(choice); // Process student menu input
    	}while(Repeat);
    	
	}
	
	//////////////////////////////////////////////////////////////////////////////

	 /**
     * Executes operations based on student submenu choice.
     *
     * @param choice the selected student menu option
     * @return true to continue, false to return to main menu
     * @throws SQLException if a database error occurs
     */
	
	static boolean departmentsProcess(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Students
		    	System.out.println("Chosen to Departments Info....\n");
		    	Departments_Info_Program.run();
	    		break;	    	

	    	case 2:
		    	System.out.println("Chosen to Manage Departments Location....\n");
		    	Department_Location_Program.run();
	    		break;	    	
	    		
	    	case 3:	
	            System.out.println("Exiting, Going back to Main Menu!");
	            System.out.println("\n------------------------\n");

	            Repeat = false;
	            break;
	    }
	    return Repeat;
	}	
	
	//////////////////////////////////////////////////////////////////////////////
	
	 /**
     * Handles the student submenu interactions.
     *
     * @throws SQLException if a database operation fails
     */
	static void Projects() throws SQLException {
		int choice = 0;
	    boolean Repeat = true;   
	    
    	do {
    		projectsMenuOptions(); // Display Projects-related options
    		choice = projectsReadInput(choice); // Gets user input
    		Repeat = projectsProcess(choice); // Process student menu input
    	}while(Repeat);
    	
	}

	//////////////////////////////////////////////////////////////////////////////

	static boolean projectsProcess(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Students
		    	System.out.println("Chosen to Manage Assignemnts....\n");
		    	Assignments.run();
	    		break;	    	

	    	case 2:
		    	System.out.println("Chosen to Manage Projects Info....\n");
		    	Projects_Info.run();
	    		break;	    	
	    		
	    	case 3:	
	            System.out.println("Exiting, Going back to Main Menu!");
	            System.out.println("\n------------------------\n");

	            Repeat = false;
	            break;
	    }
	    return Repeat;
	}	
	
//////////////////////////////////////////////////////////////////////////////

    /**
     * Handles the student submenu interactions.
     *
     * @throws SQLException if a database operation fails
     */
	static void Employees() throws SQLException {
		int choice = 0;
	    boolean Repeat = true;   
	    
    	do {
    		employeeMenuOptions(); // Display Employees-related options
    		choice = employeeReadInput(choice); // Gets user input
    		Repeat = employeesProcess(choice); // Process student menu input
    	}while(Repeat);
    	
	}
	
	//////////////////////////////////////////////////////////////////////////////

	static boolean employeesProcess(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Students
		    	System.out.println("Chosen to Manage Employee Info....\n");
		    	Employees_Info_Program.run();
	    		break;	    	

	    	case 2:
		    	System.out.println("Chosen to Manage Dependents Info....\n");
		    	Dependents_Info.run();
	    		break;	    	
	    		
	    	case 3:	
	            System.out.println("Exiting, Going back to Main Menu!");
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
        System.out.println("1. Manage Employees.");
        System.out.println("2. Manage Departments.");
        System.out.println("3. Manage Projects.");
        System.out.println("4. Exit");
	}
	
	//////////////////////////////////////////////////////////////////////////////

    /**
     * Displays employees menu options to the user.
     */
	static void employeeMenuOptions() {
		System.out.println("\n--- Employee MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. Manage Employee Info.");
        System.out.println("2. Manage Dependents Info.");
        System.out.println("3. Exit");
	}
	
	//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Displays departments menu options to the user.
     */
	static void departmentMenuOptions() {
        System.out.println("\n--- Department MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. Manage Departments Info.");
        System.out.println("2. Manage Departments Location.");
        System.out.println("3. Exit");
	}
	
	//////////////////////////////////////////////////////////////////////////////
    /**
     * Displays departments menu options to the user.
     */
	static void projectsMenuOptions() {
        System.out.println("\n--- Project MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. Manage Assignemnts.");
        System.out.println("2. Manage Projects Info.");
        System.out.println("3. Exit");
	}
	//////////////////////////////////////////////////////////////////////////////

    /**
     * Reads and validates the user's numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 4
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
     * Reads and validates the user's numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 3
     */
	static int employeeReadInput(int choice)
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
     * Reads and validates the user's numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 3
     */
	static int departmentsReadInput(int choice)
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
     * Reads and validates the user's numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 3
     */
	static int projectsReadInput(int choice)
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
}
