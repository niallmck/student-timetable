package test;

import java.util.Collection;
import java.util.List;

import dao.CourseDAO;
import dao.OfferingDAO;
import dao.ScheduleDAO;
import timetable.ScheduleManager;
import junit.framework.TestCase;
import entity.Course;
import entity.Offering;
import entity.Schedule;

public class TestSchedule extends TestCase{
	
	public TestSchedule(String name) {
		super(name);
	}

	public void testMinCredits() {
		Schedule schedule = new Schedule("name");
		Collection<String> analysis = ScheduleManager.analysis(schedule);
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
	}
	
	public void testJustEnoughCredits() {
		Course cs110 = new Course("CS110", 11);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		List<String> analysis = ScheduleManager.analysis(schedule);
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 12);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.add(th11);
		analysis = ScheduleManager.analysis(schedule);
		assertEquals(0, analysis.size());
	}

	public void testMaxCredits() {
		Course cs110 = new Course("CS110", 20);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		List<String> analysis = ScheduleManager.analysis(schedule);
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		schedule.authorizeOverload(true);
		analysis = ScheduleManager.analysis(schedule);
		assertEquals(0, analysis.size());
	}

	public void testJustBelowMax() {
		Course cs110 = new Course("CS110", 19);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		List<String> analysis = ScheduleManager.analysis(schedule);
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 18);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.add(th11);
		analysis = ScheduleManager.analysis(schedule);
		assertEquals(0, analysis.size());
	}

	public void testDupCourses() {
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Offering th11 = new Offering(1, cs110, "T11,H11");
		Schedule schedule = new Schedule("name");
		schedule.add(mwf10);
		schedule.add(th11);
		List<String> analysis = ScheduleManager.analysis(schedule);
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Same course twice - CS110"));
	}
	
	public void testOverlap() {
		Schedule schedule = new Schedule("name");
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		schedule.add(mwf10);
		Course cs101 = new Course("CS101", 6);
		Offering mixed = new Offering(1, cs101, "M10,W11,F11");
		schedule.add(mixed);
		List<String> analysis = ScheduleManager.analysis(schedule);
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		Course cs102 = new Course("CS102", 1);
		Offering mixed2 = new Offering(1, cs102, "M9,W10,F11");
		schedule.add(mixed2);
		analysis = ScheduleManager.analysis(schedule);
		assertEquals(3, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		assertTrue(analysis.contains("Course overlap - W10"));
		assertTrue(analysis.contains("Course overlap - F11"));
	}

	public void testCourseCreate() throws Exception {
		Course c = CourseDAO.create(new Course("CS202", 1));
		Course c2 = CourseDAO.findByName("CS202");
		assertEquals("CS202", c2.getName());
		Course c3 = CourseDAO.findByName("Nonexistent");
		assertNull(c3);
	}

	public void testOfferingCreate() throws Exception {
		Course c = CourseDAO.create(new Course("CS202", 2));
		Offering offering = OfferingDAO.create(new Offering(c, "M10"));
		assertNotNull(offering);
	}

	public void testPersistentSchedule() throws Exception {
		Schedule s = ScheduleDAO.create(new Schedule("Bob"));
		assertNotNull(s);
	}

	public void testScheduleUpdate() throws Exception {
		Course cs101 = CourseDAO.create(new Course("CS101", 3));
		CourseDAO.update(cs101);
		Offering off1 = OfferingDAO.create(new Offering(cs101, "M10"));
		OfferingDAO.update(off1);
		Offering off2 =  OfferingDAO.create(new Offering(cs101, "T9"));
		OfferingDAO.update(off2);
		Schedule s = ScheduleDAO.create(new Schedule("Bob"));
		s.add(off1);
		s.add(off2);
		ScheduleDAO.update(s);
		Schedule s2 = ScheduleDAO.create(new Schedule("Alice"));
		s2.add(off1);
		ScheduleDAO.update(s2);
		Schedule s3 = ScheduleDAO.findByName("Bob");
		assertEquals(2, s3.getSchedule().size());
		Schedule s4 = ScheduleDAO.findByName("Alice");
		assertEquals(1, s4.getSchedule().size());
	}

}
