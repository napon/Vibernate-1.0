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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		if// load from the database != null)
//			vibrateTimers = ; // loading from database;
//		else
//			vibrateTimers = new ArrayList<VibrateTimer>();
		
		ListView listOfVibrates = (ListView) findViewById(R.id.vibrates);
		if(vibrateTimers == null)
			vibrateTimers = new ArrayList<VibrateTimer>();
		listOfVibrates.setAdapter(new VibrateArrayAdapter(this.getApplicationContext(), R.layout.vibrate, vibrateTimers));
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
		
		public VibrateArrayAdapter(Context context, int customViewResourceId,
				List<VibrateTimer> vibrateTimers) {
			super(context, customViewResourceId, vibrateTimers);
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

//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = null;
			if(arg1 == null){
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(resourceId, arg2);
						
			}
			return view;
		}


	} 


}
