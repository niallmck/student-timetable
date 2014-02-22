package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import database.ConnectionManager;
import entity.Course;
import entity.Offering;
import entity.Schedule;

public class ScheduleDAO implements GenericDAO{
	
	
	public static void deleteAll() throws Exception {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE from schedule");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}

	@Override
	public List<Object> getAll() {
		List<Object> schedule = new ArrayList<Object>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("SELECT DISTINCT name FROM schedule"); 
            while (rs.next()) {
            	schedule.add(findByName(rs.getString("name")));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return schedule;
	}

	@Override
	public Object findByName(String name) {
		Schedule schedule= null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM schedule WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			schedule = new Schedule(name);
			while (rs.next()){
				int offeringId = rs.getInt("offeringid");
				OfferingDAO dao = (OfferingDAO) DAOManager.getDAO(DAOType.OFFERING);
				Offering offering = (Offering) dao.findById(offeringId);
				schedule.add(offering);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		return schedule;
	}

	@Override
	public void update(Object object) {
		Schedule schedule = (Schedule) object;
		ArrayList<Offering> scheduleList = schedule.getSchedule();
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE schedule SET name = ? and offeringid = ? WHERE id = ?");

			for (int i = 0; i < scheduleList.size(); i++){
				Offering offering = (Offering) scheduleList.get(i);
								
				ps.setString(1, schedule.getName());
				ps.setInt(2, offering.getId());
				ps.setInt(3, schedule.getId());
				
				ps.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		
	}

	@Override
	public void delete(Object object) {
		Schedule schedule = (Schedule) object;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM schedule WHERE id = ?");
			ps.setInt(1, schedule.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
	}

	@Override
	public void create(Object object) {
		Schedule schedule = (Schedule) object;
		ArrayList<Offering> scheduleList = schedule.getSchedule();
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO schedule (name, offeringid)  VALUES(?,?)");

			for (int i = 0; i < scheduleList.size(); i++){
				Offering offering = (Offering) scheduleList.get(i);
								
				ps.setString(1, schedule.getName());
				ps.setInt(2, offering.getId());
				
				ps.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			ConnectionManager.closeConnection();
		}
		
	}

}
