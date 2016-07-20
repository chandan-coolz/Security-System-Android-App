package com.coolcreation.showAlarmNotification;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.coolcreation.securitualarmsystem.R;

public class DisplayAlramMessage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// fetch the details from intent
		setContentView(R.layout.display_alram);
		String type = getIntent().getStringExtra("type");
		String time = getIntent().getStringExtra("time");
		String name = getIntent().getStringExtra("userName");
		String phoneNumber = getIntent().getStringExtra("phoneNumber");
		String address = getIntent().getStringExtra("address");

		// get the textView properties
		TextView aType = (TextView) findViewById(R.id.type);
		TextView aTime = (TextView) findViewById(R.id.time);
		TextView uName = (TextView) findViewById(R.id.name);
		TextView uPhoneNumber = (TextView) findViewById(R.id.phoneNumber);
		TextView uAddress = (TextView) findViewById(R.id.address);

		// set the values of testViews
		aType.setText(type);
		aTime.setText(time);
		uName.setText(name);
		uPhoneNumber.setText(phoneNumber);
		uAddress.setText(address);

	}// onCreate

}// displayalram
