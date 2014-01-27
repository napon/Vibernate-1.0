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
	private Calendar [] days = new Calendar [7];



	/**
	 * Constructor
	 */
	public VibrateTimer (Calendar startTime, Calendar endTime, boolean isActive, Calendar[] days, int id) {
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
	
	public Calendar [] getDays() {
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
	
	public void setDays (Calendar [] days) {
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

		if (days[0] != null) {
			wantedDate = wantedDate + "M";
		}

		if (days[1] != null) {
			wantedDate = wantedDate + "T";
		}

		if (days[2] != null) {
			wantedDate = wantedDate + "W";
		}

		if (days[3] !=  null) {
			wantedDate = wantedDate + "Th";
		}

		if (days[4] != null) {
			wantedDate = wantedDate + "F";
		}

		if (days[5] != null) {
			wantedDate = wantedDate + "Sat";
		}

		if (days[6] != null) {
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

		if ( days [0] != null) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if (days [1] != null) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [2] != null) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [3] != null) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [4] != null) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [5] != null) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [6] != null) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
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

