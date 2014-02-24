package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.ConnectionManager;
import entity.Course;

public class CourseDAO {
	
	/**
	 * Add a new Course to the Database.
	 * @param course - The Course object to save.
	 * @return - The saved Course object.
	 */
	public static Course create(Course course){
		delete(course);
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO course (name, credits) VALUES(?, ?)");
			ps.setString(1, course.getName());
			ps.setInt(2, course.getCredits());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return course;
	}
	
	/**
	 * Get all the Courses stored in the database.
	 * @return - An ArrayList of Course objects.
	 */
	public static ArrayList<Course> getAll(){
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM course"); 
            while (rs.next()) {
            	courses.add(new Course(rs.getString("name"), rs.getInt("credits")));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return courses;
	}
	
	/**
	 * Find a Course by its name.
	 * @param name - The string name of the sought course.
	 * @return - The Course object if found or null if not.
	 */
	public static Course findByName(String name){
		Course course = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM course WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				course = new Course(rs.getString("name"), rs.getInt("credits"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return course;
	}
	
	/**
	 * Commit changes in a Course object to the Database.
	 * @param course - The Course object to update.
	 */
	public static void update(Course course){
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE course SET credits = ? WHERE name = ?");
			ps.setInt(1, course.getCredits());
			ps.setString(2, course.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}
	
	/**
	 * Delete a given Course from the Database.
	 * @param course - The Course object to delete.
	 */
	public static void delete(Course course){
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE from course WHERE name = ?");
			ps.setString(1, course.getName());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}

}
