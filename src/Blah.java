import test.TestSchedule;
import dao.CourseDAO;
import dao.DAOManager;
import dao.DAOType;
import entity.Course;


public class Blah {
	public static void main(String[] args) {
		/*
		CourseDAO blah = (CourseDAO) DAOManager.getDAO(DAOType.COURSE);
		Course course = new Course("English", 5);
		Course course2 = (Course) blah.getAll().get(3);
		Course course3 = (Course) blah.findByName("EnDglish");
		*/
		
		TestSchedule ts = new TestSchedule("name");
		ts.testMinCredits();
	}
}
