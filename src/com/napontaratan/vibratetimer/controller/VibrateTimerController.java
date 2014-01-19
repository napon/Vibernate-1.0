package com.napontaratan.vibratetimer.controller;

import java.util.Calendar;
import com.napontaratan.vibratetimer.model.VibrateTimer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class VibrateTimerController {
	

	private static final int WEEK_MILLISECONDS = 604800000;
	
	private AlarmManager am; 
	private Activity parent;
	
	public VibrateTimerController(Activity ac){
		am = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
		parent = ac;
	}
	
	/**
	 * start a repeating vibrate service at the start time and
	 * a repeating ringtone service at the end time
	 * @param vt is the vibrateAlarm object to create a timer for
	 */
	public void createVibrateTimer(VibrateTimer vt, Context context){
		int numberOfAlarms = vt.getNumberOfRepeatingDays();
		for(int i = 0; i < numberOfAlarms; i++){
			int id = vt.getId() + numberToIncrement(i, vt);   
			Calendar startTime = calendarAtIteration(i, vt);
			Intent activateVibration = new Intent(context, VibrateOnBroadcastReceiver.class); 
			createSystemTimer(startTime.getTimeInMillis(), id, activateVibration);
			Intent disableVibration = new Intent(context, VibrateOffBroadcastReceiver.class);
			createSystemTimer(startTime.getTimeInMillis(), id, disableVibration);
		}
	}
	
	/**
	 * cancel the services corresponding to the VibrateTimer object
	 * @param vt VibrateTimer object to cancel
	 */
	@SuppressLint("NewApi")
	public void cancelVibrateTimer(VibrateTimer vt, Context context){
		for(int i = 0; i < vt.getNumberOfRepeatingDays(); i++){
			int id = vt.getId() + numberToIncrement(i,vt);
			Intent[] things = new Intent[2];
			things[0] = new Intent(context, VibrateOnBroadcastReceiver.class);
			things[1] = new Intent(context, VibrateOffBroadcastReceiver.class);
			PendingIntent pi = PendingIntent.getActivities(parent.getApplicationContext(), id, things, PendingIntent.FLAG_CANCEL_CURRENT);
			am.cancel(pi);
		}
	}
	
	private void createSystemTimer(long time, int id, Intent intent){
		PendingIntent startVibrating = PendingIntent.getBroadcast(parent.getApplicationContext(),
				id, intent, PendingIntent.FLAG_ONE_SHOT);
		am.setRepeating(AlarmManager.RTC, time, WEEK_MILLISECONDS, startVibrating); 
	}
	
	
	/**
	 * get the number to increment depending on the day, monday = 0, tuesday = 1, etc.
	 * used to calculate the correct id for each alarm
	 * @param iteration the ith day
	 * @param vt object to get the day for
	 * @return the correct incrementation for different day, monday = 0, tuesday = 1, etc.
	 */
	private int numberToIncrement(int iteration, VibrateTimer vt){
		int count = iteration + 1;
		Calendar[] alarmArray = vt.getDays();
		for (int i = 0; i < 6; i++){
			if(alarmArray[i] != null){
				count--;
				if(count == 0)
					return i;
			}
		}
		return -1;  //error;
	}
	
	/**
	 * return the ith valid calendar in vt
	 * eg. if alarm on monday wednesday and friday and iteration = 1, then return calendar object for wednesday
	 * @param iteration
	 * @param vt
	 * @return
	 */
	private Calendar calendarAtIteration(int iteration, VibrateTimer vt){
		int count = iteration + 1;
		Calendar[] alarmArray = vt.getDays();
		for (int i = 0; i < 6; i++){
			if(alarmArray[i] != null){
				count--;
				if(count == 0)
					return alarmArray[i];
			}
		}
		return null;  //error;
	}

	private static final class VibrateOnBroadcastReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
	}
	
	private class VibrateOffBroadcastReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
	}
}


