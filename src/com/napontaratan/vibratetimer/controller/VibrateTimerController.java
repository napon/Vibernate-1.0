package com.napontaratan.vibratetimer.controller;

import java.util.Calendar;
import java.util.List;

import com.napontaratan.vibratetimer.database.VibrateTimerDB;
import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;

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
			Intent activateVibration = new Intent(context, VibrateOnBroadcastReceiver.class); 
			createSystemTimer(startTime.getTimeInMillis(), id, activateVibration);
		}
		for(Calendar endTime : endTimes){
			int id = timerId + endTime.get(Calendar.DAY_OF_WEEK);
			Intent disableVibration = new Intent(context, VibrateOffBroadcastReceiver.class);
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
			Intent[] things = new Intent[2];
			things[0] = new Intent(context, VibrateOnBroadcastReceiver.class);
			things[1] = new Intent(context, VibrateOffBroadcastReceiver.class);
			PendingIntent pi = PendingIntent.getActivities(parent.getApplicationContext(), id, things, 
					PendingIntent.FLAG_CANCEL_CURRENT);
			am.cancel(pi);
		}
	}
	
	public List<VibrateTimer> getVibrateTimers() {
		return datastore.getAllVibrateTimers();
	}
	
	private void createSystemTimer(long time, int id, Intent intent){
		PendingIntent startVibrating = PendingIntent.getBroadcast(parent.getApplicationContext(),
				id, intent, PendingIntent.FLAG_ONE_SHOT);
		am.setRepeating(AlarmManager.RTC, time, WEEK_MILLISECONDS, startVibrating); 
	}

	private static final class VibrateOnBroadcastReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
	}
	
	private static final class VibrateOffBroadcastReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
	}
	
	public static int generateNextId(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("idcounter", Context.MODE_PRIVATE);
		int counter = prefs.getInt("idcounter", -1);
			if(counter == -1) {
				counter = 0;
				prefs.edit().putInt("idcounter", counter);
			} else {
				counter = counter + 10;
			}
		return counter;
	}
}


