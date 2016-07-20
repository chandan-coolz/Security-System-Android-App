package com.coolcreation.createNetwork;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coolcreation.securitualarmsystem.R;
import com.coolcreation.showAlarmNotification.FetchAlramService;

public class MyHomeCreateAlarm extends Activity implements CreateAlramInterface {
	Button ADbutton;
	TextView status;
	public static String alarmID;
	String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_home_create_alarm);
		ADbutton = (Button) findViewById(R.id.ADbutton);
		status = (TextView) findViewById(R.id.status);
		userID = getIntent().getExtras().getString("userID");
		Intent intent = new Intent(this, FetchAlramService.class);
		intent.putExtra("userID", userID);
		startService(intent);
	}

	public void alramButtonClick(View v) {

		String bString = ADbutton.getText().toString();
		if (bString.equalsIgnoreCase("Activate")) {
			// call activate function
			String type = "Fire Alram";
			// get system current itme
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			String time = ft.format(dNow);

			ActivateAlramAsync activateAlramAsync = new ActivateAlramAsync(this);
			activateAlramAsync.execute(type, time, userID);
		} else {
			// call deactivate function
			DeactivaeAlarmAsync deactivaeAlarmAsync = new DeactivaeAlarmAsync(
					this);

			deactivaeAlarmAsync.execute(alarmID);
		}

	}// alarmButtonClick

	@Override
	public void alramActive(String alarm_id) {
		// TODO Auto-generated method stub
		alarmID = alarm_id;

		ADbutton.setText("Deactivate");
		status.setText("Active");
		status.setTextColor(Color.GREEN);

	}// alarmActive

	@Override
	public void alramDeactive() {
		// TODO Auto-generated method stub
		ADbutton.setText("Activate");
		status.setText("Deactive");
		status.setTextColor(Color.RED);
		alarmID = "";

	}// alarmDeactive

}
