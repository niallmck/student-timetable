package entity;

public class Offering {
	
	private int id;
	private Course course;
	private String dayTime;
	
	public Offering(int id, Course course, String dayTime) {
		this.id = id;
		this.course = course;
		this.dayTime = dayTime;
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
	
	public String getDayTime() {
		return dayTime;
	}
	
	public void setDaysTime(String dayTime) {
		this.dayTime = dayTime;
	}
}
