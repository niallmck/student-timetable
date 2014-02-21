package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static Connection conn = null;
	private static String url = "jdbc:mysql://localhost:3306/studenttimetable";
	private static String username = "root";
	private static String password = "dha7fZn7aQ8ssw6c24zFWMhS";
	
	public ConnectionManager(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		try {
			if (conn == null || conn.isClosed()){
				conn = DriverManager.getConnection(url, username, password);
				System.out.println("Connected to Database.");
				return conn;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConnection(){
		try {
			if (conn != null && !conn.isClosed()){
				conn.close();
				System.out.println("Closed Database Connection");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
