package com.coolcreation.network;


import com.coolcreation.securitualarmsystem.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class BuildNetwork extends Activity {
	EditText ET_networkName;
	String networkName;
	String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_network);
        userID = getIntent().getExtras().getString("userID");
        
        ET_networkName=(EditText)findViewById(R.id.network_name);
    }

    public void createNetwork(View view){
    	networkName=ET_networkName.getText().toString().trim();
    	createNetwrokAsync async=new createNetwrokAsync(this);
    	async.execute(networkName,userID);
    } //code to create network


    
	

}//Build Netwrod
