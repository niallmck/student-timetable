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
		
	public static ArrayList<Course> getAll(){
		ArrayList<Course> courses = new ArrayList<Course>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM course"); 
            while (rs.next()) {
            	courses.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("credits")));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return courses;
	}
	
	public static Course findByName(String name){
		Course course = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM course WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				course = new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("credits"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return course;
	}
	
	public static void update(Course course){
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE course SET name = ? and credits = ? WHERE id = ?");
			ps.setString(1, course.getName());
			ps.setInt(2, course.getCredits());
			ps.setInt(3, course.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}
	
	public static void delete(Course course){
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE from course WHERE id = ?");
			ps.setInt(1, course.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}
	
	public static Course create(Course course){
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


}
