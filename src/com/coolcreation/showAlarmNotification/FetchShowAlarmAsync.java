package com.coolcreation.showAlarmNotification;

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

import android.content.Context;
import android.os.AsyncTask;

public class FetchShowAlarmAsync extends AsyncTask<String, Void, String> {

	Context ctx;
	ShowAlarmInterface showAlarmInterface;

	// AlertDialog alertDialog;
	// private Dialog loadingDialog;

	public FetchShowAlarmAsync(Context ctx) {
		super();
		this.ctx = ctx;
		showAlarmInterface = (ShowAlarmInterface) ctx;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		// loadingDialog = ProgressDialog.show(ctx, "Please wait", "......");
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		String loginUrl = "http://www.website4fooddelivery.in/securityalarm/fetchShowAlarmEntity.php";
		String response = "";
		String userID = params[0];

		// create url object
		try {
			URL url = new URL(loginUrl);
			// create httpurl connection object

			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();

			// set request method
			httpURLConnection.setRequestMethod("POST");
			// pass information to server
			httpURLConnection.setDoOutput(true);
			// create Output Stream object

			OutputStream os = httpURLConnection.getOutputStream();

			// alertDialog.setMessage("cool1");
			// alertDialog.show();
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			// create a data string in encoded format..for security purpose

			String data = URLEncoder.encode("userID", "UTF-8") + "="
					+ URLEncoder.encode(userID, "UTF-8");

			// now write to bufferwriter object
			bufferedWriter.write(data);

			bufferedWriter.flush();
			bufferedWriter.close();
			os.close();

			// now to get response from the server

			InputStream IS = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(IS, "iso-8859-1"));

			String line = "";

			while ((line = bufferedReader.readLine()) != null) {

				response += line + "\n";
			}// while

			bufferedReader.close();
			IS.close();

			httpURLConnection.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block

			// response=e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			// response=e.getMessage();
		}

		return response;

	}// doInBackground

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		// loadingDialog.dismiss();
		if (result.contains("fail")) {

		} else {
            String[] alarmID=null;
			String[] type = null;
			String[] time = null;
			String[] userName = null;
			String[] phoneNumber = null;
			String[] address = null;
			JSONArray jArray = null;
			JSONObject json_data = null;

			try {
				jArray = new JSONArray(result);
                alarmID = new String[jArray.length()];
				type = new String[jArray.length()];
				time = new String[jArray.length()];
				userName = new String[jArray.length()];
				phoneNumber = new String[jArray.length()];
				address = new String[jArray.length()];

				for (int i = 0; i < jArray.length(); i++) {
					json_data = jArray.getJSONObject(i);
					alarmID[i] = json_data.getString("alarm_id");
					type[i] = json_data.getString("type");
					time[i] = json_data.getString("time");
					userName[i] = json_data.getString("user_name");
					phoneNumber[i] = json_data.getString("phone_number");
					address[i] = json_data.getString("address");

				}// for

			} catch (JSONException e) {
				// TODO Auto-generated catch block

			}

			if (type != null) {
				showAlarmInterface.gotNotificationDetails(type, time, userName,
						phoneNumber, address,alarmID);
			} else {

			}

		}// else
	}// onPostExecute

}// FetchShowAlarmAsync
