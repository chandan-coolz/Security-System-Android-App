package com.coolcreation.network;

import com.coolcreation.securitualarmsystem.R;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NetworkDetail extends Activity implements NetworkDetailInterface {

	DisplayMetrics metrics;
	int width;
	int heigh;

	String networkName = "";
	String ownerflg = "";
    String userID = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_detail);
		metrics = this.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		heigh = metrics.heightPixels;

		networkName = getIntent().getStringExtra("networkName");
		ownerflg = getIntent().getStringExtra("ownerFLG");
		userID = getIntent().getExtras().getString("userID");
		if(ownerflg.contains("Y")){
			TextView view=(TextView) findViewById(R.id.leaveGroup);
			view.setVisibility(View.INVISIBLE);
		}
		
		NetworkDetailAsync detailAsync = new NetworkDetailAsync(this);
		detailAsync.execute(networkName);
	}// onCreate

	@Override
	public void printDetails(String[] userName, String[] ownerFLG,
			String[] joinedFLG) {
		// TODO Auto-generated method stub
		boolean joingRequestPrintFLG=true;
		TextView networkNameView = (TextView) findViewById(R.id.networkNameHeading);
		networkNameView.setText(networkName);
		LinearLayout lm = (LinearLayout) findViewById(R.id.mainNetworkDetail);
		LinearLayout members = (LinearLayout) findViewById(R.id.memberDetail);
		if (ownerflg.contains("Y")) {

			for (int i = 0; i < userName.length; i++) {
				if (joinedFLG[i].contains("N")) {
                  
					if(joingRequestPrintFLG){
						
						TextView textView = new TextView(this);
						textView.setTextColor(Color.BLACK);
						textView.setTextSize(30.0f);
						textView.setBackgroundColor(Color.WHITE);
						textView.setText("Joining Request");
						textView.setLayoutParams(new LinearLayout.LayoutParams(
								LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
						lm.addView(textView);
						joingRequestPrintFLG=false;
					}//print joining request heading
					
					// Create LinearLayout
					LinearLayout ll = new LinearLayout(this);
					ll.setOrientation(LinearLayout.HORIZONTAL);
					ll.setBackgroundColor(Color.LTGRAY);

					// Create TextView
					TextView name = new TextView(this);
					name.setText(userName[i]);
					name.setTextSize(25);
					name.setLayoutParams(new LinearLayout.LayoutParams(
							width * 2 / 4, LayoutParams.WRAP_CONTENT));
					ll.addView(name);
                    final String username=userName[i];
					// 1 button
					Button btn = new Button(this);
					btn.setText("Confirm");
					btn.setLayoutParams(new LinearLayout.LayoutParams(
							width / 4, LayoutParams.WRAP_CONTENT));
					btn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							updateJoiningRequest(username,"Y");
						}
					});
					
					
					ll.addView(btn);

					// 2 button
					Button btn1 = new Button(this);
					btn1.setId(i);
					btn1.setText("Reject");
					btn1.setLayoutParams(new LinearLayout.LayoutParams(
							width / 4, LayoutParams.WRAP_CONTENT));
					
					btn1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							updateJoiningRequest(username,"N");
						}
					});
					
					
					
					ll.addView(btn1);

					// add ll to main lm
					lm.addView(ll);
				}// inner if

			}// for

		}// if

		// to print joined member list
		TextView textView = new TextView(this);
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(30.0f);
		textView.setBackgroundColor(Color.WHITE);
		textView.setText("Members");
		textView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		lm.addView(textView);
		for (int i = 0; i < userName.length; i++) {
			if (joinedFLG[i].equals("Y")) {

				if (ownerFLG[i].equalsIgnoreCase("Y")) {
					// Create LinearLayout
					LinearLayout ll = new LinearLayout(this);
					ll.setOrientation(LinearLayout.HORIZONTAL);
					ll.setBackgroundColor(Color.LTGRAY);
					// Create TextView
					TextView name = new TextView(this);
					name.setText(userName[i]);
					name.setTextSize(25);
					name.setLayoutParams(new LinearLayout.LayoutParams(width*3/4,
							LayoutParams.WRAP_CONTENT));
					ll.addView(name);
					
					// Create TextView
					TextView name1 = new TextView(this);
					name1.setText("admin");
					name1.setTextSize(20);
					name1.setTextColor(Color.GREEN);
					name1.setLayoutParams(new LinearLayout.LayoutParams(width/4,
							LayoutParams.WRAP_CONTENT));
					ll.addView(name1);
					lm.addView(ll);
					//
					View view=new View(this);
					view.setBackgroundColor(Color.WHITE);
					view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
							10));
					lm.addView(view);
					
				} else {

					// Create TextView
					TextView name = new TextView(this);
					name.setText(userName[i]);
					name.setTextSize(25);
					name.setBackgroundColor(Color.LTGRAY);
					name.setLayoutParams(new LinearLayout.LayoutParams(width,
							LayoutParams.WRAP_CONTENT));

					lm.addView(name);
					
					View view=new View(this);
					view.setBackgroundColor(Color.WHITE);
					view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
							10));
					lm.addView(view);

				}

			}// inner if

		}// for

	}// printDetails

	
//method to update the joining request
	public void updateJoiningRequest(String userName,String confirmFLG){
		
		
		changeJoiningStatusAsync changeJoiningStatusAsync=new changeJoiningStatusAsync(this);
		changeJoiningStatusAsync.execute(userName,confirmFLG,networkName);
	}//updateJoiningRequest

	@Override
	public void reStartActivity() {
		// TODO Auto-generated method stub
		
		recreate();
	}//reStartActivity
	
	
	public void exitGroup(View v){
		ExitNetworkAsync exitNetworkAsync=new ExitNetworkAsync(this);
		exitNetworkAsync.execute(networkName,userID);
	}

	@Override
	public void afterGroupExit() {
		// TODO Auto-generated method stub
		Intent i=new Intent("com.coolcreation.network.myNetwork");
		i.putExtra("userID", userID);
		startActivity(i);
		this.finish();
		
	}
	
}// NetworkDetail
