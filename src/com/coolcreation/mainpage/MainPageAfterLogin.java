package com.coolcreation.mainpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coolcreation.securitualarmsystem.R;

public class MainPageAfterLogin extends Activity {
    String userID;
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main_after_login);
            userID=getIntent().getExtras().getString("userID");
	    }//onCreate
	 
	 public void goToMyHome(View v){
		 
		Intent i= new Intent("com.coolcreation.createNetwork.myHomeCreateAlarm");
		i.putExtra("userID",userID);
		startActivity(i);
	 }//goToMyHome
	 
	 public void goToMyNetwork(View v){
		
			Intent i= new Intent("com.coolcreation.network.myNetworkMain");
			i.putExtra("userID",userID);
			startActivity(i);
		 
	 }//goToMyNetwork
	 
	 
	 
}//MainPageAfterLogin
