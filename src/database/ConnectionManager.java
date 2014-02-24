package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static Connection conn = null; // This is where our one and only connection will be stored.
	private static String url = "jdbc:mysql://localhost:3306/studenttimetable";
	private static String username = "root";
	private static String password = "dha7fZn7aQ8ssw6c24zFWMhS";
	
	/**
	 * Get a connection to the Database.
	 * @return - A Connection object, connected to the given database, or null if connection fails.
	 */
	public static Connection getConnection(){
		try {
			if (conn == null || conn.isClosed()){
				conn = DriverManager.getConnection(url, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * Close the one and only connection if it is open.
	 */
	public static void closeConnection(){
		try {
			if (conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
