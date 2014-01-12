package com.napontaratan.vibratetimer.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VibrateTimer {

	private Calendar startTime;
	private Calendar endTime;
	private boolean isActive;
	private boolean [] days = new boolean [7];


	/**
	 * Constructor
	 */
	public VibrateTimer (Calendar startTime, Calendar endTime, boolean isActive ) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.isActive = isActive;
		isActive = false;
	}

	// Getter
	public Calendar getEndTime() {
		return endTime;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public boolean getIsActive() {
		return isActive;
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

		if ( days [0]) {
			wantedDate = wantedDate + "M";
		}

		if (days [1]) {
			wantedDate = wantedDate + " T";
		}

		if ( days [2]) {
			wantedDate = wantedDate + " W";
		}

		if ( days [3]) {
			wantedDate = wantedDate + " Th";
		}

		if ( days [4]) {
			wantedDate = wantedDate + " F";
		}

		if ( days [5]) {
			wantedDate = wantedDate + " Sat";
		}

		if ( days [6]) {
			wantedDate = wantedDate + " Sun";
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
		
		if ( days [0]) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if (days [1]) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [2]) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [3]) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [4]) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [5]) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}

		if ( days [6]) {
			numberOfRepeatingDays = numberOfRepeatingDays + 1;
		}
		
		return numberOfRepeatingDays;
		
	}

}
