
package app.studentManagmentProject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String DB_URL =
    	    "jdbc:sqlserver://localhost:1433;"
    	    	    + "databaseName=StudentManagementDB;"
    	    	    + "integratedSecurity=true;"
    	    	    + "encrypt=true;"
    	    	    + "trustServerCertificate=true;";

    public static Connection getConnection() throws SQLException {
    	
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver was not found.", e);
        }
        return DriverManager.getConnection(DB_URL);
    }
    
    
}
