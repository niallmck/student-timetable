import dao.CourseDAO;
import dao.DAOManager;
import dao.DAOType;
import entity.Course;


public class Blah {
	public static void main(String[] args) {
		CourseDAO blah = (CourseDAO) DAOManager.getDAO(DAOType.COURSE);
		Course course = new Course("English", 5);
		blah.create(course);
		
		
	}
}
