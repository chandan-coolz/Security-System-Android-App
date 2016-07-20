package com.coolcreation.loginModule;

import com.coolcreation.dboperation.DbOperation;
import com.coolcreation.securitualarmsystem.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class LoginActivity extends Activity implements LoginInterface  {

	EditText ET_userName,ET_userPass;
	String userName,userPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_layout);
        
        ET_userName=(EditText)findViewById(R.id.user_name);
        ET_userPass=(EditText)findViewById(R.id.user_pass);
    }//onCreate
    
    public void userLogin(View view){
    userName=ET_userName.getText().toString().trim();
    userPass=ET_userPass.getText().toString().trim();
    	
    LoginAsync login=new LoginAsync(this);
    login.execute(userName,userPass);
    
    }//userLogin

	@Override
	public void afterSuccessLogin() {
		// TODO Auto-generated method stub
		DbOperation dbOperation=new DbOperation(this);
		dbOperation.addRecord(userName);
		Intent i=new Intent("com.coolcreation.mainpage.mainPageAfterLogin");
		i.putExtra("userID",userName);
		startActivity(i);
		
	}//afterSuccessLogin


	
	
	
	
	
}//class LoginActivity
