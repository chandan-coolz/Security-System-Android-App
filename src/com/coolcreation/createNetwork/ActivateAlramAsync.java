package com.coolcreation.createNetwork;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ActivateAlramAsync  extends
AsyncTask<String, Void, String> {

	Context ctx;
	CreateAlramInterface createAlramInterface;
	// AlertDialog alertDialog;
	private Dialog loadingDialog;

	public ActivateAlramAsync (Context ctx) {
		super();
		this.ctx = ctx;
		createAlramInterface = (CreateAlramInterface) ctx;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		loadingDialog = ProgressDialog.show(ctx, "Please wait", "Activating.....");
	}
	
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

				String loginUrl = "http://www.website4fooddelivery.in/securityalarm/createAlarm.php";
				String response = "";
				 String type=params[0];
				 String time=params[1];
				 String userID=params[2];
				
				 
				//create url object
						try {
							URL url=new URL(loginUrl);
							//create httpurl connection object
                            
							HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
						
							//set request method
							httpURLConnection.setRequestMethod("POST");
							//pass information to server
							httpURLConnection.setDoOutput(true);
							//create Output Stream object
						
							OutputStream os=httpURLConnection.getOutputStream();
						
						//	alertDialog.setMessage("cool1");
							//alertDialog.show();
							BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
							//create a data string in encoded format..for security purpose
							
							String data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"+
									      URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"+
									      URLEncoder.encode("userID","UTF-8")+"="+URLEncoder.encode(userID,"UTF-8");
									         
							 //now write to bufferwriter object
							bufferedWriter.write(data);
						
							bufferedWriter.flush();
							bufferedWriter.close();
							os.close();
							
						
							//now to get response from the server
						
							InputStream  IS=httpURLConnection.getInputStream();
							BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
							
							
							String line="";
							
							while((line=bufferedReader.readLine())!=null){
								
								response+=line+"\n";
							}//while
							
							bufferedReader.close();
							IS.close();
							
							httpURLConnection.disconnect();
							
						
							
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block

							response=e.getMessage();
						} catch (IOException e) {
							// TODO Auto-generated catch block

							response=e.getMessage();
						}
							
						return response;
					

	}//doInBackground
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		loadingDialog.dismiss();
		if(result.contains("Error")){
			Toast.makeText(ctx,"Sorry, Some error occoured", Toast.LENGTH_LONG).show();
			
		} else {
			
			createAlramInterface.alramActive(result);
		}
	}//onPostExecute

}//ActivateAlramAsync
