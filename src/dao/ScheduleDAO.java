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

	public static Schedule findByName(String name) {
		Schedule schedule = null;
		ArrayList<Integer> offeringIds = new ArrayList<Integer>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM schedule WHERE name = ?");
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
		
		// We then use the OfferingDAO to retrieve the Offering objects associated with the schedule.
		for (int i = 0; i < offeringIds.size(); i++){
			Offering offering = (Offering) OfferingDAO.findById(offeringIds.get(i));
			schedule.add(offering);
		}
		
		return schedule;
	}

	public static void update(Schedule schedule) {
		ArrayList<Offering> scheduleList = schedule.getSchedule();

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM schedule WHERE name = ?");
			ps.setString(1, schedule.getName());
			ps.execute();

			for (int i = 0; i < scheduleList.size(); i++) {
				Offering offering = (Offering) scheduleList.get(i);
				
				PreparedStatement ps1 = conn.prepareStatement("INSERT INTO schedule (name, offeringid)  VALUES(?, ?)");

				ps1.setString(1, schedule.getName());
				ps1.setInt(2, offering.getId());
				
				ps1.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

	}

	public static void delete(Object object) {
		Schedule schedule = (Schedule) object;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("DELETE FROM schedule WHERE name = ?");
			ps.setString(1, schedule.getName());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
	}

	public static Schedule create(Schedule schedule) {
		ArrayList<Offering> scheduleList = schedule.getSchedule();

		try {
			Connection conn = ConnectionManager.getConnection();
			
			//if (scheduleList.isEmpty()){
				String query = "INSERT INTO schedule (name)  VALUES(?)";
				PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, schedule.getName());
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys();
	            if(rs.next())
	            {
	                int last_inserted_id = rs.getInt(1);
	                schedule.setId(last_inserted_id);
	            }
				/*
			}
			else{
				PreparedStatement ps = conn.prepareStatement("INSERT INTO schedule (name, offeringid)  VALUES(?,?)");
	
				for (int i = 0; i < scheduleList.size(); i++) {
					Offering offering = (Offering) scheduleList.get(i);
	
					ps.setString(1, schedule.getName());
					ps.setInt(2, offering.getId());

					ps.execute();
				}
			}*/
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
		return schedule;

	}

}
