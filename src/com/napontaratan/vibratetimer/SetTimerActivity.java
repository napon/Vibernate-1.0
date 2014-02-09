package com.napontaratan.vibratetimer;

import java.util.Calendar;

import com.napontaratan.vibratetimer.controller.VibrateTimerController;
import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class SetTimerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_timer);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    byte[] value = extras.getByteArray("")
		}
		final Button saveButton = (Button) findViewById(R.id.SaveButton);
		final Button cancelButton = (Button) findViewById(R.id.CancelButton);

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Create a new VibrateTimer object
				System.out.println("Clicked on Save");
				Calendar start = generateCalendar(R.id.startTimePicker);
				Calendar end = generateCalendar(R.id.endTimePicker);
				boolean[] days = generateDays();
				int id = VibrateTimerController.generateNextId(SetTimerActivity.this);

				VibrateTimer vt = new VibrateTimer(start, end, days, id);
				VibrateTimerController vtc = new VibrateTimerController(SetTimerActivity.this);
				vtc.setAlarm(vt, SetTimerActivity.this);
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 
				System.out.println("Clicked on Close");
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_timer, menu);
		return true;
	}
	
	private Calendar generateCalendar(int timerId){
		Calendar cal = Calendar.getInstance();
		TimePicker tp = (TimePicker) findViewById(timerId);
		
		cal.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
		cal.set(Calendar.MINUTE, tp.getCurrentMinute());
		
		return cal;
	}

	private boolean[] generateDays(){
		boolean[] days = new boolean[7];
		int[] buttonIds = new int[]
				{R.id.Sunday, R.id.Monday, R.id.Tuesday, R.id.Wednesday, R.id.Thursday, R.id.Friday, R.id.Saturday};
		
		for(int x = 0; x < 7; x++){
			days[x] = ((ToggleButton) findViewById(buttonIds[x])).isChecked();
		}
		return days;
	}
}
