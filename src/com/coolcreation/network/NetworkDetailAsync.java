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

public class NetworkDetailAsync extends AsyncTask<String, Void, String>{

	Context ctx;
	NetworkDetailInterface detailInterface;
	// AlertDialog alertDialog;
	private Dialog loadingDialog;

	public NetworkDetailAsync(Context ctx) {
		super();
		this.ctx = ctx;
		detailInterface = (NetworkDetailInterface) ctx;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		loadingDialog = ProgressDialog.show(ctx, "Please wait", "Loading network detail......");
	}//onPreExecute

	
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String response="";
		String loginUrl="http://www.website4fooddelivery.in/securityalarm/FetchNetworkDetails.php";
		 String networkName=params[0];
		 
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
			    
				String data = URLEncoder.encode("networkName","UTF-8")+"="+URLEncoder.encode(networkName,"UTF-8");          
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
		
        
	}//doInBackground

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		loadingDialog.dismiss();

       if(result.contains("fail")){
			
			Toast.makeText(ctx,"Sorry, some problem occured", Toast.LENGTH_LONG).show();
		}else {
		
		   
			String[] userName = null;
			String[] ownerFLG =null;
			String[] joinedFLG =null;
		
			JSONArray jArray=null;
		    JSONObject json_data = null;
		    
			try {
				jArray = new JSONArray(result);
	            
				userName=new String[jArray.length()];
				ownerFLG=new String[jArray.length()];
				joinedFLG=new String[jArray.length()];
				
				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);
					userName[i]=json_data.getString("user_name");
					ownerFLG[i]=json_data.getString("owner_flg");
					joinedFLG[i]=json_data.getString("join_flag");
	                
				}// for

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Toast.makeText(ctx,e.getMessage(), Toast.LENGTH_LONG).show();
			}
			if(userName!=null){
				detailInterface.printDetails(userName, ownerFLG, joinedFLG);
				} else {
					Toast.makeText(ctx,"No Network Available", Toast.LENGTH_LONG).show();
				}
		
		}//else
	}//onPostExecute
	
	
	
}//NetworkDetailAsync
