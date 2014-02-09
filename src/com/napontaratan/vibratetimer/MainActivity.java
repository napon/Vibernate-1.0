package com.napontaratan.vibratetimer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.napontaratan.vibratetimer.controller.VibrateTimerController;
import com.napontaratan.vibratetimer.database.VibrateTimerDB;
import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private List<VibrateTimer> vibrateTimers;
	private String[] days = new String[]{"Su ","Mo ","Tu ","We ","Th ","Fr ","Sa "};
	private VibrateTimerController controller;
	private static String SELECTED_TIMER = "selected_timer";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		controller = new VibrateTimerController(this);

		VibrateTimerDB datastore = new VibrateTimerDB(this);

		vibrateTimers = controller.getVibrateTimers();
		if(vibrateTimers == null) // no existing timers
			vibrateTimers = new ArrayList<VibrateTimer>();
		System.out.println(vibrateTimers.size());

		ListView listOfVibrates = (ListView) findViewById(R.id.vibrates);
		listOfVibrates.setAdapter(new VibrateArrayAdapter(this, R.layout.vibrate, vibrateTimers));
		final Context currentActivity = this; // to pass into item click intent
		listOfVibrates.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,long arg3) {
				// passing the vibrate object that has been clicked
				System.out.println("clicked on " + index);
				Intent i = new Intent(currentActivity, SetTimerActivity.class);
				try {
					i.putExtra(SELECTED_TIMER, VibrateTimer.serialize(vibrateTimers.get(index)));
					startActivity(i);
				} catch (IOException e) {
					System.out.println("IOException caught in MainActivity");
				}
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_addVibrate:
			addVibrateTimer();
			return true;
		case R.id.action_settings:	// go to about page
			Intent i = new Intent(this, About.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		
		}
	}

	/**
	 * add a new VibrateTimer, user will be taken to setTimerActivity page 
	 */
	public void addVibrateTimer(){
		Intent i = new Intent(this, SetTimerActivity.class);
		startActivity(i);
	}



	/**
	 * List Adapter for our custom VibrateTimer Object
	 * @author daniel
	 *
	 */
	private class VibrateArrayAdapter extends ArrayAdapter<VibrateTimer> {
		private List<VibrateTimer> listOfVibrateTimers;
		private int resourceId; // id for a single item view 
		private Context context;

		public VibrateArrayAdapter(Context context, int customViewResourceId,
				List<VibrateTimer> vibrateTimers) {
			super(context, customViewResourceId, vibrateTimers);
			this.context = context;
			this.listOfVibrateTimers = vibrateTimers;
			resourceId = customViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(resourceId, parent, false); 
			// get all the info needed to show a timer on UI 
			VibrateTimer timer = listOfVibrateTimers.get(position);
			String startTime = getStartTimeInFormat(timer, "HH:MM");
			String endTime = getStartTimeInFormat(timer, "HH:MM");
			boolean[] daysOn = timer.getDays();
			String dayString = "";
			// determines which days the timer are active and log it as String 
			for(int day = 0; day < daysOn.length; day++){
				if(daysOn[day])
					dayString += days[day];
			}
			// setting the view 
			TextView timeStart = (TextView) itemView.findViewById(R.id.time_start);
			timeStart.setText(startTime);
			TextView timeEnd = (TextView) itemView.findViewById(R.id.time_end);
			timeEnd.setText(endTime);
			TextView day = (TextView) itemView.findViewById(R.id.day);
			// note: cast int position to string bcuz not doing so throws string resource not found err
			day.setText(dayString);
			return itemView;	
		}
	} 
	
	
	// ========  HELPER METHODS ==========================
	
	/**
	 * Convert startTime as Calendar into String with proper dateFormat
	 *
	 * @param sDateFormat
	 *              "HH:MM"
	 * @return String
	 *              startTime after applying sDateFormat
	 */
	public String getStartTimeInFormat (VibrateTimer vt , String sDateFormat) {
		String startTest = null;

		// Construct a sDateTest based on given DateFormat 
		SimpleDateFormat sDateTest = new SimpleDateFormat(sDateFormat);

		if (vt.getStartTime() != null) {
			startTest = sDateTest.format(vt.getStartTime().getTime());  
		}

		System.out.println(" this is the startTest: " + startTest);
		return startTest;

	}

	/**
	 * Convert endTime as Calendar into String with proper dateFormat
	 *
	 * @param eDateFormat
	 *              "HH:MM"
	 * @return String
	 *              startTime after applying eDateFormat
	 */
	public String getEndTimeInFormat (VibrateTimer vt , String eDateFormat) {
		String startTest = null;

		// Construct a sDateTest based on given DateFormat 
		SimpleDateFormat sDateTest = new SimpleDateFormat(eDateFormat);

		if (vt.getEndTime() != null) {
			startTest = sDateTest.format(vt.getEndTime().getTime());  
		}

		System.out.println(" this is the startTest: " + startTest);
		return startTest;

	}

} 





