package com.napontaratan.vibratetimer;

import java.util.*;

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
	private String[] days = new String[]{"Su","Mo","Tu","We","Th","Fr","Sa"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		ListView listOfVibrates = (ListView) findViewById(R.id.vibrates);
		if(vibrateTimers == null)
			vibrateTimers = new ArrayList<VibrateTimer>();
		vibrateTimers.add(new VibrateTimer(null,null,false,null,0));
		listOfVibrates.setAdapter(new VibrateArrayAdapter(this, R.layout.vibrate, vibrateTimers));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private class VibrateArrayAdapter extends ArrayAdapter<VibrateTimer> {
		//		    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
		private List<VibrateTimer> vibrateTimers;
		private int resourceId;
		private Context context;
		
		public VibrateArrayAdapter(Context context, int customViewResourceId,
				List<VibrateTimer> vibrateTimers) {
			super(context, customViewResourceId, vibrateTimers);
			this.context = context;
			this.vibrateTimers = vibrateTimers;
			resourceId = customViewResourceId;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return vibrateTimers.size();
		}

		@Override
		public VibrateTimer getItem(int arg0) {
			// TODO Auto-generated method stub
			return vibrateTimers.get(arg0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(resourceId, parent, false);
			VibrateTimer timer = vibrateTimers.get(position);
			String startTime = timer.getStartTime().getTime().toString();
			String endTime = timer.getEndTime().getTime().toString();
			
			TextView timeStart = (TextView) itemView.findViewById(R.id.time_start);
			timeStart.setText("Starting Time");
			TextView timeEnd = (TextView) itemView.findViewById(R.id.time_end);
			timeEnd.setText("Ending time");
			TextView day = (TextView) itemView.findViewById(R.id.day);
			// note: cast int position to string bcuz not doing so throws string resource not found err
			day.setText(Integer.toString(position));
			return itemView;	
		}


	} 


}

