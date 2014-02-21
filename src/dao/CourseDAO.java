package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionManager;
import entity.Course;

public class CourseDAO implements GenericDAO {
		
	public List<Object> getAll(){
		List<Object> courses = new ArrayList<Object>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from course"); 
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
	
	public void update(Course course){
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement s = conn.prepareStatement("UPDATE course SET name = '?' and credits = ? WHERE id = ?");
			s.setString(1, course.getName());
			s.setInt(2, course.getCredits());
			s.setInt(3, course.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}
	
	public void delete(Course course){
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement s = conn.prepareStatement("DELETE from course WHERE id = ?");
			s.setInt(1, course.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}
	
	public void create(Course course){
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement s = conn.prepareStatement("INSERT INTO course (name, credits) VALUES(?, ?)");
			s.setString(1, course.getName());
			s.setInt(2, course.getCredits());
			s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}

}
