package com.example.solarpanelmanager;

import java.sql.Time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.Constants;
import com.example.solarpanelmanager.api.responses.Event;

public class AddEventActivity extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		
		final EditText startTime = (EditText) findViewById(R.id.StartTime);
		final EditText durationText = (EditText) findViewById(R.id.Duration);
		final EditText intervalText = (EditText) findViewById(R.id.Interval);
		final EditText nameText = (EditText) findViewById(R.id.Name);
		Button addButton = (Button) findViewById(R.id.AddButton);
		
		final String deviceId = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.CURRENT_DEVICE, null);
		if (deviceId == null){
			// TODO - Tell the user that something is wrong
		}
		
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String start = startTime.getText().toString();
				String duration = durationText.getText().toString();
				String interval = intervalText.getText().toString();
				String name = nameText.getText().toString();
				
				Time startTime = Time.valueOf(start);
				Time durationLength = Time.valueOf(duration);
				Time intervalTime = Time.valueOf(interval);
				
				Event e = new Event("some id", name, startTime.getTime(), durationLength.getTime(), intervalTime.getTime());
				Intent i = new Intent();
				i.putExtra(Constants.EVENT_RESULT_CODE, e);
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
		return true;
	}

}
