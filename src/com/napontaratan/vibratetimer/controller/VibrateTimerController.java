package com.napontaratan.vibratetimer.controller;

import java.util.Calendar;

import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

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
	public void createVibrateTimer(VibrateTimer vt, Context context){
		int numberOfAlarms = vt.getNumberOfRepeatingDays();
		for(int i = 0; i < numberOfAlarms; i++){
			int id = vt.hashCode() + i;
			Calendar startTime = vt.getStartTime();
			Intent activateVibration = new Intent(context, VibrateOnBroadcastReceiver.class);
			createSystemTimer(startTime.getTimeInMillis(), id, activateVibration);
		}
	}
	
	private void createSystemTimer(long time, int id, Intent intent){
		PendingIntent startVibrating = PendingIntent.getBroadcast(parent.getApplicationContext(),
				id, intent, PendingIntent.FLAG_ONE_SHOT);
		am.setRepeating(AlarmManager.RTC, time, 604800000, startVibrating); 
	}
	
	/**
	 * cancel the services corresponding to the VibrateTimer object
	 * @param vt VibrateTimer object to cancel
	 */
	public void cancelVibrateTimer(VibrateTimer vt){
		
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
}


