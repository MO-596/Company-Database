package tables;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_Company_Tables {

	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your URL]"; 
	    
	    try(Connection conn = DriverManager.getConnection(url, user, password);
	    		Statement stmt = conn.createStatement()) 
			{
				System.out.println("\nConnected successfully.");

				// C_EMPLOYEE Table
			    String companyTableQuery  = "CREATE TABLE C_EMPLOYEE ("
			    		+ "EMPLOYEE_ID			NUMBER(8), "
			    		+ "FIRST_NAME			VARCHAR(25) NOT NULL, "
			    		+ "MIDDLE_INITIAL		CHAR, "
			    		+ "LAST_NAME			VARCHAR(25) NOT NULL, "
			    		+ "SSN					NUMBER(9)   NOT NULL, "
			    		+ "BIRTH_DATE			DATE 	    NOT NULL, "
			    		+ "CURRENT_ADDRESS		VARCHAR(40) NOT NULL, "
			    		+ "GENDER				CHAR, "
			    		+ "SALARY				NUMBER(9) NOT NULL, "
			    		+ "SUPERVISOR_ID 		NUMBER(8), "			
			    		+ "DEPT_ID 				NUMBER(1) NOT NULL ) ";
			    
			    String companyPrimaryKey = "ALTER TABLE C_EMPLOYEE "
			    		+ "ADD CONSTRAINT PK_C_EMPLOYEE PRIMARY KEY(EMPLOYEE_ID)";

			    String companyEmployeeSequence = "CREATE SEQUENCE SEQ_EMPLOYEE_ID START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE";
			    
			    stmt.executeUpdate(companyTableQuery); // same as commit
	            System.out.println("Table C_EMPLOYEE created successfully.");
				
			    stmt.executeUpdate(companyPrimaryKey);
	            System.out.println("Primary Key for C_EMPLOYEE successfully added.");
	            
	            stmt.executeUpdate(companyEmployeeSequence);
	            System.out.println("Sequence for C_EMPLOYEE successfully added.");

	        	//////////////////////////////////////////////////////////////////////////////
	            
				// C_DEPARTMENT Table
	            String departmentTableQuery = "CREATE TABLE C_DEPARTMENT ("
	            		+ "DEPT_NAME		VARCHAR(25)	 NOT NULL,"
	            		+ "DEPT_ID			NUMBER(10), "
	            		+ "MANAGER_ID 		NUMBER(8)	 NOT NULL,"
	            		+ "OFFICE_NUMBER	NUMBER(10)   NOT NULL, "
	            		+ "PHONE_NUMBER		VARCHAR(30)  NOT NULL ) ";
	            
	            String departmentPrimaryKey = "ALTER TABLE C_DEPARTMENT "
	            		+ "ADD CONSTRAINT PK_C_DEPARTMENT PRIMARY KEY(DEPT_ID)";	            
	            
			    String departmentSequence= "CREATE SEQUENCE SEQ_DEPT_ID START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE";
	            
			    stmt.executeUpdate(departmentTableQuery); // same as commit
	            System.out.println("Table C_DEPARTMENT created successfully.");
				
			    stmt.executeUpdate(departmentPrimaryKey);
	            System.out.println("Primary Key for C_DEPARTMENT successfully added.");
	            
	            stmt.executeUpdate(departmentSequence);
	            System.out.println("Sequence for C_DEPARTMENT successfully added.");
	            
	        	//////////////////////////////////////////////////////////////////////////////
     
				// C_ASSIGNMENTS Table
	            String assignmentsTableQuery = "CREATE TABLE C_ASSIGNMENTS ( "
	            		+ "EMPLOYEE_ID		NUMBER(8), "
	            		+ "PROJECT_ID 		NUMBER(8), "
	            		+ "HOURS_WORKED 	NUMBER(10), "
	            		+ "DEPT_ID 			NUMBER(10)  NOT NULL) ";
	           
	            String assignmentsPrimaryKey = "ALTER TABLE C_ASSIGNMENTS "
	            		+ "ADD CONSTRAINT PK_C_ASSIGNMENTS PRIMARY KEY(EMPLOYEE_ID, PROJECT_ID)";	   
	            
			    stmt.executeUpdate(assignmentsTableQuery); // same as commit
	            System.out.println("Table C_ASSIGNMENTS created successfully.");
				
			    stmt.executeUpdate(assignmentsPrimaryKey);
	            System.out.println("Primary Key for C_ASSIGNMENTS successfully added.");
 
	        	//////////////////////////////////////////////////////////////////////////////

				// C_PROJECT Table
	            String projectsTableQuery = "CREATE TABLE C_PROJECT  ( "
	            		+ "PROJECT_NAME 		VARCHAR(25) NOT NULL, "
	            		+ "PROJECT_ID 			NUMBER(10), "
	            		+ "PROJECT_LOCATION 	VARCHAR(30) NOT NULL, "
	            		+ "PROJECT_DEPT_ID		NUMBER(10)  NOT NULL ) ";
	            
	            String projectsPrimaryKey = "ALTER TABLE C_PROJECT "
	            		+ "ADD CONSTRAINT PK_C_PROJECT PRIMARY KEY(PROJECT_ID)";	   
	            
			    String projectsSequence= "CREATE SEQUENCE SEQ_PROJECT_ID START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE";
	            
			    stmt.executeUpdate(projectsTableQuery); // same as commit
	            System.out.println("Table C_PROJECT created successfully.");
				
			    stmt.executeUpdate(projectsPrimaryKey);
	            System.out.println("Primary Key for C_PROJECT successfully added.");
	            
	            stmt.executeUpdate(projectsSequence);
	            System.out.println("Sequence for C_PROJECT successfully added.");
	            
	        	//////////////////////////////////////////////////////////////////////////////

				// C_DEPARTMENT_LOCATION Table
	            String deptLocationTableQuery = "CREATE TABLE C_DEPARTMENT_LOCATION ( "
	            		+ "DEPT_ID	NUMBER(10), "
	            		+ "LOCATION VARCHAR(30) ) ";
	            
	            String deptLocationPrimaryKey = "ALTER TABLE C_DEPARTMENT_LOCATION "
	            		+ "ADD CONSTRAINT PK_C_DEPARTMENT_LOCATION PRIMARY KEY(DEPT_ID, LOCATION)";	   
	                        
			    stmt.executeUpdate(deptLocationTableQuery); // same as commit
	            System.out.println("Table C_DEPARTMENT_LOCATION created successfully.");
				
			    stmt.executeUpdate(deptLocationPrimaryKey);
	            System.out.println("Primary Key for C_DEPARTMENT_LOCATION successfully added.");

	        	//////////////////////////////////////////////////////////////////////////////

				// C_DEPENDENTS Table
	            String dependentsTableQuery = "CREATE TABLE C_DEPENDENTS  ( "
	            		+ "EMPLOYEE_ID		NUMBER(8) 	NOT NULL, "
	            		+ "DEPENDENTS_ID	NUMBER(8), "
	            		+ "DEPENDENTS_NAME 	VARCHAR(30) NOT NULL, "
	            		+ "GENDER			CHAR		NOT NULL, "
			    		+ "BIRTH_DATE		DATE 	    NOT NULL, "
	            		+ "RELATIONSHIP		VARCHAR(30) NOT NULL ) ";
	            
			    stmt.executeUpdate(dependentsTableQuery); // same as commit
	            System.out.println("Table C_DEPENDENTS created successfully.");
	            
	            String dependentsPrimaryKey = "ALTER TABLE C_DEPENDENTS "
	            		+ "ADD CONSTRAINT PK_C_DEPENDENTS PRIMARY KEY(DEPENDENTS_ID)";	  
	          
			    stmt.executeUpdate(dependentsPrimaryKey);
	            System.out.println("Primary Key for C_DEPENDENTS successfully added.");
	            
	            String dependentsSequence = "CREATE SEQUENCE SEQ_DEPENDENTS_ID START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE";
	            
	            stmt.executeUpdate(dependentsSequence);
	            System.out.println("Sequence for C_DEPENDENTS successfully added.");
	            
	        	//////////////////////////////////////////////////////////////////////////////
	            //ADDING UNIQUE CONSTRAINT
	            stmt.executeUpdate("ALTER TABLE C_EMPLOYEE ADD CONSTRAINT UNIQ_C_EMPLOYEE_SSN UNIQUE(SSN)");
	            stmt.executeUpdate("ALTER TABLE C_DEPARTMENT ADD CONSTRAINT UNIQ_C_DEPARTMENT_NAME UNIQUE(DEPT_NAME)");
	            stmt.executeUpdate("ALTER TABLE C_PROJECT ADD CONSTRAINT UNIQ_C_PROJECT_NAME UNIQUE(PROJECT_NAME)");
	            System.out.println("Unique constraints added successfully.");

	        	//////////////////////////////////////////////////////////////////////////////
	            //ADDING CONSTRAINTS FOR COMPANY DATABASE
	            //C_EMPLOYEE
	            //Ensures that the SUPERVISOR_ID in C_EMPLOYEE refers to a valid EMPLOYEE_ID in the same table (self-referencing FK).
	            stmt.executeUpdate("ALTER TABLE C_EMPLOYEE ADD CONSTRAINT FK_C_EMPLOYEE_SUPERVISOR_ID "
	            		+ "FOREIGN KEY(SUPERVISOR_ID) "
	            		+ "REFERENCES C_EMPLOYEE(EMPLOYEE_ID)");

	            //Ensures that the DEPT_ID in C_EMPLOYEE refers to a valid department in C_DEPARTMENT.
	            stmt.executeUpdate("ALTER TABLE C_EMPLOYEE ADD CONSTRAINT FK_C_EMPLOYEE_DEPT_ID "
	            		+ "FOREIGN KEY(DEPT_ID) "
	            		+ "REFERENCES C_DEPARTMENT(DEPT_ID)");
	            
	            //C_DEPARTMENT
	            //Ensures that the MANAGER_ID in C_DEPARTMENT refers to an existing EMPLOYEE_ID in C_EMPLOYEE.
	            stmt.executeUpdate("ALTER TABLE C_DEPARTMENT ADD CONSTRAINT FK_C_DEPARTMENT_MANAGER_ID "
	            		+ "FOREIGN KEY(MANAGER_ID) "
	            		+ "REFERENCES C_EMPLOYEE(EMPLOYEE_ID)");
	            
	            //C_ASSIGNMENTS
	            //Ensures that EMPLOYEE_ID in C_ASSIGNMENTS refers to a valid employee in C_EMPLOYEE.
	            stmt.executeUpdate("ALTER TABLE C_ASSIGNMENTS ADD CONSTRAINT FK_C_ASSIGNMENTS_EMPLOYEE_ID "
	            		+ "FOREIGN KEY(EMPLOYEE_ID) "
	            		+ "REFERENCES C_EMPLOYEE(EMPLOYEE_ID)");

	            // Ensures that PROJECT_ID in C_ASSIGNMENTS refers to a valid project in C_PROJECT.
	            stmt.executeUpdate("ALTER TABLE C_ASSIGNMENTS ADD CONSTRAINT FK_C_ASSIGNMENTS_PROJECT_ID "
	            		+ "FOREIGN KEY(PROJECT_ID ) "
	            		+ "REFERENCES C_PROJECT(PROJECT_ID)");
	            
	            //Ensures that DEPT_ID in C_ASSIGNMENTS refers to a valid department in C_DEPARTMENT.
	            stmt.executeUpdate("ALTER TABLE C_ASSIGNMENTS ADD CONSTRAINT FK_C_ASSIGNMENTS_DEPT_ID "
	            		+ "FOREIGN KEY(DEPT_ID) "
	            		+ "REFERENCES C_DEPARTMENT(DEPT_ID)");
	            
	            //C_PROJECT
	            // Ensures that the combination of PROJECT_DEPT_ID and PROJECT_LOCATION in C_PROJECT 
	            // refers to a valid (DEPT_ID, LOCATION) pair in C_DEPARTMENT_LOCATION.
	            stmt.executeUpdate("ALTER TABLE C_PROJECT ADD CONSTRAINT FK_C_PROJECT_PROJECT_LOCATION_ID "
	            		+ "FOREIGN KEY(PROJECT_DEPT_ID, PROJECT_LOCATION) "
	            		+ "REFERENCES C_DEPARTMENT_LOCATION(DEPT_ID, LOCATION)");
	            
	            //C_DEPENDENTS
	            //Ensures that EMPLOYEE_ID in C_DEPENDENTS refers to a valid employee in C_EMPLOYEE.
	            stmt.executeUpdate("ALTER TABLE C_DEPENDENTS ADD CONSTRAINT FK_C_DEPENDENTS_EMPLOYEE_ID "
	            		+ "FOREIGN KEY(EMPLOYEE_ID) "
	            		+ "REFERENCES C_EMPLOYEE(EMPLOYEE_ID)");
	            
	            System.out.println("Foreign key constraints added successfully.");	         
	            
	            //ALTER TABLE C_EMPLOYEE MODIFY FIRST_NAME NOT NULL; 
	            
			}catch (SQLException e) {
		    	System.out.println("Connection failed:");
		    	e.printStackTrace();
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
}
