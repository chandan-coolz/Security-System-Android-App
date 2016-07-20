package com.coolcreation.network;

import com.coolcreation.securitualarmsystem.R;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path.FillType;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class JoinNetwork extends Activity implements FetchCompleted {
	GetNetworkNameAsync getNetworkNameAsync;
	DisplayMetrics metrics;
	int width;
	int height;
    String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join_network);
		userID=getIntent().getExtras().getString("userID");
		metrics = this.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;

		getNetworkNameAsync = new GetNetworkNameAsync(this);
		getNetworkNameAsync.execute(userID);

	}

	@Override
	public void onTaskComplete(String[] networkList) {
		// TODO Auto-generated method stub

		LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);
		// button will be displayed
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		// Create four
		for (int j = 0; j < networkList.length; j++) {
			// Create LinearLayout

			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.HORIZONTAL);

			// Create TextView
			TextView netwrokName = new TextView(this);
			netwrokName.setText(networkList[j]);
			netwrokName.setBackgroundColor(Color.BLUE);
			netwrokName.setTextColor(Color.WHITE);
			netwrokName.setTextSize(30.0f);

			netwrokName.setLayoutParams(new LinearLayout.LayoutParams(
					(width / 4) * 3, LayoutParams.WRAP_CONTENT));
			ll.addView(netwrokName);

			// Create Button
			Button btn = new Button(this);
			// Give button an ID
			btn.setId(j);
			// btn.setHeight(30);
			btn.setText("Join");

			// set the layoutParams on the button
			btn.setLayoutParams(params);

			// Set click listener for button

			final String id = networkList[j];
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					sendRequest(id);

				}
			});

			// Add button to LinearLayout
			ll.addView(btn);
			// Add button to LinearLayout defined in XML
			lm.addView(ll);
			View view = new View(this);
			view.setBackgroundColor(Color.WHITE);
			view.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, 3));
			lm.addView(view);

		}

	}// onTaskComplete

	public void sendRequest(String id) {

		SendJoinRequestAsync joinRequestAsync = new SendJoinRequestAsync(this);
		joinRequestAsync.execute(id,userID);

	}// sendRequest

	@Override
	public void restartActivity() {
		// TODO Auto-generated method stub
		recreate();
		

	}

}// joinNetwork
