package com.coolcreation.network;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetNetworkNameAsync extends AsyncTask<String, String, String> {

	Context ctx;
	FetchCompleted fetchCompleted;
	// AlertDialog alertDialog;
	private Dialog loadingDialog;

	public GetNetworkNameAsync(Context ctx) {
		super();
		this.ctx = ctx;
		fetchCompleted = (FetchCompleted) ctx;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		loadingDialog = ProgressDialog.show(ctx, "Please wait", "Fetching Details.....");
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String loginUrl = "http://www.website4fooddelivery.in/securityalarm/fetchNetwork.php";
		String response = "";
		String userID=params[0];
		// create url object
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
		    
			String data = URLEncoder.encode("userID","UTF-8")+"="+URLEncoder.encode(userID,"UTF-8");
					          
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

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return response;

	}// doInBackground

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(result.equalsIgnoreCase("fail")){
			
			Toast.makeText(ctx,"Some, some problem occured", Toast.LENGTH_LONG).show();
		}else {
		
		
		String[] networkName = null;
		JSONArray jArray=null;
		JSONObject json_data = null;
		
		try {
			jArray = new JSONArray(result);
            
			networkName=new String[jArray.length()];

			for (int i = 0; i < jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				networkName[i]=json_data.getString("network_name");

			}// for

		} catch (JSONException e) {
			// TODO Auto-generated catch block
		
		}
		
		loadingDialog.dismiss();
		if(networkName!=null){
		fetchCompleted.onTaskComplete(networkName);
		} else {
			Toast.makeText(ctx,"No Network Available", Toast.LENGTH_LONG).show();
		}

	}//else
	}//onPostExecute

}// GetNetworkNameAsync
