package entity;

import java.util.ArrayList;

/**
 * POJO Representing a Schedule.
 * @author Niall
 *
 */

public class Schedule {
	
	private String name;
	private int credits = 0;
	public static final int MIN_CREDITS = 12;
	public static final int MAX_CREDITS = 18;
	private boolean permission = false;
	private ArrayList<Offering> schedule = new ArrayList<Offering>();
	
	public Schedule(String name){
		this.name = name;
	}
	
	public void add(Offering offering){
		credits += offering.getCourse().getCredits();
		schedule.add(offering);
	}
	
	public ArrayList<Offering> getSchedule(){
		return schedule;
	}

	public String getName() {
		return name;
	}
	

	public int getCredits() {
		return credits;
	}

	public boolean getPermission() {
		return permission;
	}
	
	public void authorizeOverload(boolean authorized) {
		permission = authorized;
	}

}
