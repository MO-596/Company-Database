package tables;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Delete_Company_Tables is a utility program used to remove all tables and sequences 
 * related to the company database schema from an Oracle SQL database.
 * 
 * This program:
 * - Connects to the Oracle database using credentials entered via console.
 * - Drops all tables in the reverse order of their dependencies using CASCADE CONSTRAINTS.
 * - Drops all sequences associated with primary key generation.
 * 
 * It is typically run before recreating the schema from scratch or during cleanup procedures.
 */
public class Delete_Company_Tables {
    /**
     * The main method prompts the user for database credentials, connects to the database,
     * and executes SQL commands to drop all relevant tables and sequences.
     */
	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your URL]"; 
	    
		try(Connection conn = DriverManager.getConnection(url, user, password);
			    Statement stmt = conn.createStatement()) 
			{			
				System.out.println("\nConnected successfully.");
				
	            // Drop tables in dependency-safe reverse order
				String[] dropTables = {
						"DROP TABLE C_DEPENDENTS CASCADE CONSTRAINTS ",
						"DROP TABLE C_DEPARTMENT_LOCATION CASCADE CONSTRAINTS",
						"DROP TABLE C_PROJECT CASCADE CONSTRAINTS",
						"DROP TABLE C_ASSIGNMENTS  CASCADE CONSTRAINTS",
						"DROP TABLE C_DEPARTMENT CASCADE CONSTRAINTS",
						"DROP TABLE C_EMPLOYEE CASCADE CONSTRAINTS"};
				
				for(String drop : dropTables) {
					try {
						stmt.executeUpdate(drop);
						System.out.println("Executed: " + drop);
					}catch(SQLException e){
	                    System.out.println("Skipping (not found or failed): " + drop);

					}
				}
	            // Drop sequences used for ID generation
				String[] dropSeq = {
						"DROP SEQUENCE SEQ_DEPENDENTS_ID",
						"DROP SEQUENCE SEQ_PROJECT_ID",
						"DROP SEQUENCE SEQ_DEPT_ID",
						"DROP SEQUENCE SEQ_EMPLOYEE_ID"};
				
				for(String drop : dropSeq) {
					try {
						stmt.executeUpdate(drop);
						System.out.println("Executed: " + drop);
					}catch(SQLException e){
	                    System.out.println("Skipping (not found or failed): " + drop);

					}
				}
				
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
