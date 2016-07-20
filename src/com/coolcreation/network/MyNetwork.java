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
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyNetwork extends Activity implements MyNetworkInterface {

	DisplayMetrics metrics;
	int width;
	int height;
	String userID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_network);
		userID=getIntent().getExtras().getString("userID");
		metrics = this.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		
		FetchIndividualNetworksAsync fetchIndividualNetworksAsync = new FetchIndividualNetworksAsync(
				this);
		fetchIndividualNetworksAsync.execute(userID);
	}

	@Override
	public void printNetworkDetails(String[] joinAdminNetwork,
			String[] ownerFlg) {
		// TODO Auto-generated method stub

		LinearLayout lm = (LinearLayout) findViewById(R.id.linear);

		LinearLayout lm2 = (LinearLayout) findViewById(R.id.linear2);
		
		for (int i = 0; i < joinAdminNetwork.length; i++) {

			if(ownerFlg[i].equalsIgnoreCase("Y")){
			TextView textView = new TextView(this);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(25.0f);
			textView.setBackgroundColor(Color.LTGRAY);
			textView.setText(joinAdminNetwork[i]);
			textView.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			final String id=joinAdminNetwork[i];
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					openNetwork(id,"Y");
				}
			});

			lm.addView(textView);

			View view = new View(this);
			view.setBackgroundColor(Color.WHITE);
			view.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, 3));
			lm.addView(view);
			}//if
		}// for

		for (int i = 0; i < joinAdminNetwork.length; i++) {
			if(ownerFlg[i].equalsIgnoreCase("N")){
			TextView textView = new TextView(this);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(25.0f);
			textView.setBackgroundColor(Color.LTGRAY);
			textView.setText(joinAdminNetwork[i]);
			textView.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			final String id=joinAdminNetwork[i];
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					openNetwork(id,"N");
				}
			});
			lm2.addView(textView);

			View view = new View(this);
			view.setBackgroundColor(Color.WHITE);
			view.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, 3));
			lm2.addView(view);
			}//if
		}// for

	}// printnetworkdetails
	
public void openNetwork(String networkName,String ownerFLG){
     Intent networkDetailActivity = new Intent("com.coolcreation.network.networkDetail");
     networkDetailActivity.putExtra ("networkName",networkName);
     networkDetailActivity.putExtra ("ownerFLG",ownerFLG);
     networkDetailActivity.putExtra ("userID",userID);
     startActivity (networkDetailActivity);
	
}//openNetwork
	
	

}// MyNetwork
