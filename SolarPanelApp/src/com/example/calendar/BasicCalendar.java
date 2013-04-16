package com.example.calendar;


import java.util.Collection;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import com.example.solarpanelmanager.api.responses.Event;

public class BasicCalendar {
	private Map<String, Event> calendar = new TreeMap<String, Event>();
	private static final long DAY_MILLIS = 24 * 60 * 60 * 1000;
	
	public BasicCalendar(Collection<Event> events) {
		for (Event e : events) {
			addEvent(e);				
		}
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
	
	public void removeEvent(String id) {
		for (Event e : calendar.values()) {
			if (e.getId() == id) {
				removeEvent(e);
				break;
			}
		}
	}
	
	public Map<String, Event> getCalendar() {
		return calendar;
	}
	
	public static String eventToKey(Event e) {
		Calendar day = Calendar.getInstance();
		day.setTimeInMillis(e.getFirstTime());
		return String.format("%d:%02d", day.get(Calendar.HOUR), day.get(Calendar.MINUTE));
	}
	
	private static boolean isOverlap(Event e1, Event e2) {
		long newStart = e1.getFirstTime() % DAY_MILLIS;
		long newEnd = (newStart + e1.getDuration()) % DAY_MILLIS;
		
		long oldStart = e2.getFirstTime() % DAY_MILLIS;
		long oldEnd = (oldStart + e2.getDuration()) % DAY_MILLIS;
		
		return (newStart >= oldStart && newStart < oldEnd) || (newEnd <= oldEnd && newEnd > oldStart);
	}
}