package com.napontaratan.vibratetimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * About the app page
 * @author Paul, Amelia
 */
public class About extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_app);

		Button shareBotton = (Button) this.findViewById(R.id.ShareButton);
		shareBotton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) { 
				ShareIt();
			}

			private void ShareIt() {
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "This app is so amazing I use it for all my classes! Check it out! <PlayStore url>";

				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "VibrateTimer is the best app ever!");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}
		});
	}
}