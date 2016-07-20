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

import android.os.AsyncTask;

public class deleteRecordIfNotEXinSWalrmTbl extends
		AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		String loginUrl = "http://www.website4fooddelivery.in/securityalarm/delEntityIFNExistINShowAlarmTbl.php";

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

			bufferedWriter.flush();
			bufferedWriter.close();
			os.close();

			// now to get response from the server

			InputStream IS = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(IS, "iso-8859-1"));

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
		return "done";

	}// doInBackground

}// deleteRecordIfNotEXinSWalrmTbl
