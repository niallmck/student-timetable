package entity;

public class Offering {
	
	private int id;
	private Course course;
	private String daysTimes;
	
	public Offering(int id, Course course, String daysTimes) {
		this.id = id;
		this.course = course;
		this.daysTimes = daysTimes;
	}

	public Offering(Course course, String daysTimes) {
		this.course = course;
		this.daysTimes = daysTimes;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public String getDaysTimes() {
		return daysTimes;
	}
	
	public void setDaysTimes(String dayTime) {
		this.daysTimes = dayTime;
	}
}
