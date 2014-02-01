package com.napontaratan.vibratetimer.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VibrateTimer implements Serializable{
	
	private final Calendar startTime;
	private final Calendar endTime;
	private final int id;
	private final boolean[] days;

	/**
	 * Constructor
	 */
	public VibrateTimer (Calendar startTime, Calendar endTime, boolean[] days, int id) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.days = days;
		this.id = id;
	}

	public int getId(){
		return id;
	}
	
	public Calendar getEndTime() {
		return endTime;
	}

	public Calendar getStartTime() {
		return startTime;
	}
	
	public boolean[] getDays() {
		return days;
	}

	/**
	 * Convert startTime as Calendar into String with proper dateFormat
	 *
	 * @param sDateFormat
	 * 				"HH:MM"
	 * @return String
	 * 				startTime after applying sDateFormat
	 */
	public String getStartTimeInFormat (String sDateFormat) {
		String startTest = null;

		// Construct a sDateTest based on given DateFormat 
		SimpleDateFormat sDateTest = new SimpleDateFormat(sDateFormat);

		if (startTime != null) {
			startTest = sDateTest.format(startTime.getTime());	
		}

		System.out.println(" this is the startTest: " + startTest);
		return startTest;

	}

	/**
	 * Convert endTIme as Calendar into String with proper dateFormat
	 * 
	 * @param eDateFormat
	 * 				"HH:MM"
	 * @return String
	 * 				endTime after applying eDateFormat
	 */
	public String getEndTimeInFormat (String eDateFormat) {

		String endTest = null;
		SimpleDateFormat eDateTest = new SimpleDateFormat(eDateFormat);

		if (endTime != null) {
			endTest = eDateTest.format(endTime.getTime());
		}

		System.out.println("this is the endTest: " + endTest);
		return endTest;
	}

	/**
	 * Get repeating days
	 * 
	 * @return String
	 */
	public String getRepeatingDays() {
		String wantedDate = "";

		if (days[1]) {
			wantedDate = wantedDate + "Mon";
		}

		if (days[2]) {
			wantedDate = wantedDate + "Tue";
		}

		if (days[3]) {
			wantedDate = wantedDate + "Wed";
		}

		if (days[4]) {
			wantedDate = wantedDate + "Thu";
		}

		if (days[5]) {
			wantedDate = wantedDate + "Fri";
		}

		if (days[6]) {
			wantedDate = wantedDate + "Sat";
		}

		if (days[0]) {
			wantedDate = wantedDate + "Sun";
		}
		return wantedDate;
	}

	public List<Calendar> getStartAlarmCalendars(){
		List<Calendar> calendars = new ArrayList<Calendar>();
		for (int i = 0; i < 7; i++) {
			if (days[i]) {
				Calendar day = Calendar.getInstance();
				day.set(Calendar.DAY_OF_WEEK, getDayOfWeekFromInt(i));
				day.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
				day.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
				day.set(Calendar.SECOND, startTime.get(Calendar.SECOND));
			}
		}
		return calendars;
	}
	
	public List<Calendar> getEndAlarmCalendars() {
		List<Calendar> calendars = new ArrayList<Calendar>();
		for (int i = 0; i < 7; i++) {
			if (days[i]) {
				Calendar day = Calendar.getInstance();
				day.set(Calendar.DAY_OF_WEEK, getDayOfWeekFromInt(i));
				day.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
				day.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
				day.set(Calendar.SECOND, endTime.get(Calendar.SECOND));
			}
		}
		return calendars;
	}
	
	private int getDayOfWeekFromInt(int day) {
		int dayOfWeek = 0;
		switch(day) {
		case 0:
			dayOfWeek = Calendar.SUNDAY;
			break;
		case 1:
			dayOfWeek = Calendar.MONDAY;
			break;
		case 2:
			dayOfWeek = Calendar.TUESDAY;
			break;
		case 3:
			dayOfWeek = Calendar.WEDNESDAY;
			break;
		case 4:
			dayOfWeek = Calendar.THURSDAY;
			break;
		case 5:
			dayOfWeek = Calendar.FRIDAY;
			break;
		case 6:
			dayOfWeek = Calendar.SATURDAY;
			break;
		}
		return dayOfWeek;
	}
	
	/**
	 * Get the number of repeatingDays
	 * 
	 * @return numberOfRepeatingDays
	 */
	public Integer getNumberOfRepeatingDays () {
		Integer numberOfRepeatingDays = 0;
		for (int i = 0; i < 7; i++) {
			if (days[i]) {
				numberOfRepeatingDays += 1;
			}
		}
		return numberOfRepeatingDays;
	}
	
	// ========================== //
	// need the following to work with the database - napon
	
	public static byte[] serialize(Object obj) throws IOException {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	ObjectOutputStream os = new ObjectOutputStream(out);
    	os.writeObject(obj);
    	return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
    	ByteArrayInputStream in = new ByteArrayInputStream(data);
    	ObjectInputStream is = new ObjectInputStream(in);
    	return is.readObject();
    }
    
    @Override
    public String toString() {  // for testing purposes
		String response = "VibrateTimer id: " + getId() + " startTime: " + getStartTime() + " endTime: " + getEndTime() + " repeating on: " + getRepeatingDays();
    	return response;   
    }


}

