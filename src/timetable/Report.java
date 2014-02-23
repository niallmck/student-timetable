package timetable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import dao.OfferingDAO;
import dao.ScheduleDAO;
import entity.Offering;
import entity.Schedule;

public class Report {
	
	public Report() {
	}

	private Hashtable<Integer, ArrayList<String>> offeringToName = new Hashtable<Integer, ArrayList<String>>();

	public void populateMap() throws Exception {
		Collection<Schedule> schedules = ScheduleDAO.getAll();
		for (Iterator<Schedule> eachSchedule = schedules.iterator(); eachSchedule.hasNext();) {
			Schedule schedule = (Schedule) eachSchedule.next();
			for (Iterator<Offering> each = schedule.getSchedule().iterator(); each.hasNext(); ) {
				Offering offering = (Offering) each.next();
				populateMapFor(schedule, offering);
			}
		}
	}

	private void populateMapFor(Schedule schedule, Offering offering) {
		ArrayList<String> list = (ArrayList<String>)offeringToName.get(new Integer(offering.getId()));
		if (list == null) {
			list = new ArrayList<String>();
			offeringToName.put(new Integer(offering.getId()), list);
		}
		list.add(schedule.getName());
	}

	public void writeOffering(StringBuffer buffer, ArrayList<String> list, Offering offering) {
		buffer.append(offering.getCourse().getName() + " " + offering.getDaysTimes() + "\n");
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			buffer.append("\t" + s + "\n");
		}
	}

	public void write(StringBuffer buffer) throws Exception {
		populateMap();
		Enumeration<Integer> enumeration = (Enumeration<Integer>)offeringToName.keys();
		while (enumeration.hasMoreElements()) {
			Integer offeringId = (Integer)enumeration.nextElement();
			ArrayList<String> list = (ArrayList<String>)offeringToName.get(offeringId);
			writeOffering(buffer, list, (Offering) OfferingDAO.findById(offeringId.intValue()));
		}
		buffer.append("Number of scheduled offerings: ");
		buffer.append(offeringToName.size());
		buffer.append("\n");
	}
}
