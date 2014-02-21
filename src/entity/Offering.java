package entity;

public class Offering {
	
	private int id;
	private Course course;
	private String daysTimes;
	
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
	
	public void setDaysTimes(String daysTimes) {
		this.daysTimes = daysTimes;
	}
}
