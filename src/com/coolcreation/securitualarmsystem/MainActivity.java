package com.coolcreation.securitualarmsystem;

import com.coolcreation.dboperation.DbOperation;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {
    DbOperation dbOperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbOperation = new DbOperation(this);
        if(dbOperation.doesTableExist()){
        	String userID=dbOperation.readRecord();
        	if(userID!=null){
        		//call directly home page
        		Intent i=new Intent("com.coolcreation.mainpage.mainPageAfterLogin"); //call if user have already login
        		i.putExtra("userID",userID);
        		startActivity(i);
        	}else{
        		startActivity(new Intent("android.intent.action.loginActivity"));//start the login activity if table doesnot have value
        	}
        	//go to home page

        	
        } else {
        	dbOperation.createTable();
        	startActivity(new Intent("android.intent.action.loginActivity"));
        }
        
        this.finish();
    }


}
