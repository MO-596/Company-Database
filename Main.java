package main;
import java.io.IOException;
import java.sql.SQLException;

import departments.Departments_Info_Program;
import departments.Department_Location_Program;
import employees.Dependents_Info;
import employees.Employees_Info_Program;
import projects.Assignments;
import projects.Projects_Info;
import queries.Queires;


 /**
 * The {@code Main} class serves as the entry point of the Company Management System.
 * It provides a command-line interface for managing employee, departments, and project/assignments data.
 * <p>
 * This program interacts with an Oracle database to perform operations such as:
 * <ul>
 *   <li>Adding and retrieving employee records</li>
 *   <li>Viewing or creating department records</li>
 *   <li>Managing projects and employee assignments</li>
 * </ul>
 */
 

public class Main {
    /**
     * Entry point for the program. Displays the main menu and allows navigation to submenus.
     *
     * @param args Command-line arguments (not used).
     * @throws SQLException if any SQL operation fails.
     */
	public static void main(String[] args) throws SQLException {
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
	    		System.out.println("Chosen to write a custom query....\n");
	    		Queries();
	    		break;

	    	case 5:	
	            System.out.println("Exiting, Thank you for you for using the app!");
	            System.out.println("\n------------------------\n");

	            Repeat = false;
	            break;
	    }
	    return Repeat;
	}
	
	//////////////////////////////////////////////////////////////////////////////

    /**
     * Displays and processes the Departments submenu.
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
     * Executes operations based on the Departments submenu selection.
     *
     * @param choice the department menu option
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
     * Displays and processes the Projects submenu.
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
    /**
     * Executes operations based on the Projects submenu selection.
     *
     * @param choice the project menu option
     * @return true to continue, false to return to main menu
     * @throws SQLException if a database error occurs
     */
	static boolean projectsProcess(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: 
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
     * Displays and processes the Employees submenu.
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
    /**
     * Executes operations based on the Employees submenu selection.
     *
     * @param choice the employee menu option
     * @return true to continue, false to return to main menu
     * @throws SQLException if a database error occurs
     */
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
     * Displays and processes the Queries submenu.
     *
     * @throws SQLException if a database operation fails
     */
	static void Queries() throws SQLException {
		int choice = 0;
	    boolean Repeat = true;   
	    
    	do {
    		queriesMenuOptions(); // Display Employees-related options
    		choice = queriesReadInput(choice); // Gets user input
    		Repeat = queryProcess(choice); // Process student menu input
    	}while(Repeat);
    	
	}
	
	//////////////////////////////////////////////////////////////////////////////
    /**
     * Executes operations based on the queries submenu selection.
     *
     * @param choice the queries menu option
     * @return true to continue, false to return to main menu
     * @throws SQLException if a database error occurs
     */
	static boolean queryProcess(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Students
		    	System.out.println("Chosen to write a custom query....\n");
		    	Queires.run();
	    		break;	    	
	
	    		
	    	case 2:	
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
        System.out.println("4. Write Custom Queries.");
        System.out.println("5. Exit");
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
     * Displays queries menu options to the user.
     */
	static void queriesMenuOptions() {
        System.out.println("\n--- Query MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. Write a custom query.");
        System.out.println("2. Exit.");
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
                if (val >= 1 && val <= 5) {
                	return val;
                }
            }     		
            System.out.println("Invalid input. Please enter a number between 1 to 4");
		}
	}	
	
	//////////////////////////////////////////////////////////////////////////////

    /**
     * Reads and validates the employee submenu numeric input.
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
     * Reads and validates the departments submenu numeric menu input.
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
     * Reads and validates the projects submenu numeric menu input.
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
     * Reads and validates the queries submenu numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 2
     */
	static int queriesReadInput(int choice)
	{
		while(true)
		{
			String input = readEntry("Enter choice: ");
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= 1 && val <= 2) {
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
