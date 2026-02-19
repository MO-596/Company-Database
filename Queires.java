package queries;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
/**
 * The {@code Queries} class allows users to execute
 * custom SELECT queries against the Company database.
 *
 * It dynamically prints column names and values,
 * making it reusable for any table.
 * 
 * For safety it only allows SELECT Queries to be done.
*/

public class Queires {

	public static void run() {
		// TODO Auto-generated method stub
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; 
	    
	    try(Connection conn = DriverManager.getConnection(url, user, password);
	    		Statement stmt = conn.createStatement()) 
			{			
            	System.out.println("\nConnected successfully.");
            	
            	boolean repeat = true;
            	
            	while(repeat) {
            		String query = readEntry("\nEnter a SELECT query (or type 'exit'): ");
            		if(query.equalsIgnoreCase("exit")) {
            			repeat = false;
            			break;
            		}
            		
            		// Only allows SELECT to happen
            		if(!query.trim().toUpperCase().startsWith("SELECT")) {
            			 System.out.println("Only SELECT queries are allowed.");
                         continue;
            		}
            		
            		executeQuery(conn, query);	
            	}
            	
                System.out.println("Exiting query console.");
	    	
			}catch (SQLException e) {
		    	System.out.println("Connection failed:");
		    	e.printStackTrace();
		    }
	}
//////////////////////////////////////////////////////////////////////////////
    /**
     * Executes a custom SELECT query and dynamically prints results.
     */
	static void executeQuery(Connection conn, String query) {
	       try (Statement stmt = conn.createStatement();
	              ResultSet rs = stmt.executeQuery(query)) {

	              ResultSetMetaData meta = rs.getMetaData();
	              int columnCount = meta.getColumnCount();

	              // Print column headers
	              for (int i = 1; i <= columnCount; i++) {
	                  System.out.print(meta.getColumnName(i) + "\t");
	              }
	              System.out.println("\n--------------------------------------------------");

	              // Print rows
	              while (rs.next()) {
	                  for (int i = 1; i <= columnCount; i++) {
	                      System.out.print(rs.getString(i) + "\t");
	                  }
	                  System.out.println();
	              }

	          } catch (SQLException e) {
	              System.out.println("Query failed:");
	              System.out.println(e.getMessage());
	          }
	}
	
//////////////////////////////////////////////////////////////////////////////

	
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
