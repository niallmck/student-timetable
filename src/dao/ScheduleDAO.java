package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.ConnectionManager;
import entity.Offering;
import entity.Schedule;

public class ScheduleDAO {
	
	/**
	 * Add a new Schedule to the Database.
	 * @param schedule - The Schedule object to save.
	 * @return - The saved Schedule object.
	 */
	public static Schedule create(Schedule schedule) {
		ArrayList<Offering> scheduleList = schedule.getSchedule();
		
		delete(schedule);
		try {
			Connection conn = ConnectionManager.getConnection();
			
			// Check if a scheduleList was intialised when the Schedule object was created.
			// If so, we also want to store that scheduleList.
			if (scheduleList.isEmpty()){
				PreparedStatement ps = conn.prepareStatement("INSERT INTO schedule (name)  VALUES(?)");
				ps.setString(1, schedule.getName());
				ps.execute();
			}
			else{
				PreparedStatement ps = conn.prepareStatement("INSERT INTO schedule (name, offeringid)  VALUES(?,?)");
	
				for (int i = 0; i < scheduleList.size(); i++) {
					Offering offering = (Offering) scheduleList.get(i);
	
					ps.setString(1, schedule.getName());
					ps.setInt(2, offering.getId());
					ps.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
		return schedule;
	}

	/**
	 * Get all Schedules stored in the Database.
	 * @return - An ArrayList of Schedule objects.
	 */
	public static ArrayList<Schedule> getAll() {
		ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
		ArrayList<String> scheduleNames = new ArrayList<String>();
		
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT DISTINCT name FROM schedule");
			while (rs.next()) {
				scheduleNames.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
		
		// Use the findByName method to populate the ScheduleList
		for (int i = 0; i < scheduleNames.size(); i++){
			scheduleList.add(findByName(scheduleNames.get(i)));
		}
		
		
		return scheduleList;
	}

	/**
	 * Find a Schedule by its name.
	 * @param name - String name of the sought Schedule.
	 * @return - The given Schedule object if found, null if not.
	 */
	public static Schedule findByName(String name) {
		Schedule schedule = null;
		ArrayList<Integer> offeringIds = new ArrayList<Integer>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM schedule WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			schedule = new Schedule(name);

			while (rs.next()) {
				offeringIds.add(rs.getInt("offeringid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
		
		// Use the OfferingDAO to retrieve the Offering objects associated with the schedule.
		for (int i = 0; i < offeringIds.size(); i++){
			Offering offering = (Offering) OfferingDAO.findById(offeringIds.get(i));
			schedule.add(offering);
		}
		
		return schedule;
	}

	/**
	 * Commit changes in a Schedule object to the database.
	 * @param schedule - The updated Schedule object.
	 */
	public static void update(Schedule schedule) {
		ArrayList<Offering> scheduleList = schedule.getSchedule();
		
		delete(schedule);
		try {
			Connection conn = ConnectionManager.getConnection();

			for (int i = 0; i < scheduleList.size(); i++) {
				Offering offering = (Offering) scheduleList.get(i);
				
				PreparedStatement ps = conn.prepareStatement("INSERT INTO schedule (name, offeringid)  VALUES(?, ?)");

				ps.setString(1, schedule.getName());
				ps.setInt(2, offering.getId());
				
				ps.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

	}

	/**
	 * Delete a given schedule from the Database.
	 * @param schedule - The Schedule object to delete.
	 */
	public static void delete(Schedule schedule) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM schedule WHERE name = ?");
			ps.setString(1, schedule.getName());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
	}
	
	/**
	 * Delete all Schedules from the Database.
	 */
	public static void deleteAll() {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("DELETE from schedule");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
	}

}
