package com.napontaratan.vibratetimer.controller;

import java.util.Calendar;
import java.util.List;

import com.napontaratan.vibratetimer.database.VibrateTimerDB;
import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public final class VibrateTimerController {
	private VibrateTimerDB datastore;
	
	private static final int WEEK_MILLISECONDS = 604800000;
	
	private AlarmManager am; 
	private Activity parent;
	
	public VibrateTimerController(Activity ac){
		am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
		datastore = new VibrateTimerDB(ac);
		parent = ac;
	}
	
	/**
	 * Start a repeating vibrate service at the start time and
	 * a repeating ringtone service at the end time 
	 * @param vt is the vibrateAlarm object to create a timer for
	 */
	public void setAlarm(VibrateTimer vt, Context context){
		datastore.addToDB(vt);
		List<Calendar> startTimes = vt.getStartAlarmCalendars();
		List<Calendar> endTimes = vt.getEndAlarmCalendars();
		int timerId = vt.getId();
		for (Calendar startTime : startTimes) {
			int id = timerId + startTime.get(Calendar.DAY_OF_WEEK);
			System.out.println("attempting to create a system alarm with id: " + id + " for start");
			Intent activateVibration = new Intent(parent.getBaseContext(), VibrateOnBroadcastReceiver.class); 
			createSystemTimer(startTime.getTimeInMillis(), id, activateVibration);
		}
		for(Calendar endTime : endTimes){
			int id = timerId + endTime.get(Calendar.DAY_OF_WEEK) + 10;
			System.out.println("attempting to create a system alarm with id: " + id + " for stop");
			Intent disableVibration = new Intent(parent.getBaseContext(), VibrateOffBroadcastReceiver.class);
			createSystemTimer(endTime.getTimeInMillis(), id, disableVibration);
		}
	}
	
	/**
	 * Cancel the services corresponding to the VibrateTimer object
	 * @param vt VibrateTimer object to cancel
	 */
	@SuppressLint("NewApi")
	public void cancelAlarm(VibrateTimer vt, Context context){
		datastore.remove(vt);
		List<Calendar> times = vt.getStartAlarmCalendars();
		for(Calendar time : times){
			int id = vt.getId() + time.get(Calendar.DAY_OF_WEEK);
			System.out.println("deleting alarm with id " + id + " and " + (id+10));
			PendingIntent pi = PendingIntent.getBroadcast(parent.getBaseContext(), id, new Intent(parent.getBaseContext(), VibrateOnBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
			pi.cancel();
			am.cancel(pi);
			pi = PendingIntent.getBroadcast(parent.getBaseContext(), id+10, new Intent(parent.getBaseContext(), VibrateOffBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
			pi.cancel();
			am.cancel(pi);
		}
	}
	
	public List<VibrateTimer> getVibrateTimers() {
		return datastore.getAllVibrateTimers();
	}
	
	public void createSystemTimer(long time, int id, Intent intent){
		System.out.println("SETTING AN ALARM IN CREATESYSTEMTIMER");
		PendingIntent startVibrating = PendingIntent.getBroadcast(parent.getBaseContext(),
				id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am.setRepeating(AlarmManager.RTC, time, WEEK_MILLISECONDS, startVibrating); 
	}
	
	public static int generateNextId(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("idcounter", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		int counter = prefs.getInt("idcounter", -1);
		System.out.println("counter before is " + counter);
			if(counter == -1) {
				counter = 0;
				editor.putInt("idcounter", counter);
				editor.commit();
			} else {
				counter = counter + 20;
				editor.putInt("idcounter", counter);
				editor.commit();
			}
			System.out.println("counter after is " + counter);
		return counter;
	}
}


