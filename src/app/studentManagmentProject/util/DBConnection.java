package app.studentManagmentProject.util;

import java.sql.Connection;

public class DBConnection {
	private static final String DB_URL = 
			"jdbc: ;"
			+ "databasName= ;"
			+"integraedSecurity=true;"
			+"encrypt=false";

	public static Connection getConnection() throws SQLException {
		
		Connection connection = DriverManager.getConnection(DB_URL);
		return connection;
	}
}
