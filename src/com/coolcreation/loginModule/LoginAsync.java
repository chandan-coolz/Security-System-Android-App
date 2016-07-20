package com.coolcreation.loginModule;

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

public class LoginAsync extends AsyncTask<String, Void, String> {

	Context ctx;

	// AlertDialog alertDialog;
	private Dialog loadingDialog;
	private LoginInterface loginInterface;

	public LoginAsync(Context ctx) {
		super();
		this.ctx = ctx;
		loginInterface = (LoginInterface) ctx;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		// alertDialog=new AlertDialog.Builder(ctx).create();
		// alertDialog.setTitle("Logging Information");
		loadingDialog = ProgressDialog.show(ctx, "Please wait",
				"Authenticating...");

	}// onpreexecute

	@Override
	protected String doInBackground(String... param) {
		String response = "";
		// TODO Auto-generated method stub
		String loginUrl = "http://www.website4fooddelivery.in/securityalarm/login.php";
		String userName = param[0];
		String userPass = param[1];

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

			String data = URLEncoder.encode("userName", "UTF-8") + "="
					+ URLEncoder.encode(userName, "UTF-8") + "&"
					+ URLEncoder.encode("userPass", "UTF-8") + "="
					+ URLEncoder.encode(userPass, "UTF-8");
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

			return response;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return response;
	}// doinbackground

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}// onprogressupdate

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		loadingDialog.dismiss();
		if (result.contains("success")) {

			loginInterface.afterSuccessLogin();
			/*
			 * alertDialog.setMessage(result); alertDialog.show();
			 */
		} else {

			Toast.makeText(ctx, "Invalid Login", Toast.LENGTH_SHORT).show();
		}
	}// onpostexecute

}// LoginAsync
