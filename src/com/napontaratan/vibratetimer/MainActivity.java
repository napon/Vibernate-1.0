package com.napontaratan.vibratetimer;

import java.util.*;

import com.napontaratan.vibratetimer.controller.VibrateTimerController;
import com.napontaratan.vibratetimer.model.VibrateTimer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private List<VibrateTimer> vibrateTimers;
	private String[] days = new String[]{"Su ","Mo ","Tu ","We ","Th ","Fr ","Sa "};
	private VibrateTimerController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		controller = new VibrateTimerController(this);
		
		vibrateTimers = controller.getVibrateTimers();
		
		ListView listOfVibrates = (ListView) findViewById(R.id.vibrates);
		if(vibrateTimers == null) // no existing timers
			vibrateTimers = new ArrayList<VibrateTimer>();
		listOfVibrates.setAdapter(new VibrateArrayAdapter(this, R.layout.vibrate, vibrateTimers));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			String startTime = timer.getStartTime().getTime().toString();
			String endTime = timer.getEndTime().getTime().toString();
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


}

