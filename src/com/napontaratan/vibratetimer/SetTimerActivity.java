package com.napontaratan.vibratetimer;

import java.io.IOException;
import java.util.Calendar;

import com.napontaratan.vibratetimer.controller.VibrateTimerController;
import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.MenuItem;

public class SetTimerActivity extends Activity {

	private static String SELECTED_TIMER = "selected_timer";
	private VibrateTimer oldTimer = null;
	
	/**
	 * Create/Modify a VibrateTimer interface
	 * @author Bryan
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_timer);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			try {
				oldTimer = (VibrateTimer) VibrateTimer.deserialize(extras
						.getByteArray(SELECTED_TIMER));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		/**
		 * If editing an existing VibrateTimer, set all the values of the UI
		 */
		if (oldTimer != null) {
			TimePicker start = (TimePicker) findViewById(R.id.startTimePicker);
			TimePicker end = (TimePicker) findViewById(R.id.endTimePicker);
			
			Calendar startTime = oldTimer.getStartTime();
			Calendar endTime = oldTimer.getEndTime();
			start.setCurrentHour(startTime.get(Calendar.HOUR_OF_DAY));
			start.setCurrentMinute(startTime.get(Calendar.MINUTE));
			end.setCurrentHour(endTime.get(Calendar.HOUR_OF_DAY));
			end.setCurrentMinute(endTime.get(Calendar.MINUTE));
			

			int[] buttonIds = new int[] { R.id.Sunday, R.id.Monday,
					R.id.Tuesday, R.id.Wednesday, R.id.Thursday, R.id.Friday,
					R.id.Saturday };
			for (int x = 0; x < 7; x++) {
				ToggleButton tb = (ToggleButton) findViewById(buttonIds[x]);
				tb.setChecked(oldTimer.getDays()[x]);
			}

		}

		final Button saveButton = (Button) findViewById(R.id.SaveButton);
		final Button cancelButton = (Button) findViewById(R.id.CancelButton);

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// Create a new VibrateTimer object
				Calendar start = generateCalendar(R.id.startTimePicker);
				Calendar end = generateCalendar(R.id.endTimePicker);
				boolean[] days = generateDays();
				
				
				
				if(!isWeekDaySet(days)) {
					Toast.makeText(SetTimerActivity.this, "Please specify the days", Toast.LENGTH_SHORT).show();
				
				} else {
				
				int id = VibrateTimerController
						.generateNextId(SetTimerActivity.this);
				VibrateTimer vt = new VibrateTimer(start, end, days, id);

				VibrateTimerController vtc = new VibrateTimerController(
						SetTimerActivity.this);
				vtc.setAlarm(vt, SetTimerActivity.this);
				if (oldTimer != null) {
					vtc.cancelAlarm(oldTimer, SetTimerActivity.this);
				}

				// GO BACK
				Intent intent = new Intent(SetTimerActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// GO BACK
				Intent intent = new Intent(SetTimerActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_timer, menu);
		return true;
	}

	private Calendar generateCalendar(int timerId) {
		Calendar cal = Calendar.getInstance();
		TimePicker tp = (TimePicker) findViewById(timerId);

		cal.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
		cal.set(Calendar.MINUTE, tp.getCurrentMinute());
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings: // go to about page
			Intent i = new Intent(this, About.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean[] generateDays() {
		boolean[] days = new boolean[7];
		int[] buttonIds = new int[] { R.id.Sunday, R.id.Monday, R.id.Tuesday,
				R.id.Wednesday, R.id.Thursday, R.id.Friday, R.id.Saturday };

		for (int x = 0; x < 7; x++) {
			days[x] = ((ToggleButton) findViewById(buttonIds[x])).isChecked();
		}

		return days;
	}
	
	
	private boolean isWeekDaySet(boolean [] days){
		System.out.println("Incoming Days Status:" + days.length);
		for(boolean day: days){
			if(day)
				return true;
		}
		return false;
	}
}
