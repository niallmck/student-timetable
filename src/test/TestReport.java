package test;

import junit.framework.TestCase;

import java.util.List;
import java.util.Collection;

import timetable.Report;
import dao.CourseDAO;
import dao.OfferingDAO;
import dao.ScheduleDAO;
import entity.Course;
import entity.Offering;
import entity.Schedule;

public class TestReport extends TestCase {

	public TestReport(String name) { 
		super(name); 
	}
	
	public void testEmptyReport() throws Exception {
		ScheduleDAO.deleteAll();
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		assertEquals("Number of scheduled offerings: 0\n", buffer.toString());
	}
	
	public void testReport() throws Exception {
		ScheduleDAO.deleteAll();
		Course cs101 = CourseDAO.create(new Course("CS101", 3));
		CourseDAO.update(cs101);
		Offering off1 = OfferingDAO.create(new Offering(cs101, "M10"));
		OfferingDAO.update(off1);
		Offering off2 = OfferingDAO.create(new Offering(cs101, "T9"));
		OfferingDAO.update(off2);
		Schedule s = ScheduleDAO.create(new Schedule("Bob"));
		s.add(off1);
		s.add(off2);
		ScheduleDAO.update(s);
		Schedule s2 = ScheduleDAO.create(new Schedule("Alice"));
		s2.add(off1);
		ScheduleDAO.update(s2);
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		String result = buffer.toString();
		String valid1 = "CS101 M10\n\tAlice\n\tBob\n" + "CS101 T9\n\tBob\n" + "Number of scheduled offerings: 2\n";
		String valid2 = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n" + "Number of scheduled offerings: 2\n";
		assertTrue(result.equals(valid1) || result.equals(valid2));
	}
}
