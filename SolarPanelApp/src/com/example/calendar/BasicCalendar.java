package com.example.calendar;


import java.util.Collection;
import java.util.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import com.example.solarpanelmanager.api.responses.Event;

public class BasicCalendar {
	private Map<String, Event> calendar = new TreeMap<String, Event>();
	private static final long DAY_MILLIS = 24 * 60 * 60 * 1000;
	
	public BasicCalendar(Collection<Event> events) {
		for (Event e : events)
			addEvent(e);
	}
	
	public boolean addEvent(Event event) {
		for (Event e : calendar.values()) {
			if (isOverlap(event, e))
				return false;
		}
		calendar.put(eventToKey(event), event);
		return true;
	}
	
	public void removeEvent(Event event) {
		calendar.remove(eventToKey(event));
	}
	
	public Map<String, Event> getCalendar() {
		return calendar;
	}
	
	private static String eventToKey(Event e) {
		Calendar day = Calendar.getInstance();
		day.setTimeInMillis(e.getFirstTime());
		return day.get(Calendar.HOUR) + ":" + day.get(Calendar.MINUTE);
	}
	
	private static boolean isOverlap(Event e1, Event e2) {
		Date newDay = new Date(e1.getFirstTime());
		long newStart = ((newDay.getHours() * 60 + newDay.getMinutes()) * 60 * 1000) % DAY_MILLIS;
		long newEnd = (newStart + e1.getDuration()) % DAY_MILLIS;
		
		Date oldDay = new Date(e2.getFirstTime());
		long oldStart = ((oldDay.getHours() * 60 + oldDay.getMinutes()) * 60 * 1000) % DAY_MILLIS;
		long oldEnd = (oldStart + e2.getDuration()) % DAY_MILLIS;
		
		return (newStart >= oldStart && newStart < oldEnd) || (newEnd <= oldEnd && newEnd > oldStart);
	}
}
