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
		ArrayList<Schedule> schedule = new ArrayList<Schedule>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT DISTINCT name FROM schedule");
			while (rs.next()) {
				schedule.add(findByName(rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
		return schedule;
	}

	public static Schedule findByName(String name) {
		Schedule schedule = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM schedule WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			schedule = new Schedule(name);
			while (rs.next()) {
				int offeringId = rs.getInt("offeringid");
				Offering offering = (Offering) OfferingDAO.findById(offeringId);
				schedule.add(offering);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
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
			
			if (scheduleList.isEmpty()){
				PreparedStatement ps = conn.prepareStatement("INSERT INTO schedule (name)  VALUES(?)");
				ps.setString(1, schedule.getName());
				ps.executeUpdate();
				
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

}
