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
import entity.Offering;

public class OfferingDAO {
	
	/**
	 * Add a new offering to the Database.
	 * @param offering - The Offering object to save.
	 * @return - The saved Offering object.
	 */
	public static Offering create(Offering offering) {
		try {
			Connection conn = ConnectionManager.getConnection();
			String query = "INSERT INTO offering (name, daystimes) VALUES(?, ?)";
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, offering.getCourse().getName());
			ps.setString(2, offering.getDaysTimes());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                offering.setId(last_inserted_id);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return offering;
	}

	/**
	 * Get all the Offerings stored in the Database.
	 * @return - An ArrayList of Offering objects.
	 */
	public static List<Offering> getAll() {
		List<Offering> offerings = new ArrayList<Offering>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM offering"); 
            while (rs.next()) {
            	Course course = (Course) CourseDAO.findByName(rs.getString("name"));
            	offerings.add(new Offering(rs.getInt("id"), course, rs.getString("daystimes")));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return offerings;
	}

	/**
	 * Find an Offering by its name.
	 * @param name - String name of the sought Offering.
	 * @return - The Offering object if found, null if not.
	 */
	public static Offering findByName(String name) {
		Offering offering = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM offering WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				Course course = (Course) CourseDAO.findByName(name);
				offering = new Offering(rs.getInt("id"), course, rs.getString("daystimes"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return offering;
	}
	
	/**
	 * Find an Offering by its ID.
	 * @param id - The id of the sought Offering.
	 * @return - The Offering object if found, null if not.
	 */
	public static Offering findById(int id) {
		Offering offering = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM offering WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				String courseName = rs.getString("name");
				int offeringId = rs.getInt("id");
				String daystimes =  rs.getString("daystimes");
				Course course = (Course) CourseDAO.findByName(courseName);
				offering = new Offering(offeringId, course, daystimes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return offering;
	}

	/**
	 * Commit changes in an Offering object to the Database.
	 * @param offering - The updated Offering object.
	 */
	public static void update(Offering offering) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE offering SET name = ? and daytimes = ? WHERE id = ?");
			ps.setString(1, offering.getCourse().getName());
			ps.setString(2, offering.getDaysTimes());
			ps.setInt(3, offering.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}

	/**
	 * Delete a given Offering from the Database.
	 * @param offering - The Offering object to delete.
	 */
	public static void delete(Offering offering) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE from offering WHERE id = ?");
			ps.setInt(1, offering.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}



}
