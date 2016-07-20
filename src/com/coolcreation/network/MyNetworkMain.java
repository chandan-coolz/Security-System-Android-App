package com.coolcreation.network;

import com.coolcreation.securitualarmsystem.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MyNetworkMain extends Activity {
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_network_home);
        userID=getIntent().getExtras().getString("userID");
    }

	public void buildNetwork(View view){
	
	Intent i = new Intent("com.coolcreation.network.buildNetwork");	
	i.putExtra("userID",userID);
	startActivity(i);
}//build network
	
	
	public void joinNetwork(View view) {
		Intent i = new Intent("com.coolcreation.network.joinNetwork");	
		i.putExtra("userID",userID);
		startActivity(i);
	}//joinNetwork
	
	public void myNetwork(View view) {

		Intent i = new Intent("com.coolcreation.network.myNetwork");	
		i.putExtra("userID",userID);
		startActivity(i);
	} //myNetwork
	
	
    
} //class
