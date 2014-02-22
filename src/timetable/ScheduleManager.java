package timetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import entity.Course;
import entity.Offering;
import entity.Schedule;

public class ScheduleManager {
	
	private static ArrayList<Offering> scheduleList;
	
	public static List<String> analysis(Schedule schedule) {
		scheduleList = schedule.getSchedule();
		ArrayList<String> result = new ArrayList<String>();
		if (schedule.getCredits() < Schedule.MIN_CREDITS)
			result.add("Too few credits");
		if (schedule.getCredits() > Schedule.MAX_CREDITS && !schedule.getPermission())
			result.add("Too many credits");
		checkDuplicateCourses(result);
		checkOverlap(result);
		return result;
	}
	
	private static void checkDuplicateCourses(ArrayList<String> analysis) {
		HashSet<Course> courses = new HashSet<Course>();
		for (int i = 0; i < scheduleList.size(); i++) {
			Course course = ((Offering) scheduleList.get(i)).getCourse();
			if (courses.contains(course))
				analysis.add("Same course twice - " + course.getName());
			courses.add(course);
		}
	}
	
	private static void checkOverlap(ArrayList<String> analysis) {
		HashSet<String> times = new HashSet<String>();
		for (Iterator<Offering> iterator = scheduleList.iterator(); iterator.hasNext();) {
			Offering offering = (Offering) iterator.next();
			String daysTimes = offering.getDayTime();
			StringTokenizer tokens = new StringTokenizer(daysTimes, ",");
			while (tokens.hasMoreTokens()) {
				String dayTime = tokens.nextToken();
				if (times.contains(dayTime))
					analysis.add("Course overlap - " + dayTime);
				times.add(dayTime);
			}
		}
	}

}
