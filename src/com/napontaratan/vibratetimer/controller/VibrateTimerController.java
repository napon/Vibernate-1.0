package com.napontaratan.vibratetimer.controller;

import java.util.Calendar;

import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class VibrateTimerController {
	
	AlarmManager am; // using AlarmManager to trigger an event at the specified time
	Activity parent;
	
	public VibrateTimerController(Activity ac){
		am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
		parent = ac;
	}
	
	/**
	 * start a repeating vibrate service at the start time and
	 * a repeating ringtone service at the end time
	 * @param vt is the vibrateAlarm object to create a timer for
	 */
	public void createVibrateTimer(VibrateTimer vt){
		int numberOfAlarms = vt.getNumberOfRepeatingDays();
		for(int i = 0; i < numberOfAlarms; i++){
			int id = vt.hashCode() + i;   
			Calendar startTime = vt.getStartTime();
			Intent activateVibration = new Intent(); //TODO: do the actual setting phone to vibrate here;
			PendingIntent startVibrating = PendingIntent.getBroadcast(parent.getApplicationContext(), id, activateVibration, PendingIntent.FLAG_ONE_SHOT);
			am.setRepeating(AlarmManager.RTC, 10000, 604800000, startVibrating); //TODO: 10000 needs to be changed, currently go on vibrate after 10 seconds;
		}
	}
	
	/**
	 * cancel the services corresponding to the VibrateTimer object
	 * @param vt VibrateTimer object to cancel
	 */
	public void cancelVibrateTimer(VibrateTimer vt){
		
	}
	
	/**
	 * 
	 * @param oldvt vibrateAlarm object to change
	 * @param newvt vibrateAlarm object to change to
	 */
	public void editAlarm(VibrateTimer oldvt, VibrateTimer newvt){
		cancelVibrateTimer(oldvt);
		createVibrateTimer(newvt);
	}
	
}


