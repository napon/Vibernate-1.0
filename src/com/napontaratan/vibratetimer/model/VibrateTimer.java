package com.napontaratan.vibratetimer.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VibrateTimer implements Serializable{
	
	private Calendar startTime;
	private Calendar endTime;
	private boolean isActive;
	private int id;
	private boolean[] days;



	/**
	 * Constructor
	 */
	public VibrateTimer (Calendar startTime, Calendar endTime, boolean isActive, boolean[] days, int id) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.isActive = isActive;
		isActive = false;
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

	public boolean getIsActive() {
		return isActive;
	}
	
	public boolean[] getDays() {
		return days;
	}
	
	// Setter
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public void setStartTime (Calendar startTime) {
		this.startTime = startTime;

	}

	public void setIsActivate (boolean isActive) {
		this.isActive = isActive;
	}
	
	public void setDays (boolean[] days) {
		this.days = days;
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
		String response = "VibrateTimer id: " + getId() + " isActive: " + getIsActive() + " startTime: " + getStartTime() + " endTime: " + getEndTime() + " repeating on: " + getRepeatingDays();
    	return response;   
    }
}

