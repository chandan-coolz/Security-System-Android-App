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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.coolcreation.securitualarmsystem.R;

public class FetchAlramService extends IntentService {

	HashMap<String, Integer> aIDnIDmap;
	String userID;

	public FetchAlramService() {
		super(FetchAlramService.class.getName());

		// TODO Auto-generated constructor stub
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
       
		aIDnIDmap = new HashMap<String, Integer>();
		return super.onStartCommand(intent, START_REDELIVER_INTENT, startId);
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		userID = arg0.getExtras().getString("userID");
     
		while (true) {

			callFetchShowAlarmAsync();
			cancellNotification();
			callDelereRecIfNotExitInShAmTbl();
		}

	}// onHandleIntent

	// methods here to perform the work

	// method to create notification
	// ********************************************************method for
	// notification creation
	// ************************************************************
	public void createNotification(String type, String time, String userName,
			String phoneNumber, String address, int nID) {

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				getApplicationContext()).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Alarm alert")
				.setContentText(type + " at " + userName);
		mBuilder.setAutoCancel(true);
		Intent intent1 = new Intent(getApplicationContext(),
				DisplayAlramMessage.class);
		intent1.putExtra("type", type);
		intent1.putExtra("time", time);
		intent1.putExtra("userName", userName);
		intent1.putExtra("phoneNumber", phoneNumber);
		intent1.putExtra("address", address);
		intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		TaskStackBuilder stackBuilder = TaskStackBuilder
				.create(getApplicationContext());
		stackBuilder.addParentStack(DisplayAlramMessage.class);
		stackBuilder.addNextIntent(intent1);
		// PendingIntent
		// pendingIntent=PendingIntent.getActivity(getApplicationContext(), 0,
		// intent1, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(nID,
				PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NM.notify(nID, mBuilder.build());

	}// createNotification

	// ***********************************************************method to
	// fetch shaow alarm record
	// *******************************************************
	// method to call fetchShowAlarmAsync

	public void callFetchShowAlarmAsync() {

		String loginUrl = "http://www.website4fooddelivery.in/securityalarm/fetchShowAlarmEntity.php";
		String response = "";

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

		// to do processing after getting result

		if (response.contains("fail")) {

		} else {
			String[] alarmID = null;
			String[] type = null;
			String[] time = null;
			String[] userName = null;
			String[] phoneNumber = null;
			String[] address = null;
			JSONArray jArray = null;
			JSONObject json_data = null;

			try {
				jArray = new JSONArray(response);
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
				gotNotificationDetails(type, time, userName, phoneNumber,
						address, alarmID);
			} else {

			}

		}// else

	}// callFetchShowAlarmAsync

	// function which got notification detail
	// **************************************************caller of create
	// notification
	// method************************************************************
	public void gotNotificationDetails(String[] type, String[] time,
			String[] userName, String[] phoneNumber, String[] address,
			String[] alarmID) {
		// TODO Auto-generated method stub

		for (int i = 0; i < type.length; i++) {

			if (aIDnIDmap.containsKey(alarmID[i])) {

			} else {

				int j = new Random().nextInt();
				aIDnIDmap.put(alarmID[i], j);
				createNotification(type[i], time[i], userName[i],
						phoneNumber[i], address[i], j);

			}
		}// for

		// 2.delete details from show alarm table
		for (Map.Entry m : aIDnIDmap.entrySet()) {

			// Toast.makeText(this,m.getKey().toString() ,
			// Toast.LENGTH_LONG).show();
			callDeleteUserEntryFromShowAlarmTblAsync(m.getKey().toString(),
					userID);

		}

	}// gotNotificationDetails

	// method called to delete the created notification record
	// ************************************************method called to delete
	// record from show
	// table*******************************************************
	public void callDeleteUserEntryFromShowAlarmTblAsync(String alarmID,
			String userID) {

		DeleteUserEntryFromShowAlarmTblAsync deleteUserEntryFromShowAlarmTblAsync = new DeleteUserEntryFromShowAlarmTblAsync();
		deleteUserEntryFromShowAlarmTblAsync.execute(alarmID, userID);

	}// callDeleteUserEntryFromShowAlarmTblAsync

	// ***************************************function to cancell notification
	// based on
	// status*************************************************************

	public void cancellNotification() {
		NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		for (Map.Entry m : aIDnIDmap.entrySet()) {

			// ToauserID);st.makeText(this,m.getKey().toString() ,
			// Toast.LENGTH_LONG).show();
			String status = chckAlarmStatus(m.getKey().toString());

			if (status.contains("D")) {

				NM.cancel(aIDnIDmap.get(m.getKey()));
				aIDnIDmap.remove(m.getKey());
			}

		} // for

	}// cancellNotification

	// ***********************************function to check status of ararm and
	// del notification if status id D
	// *******************************************

	public String chckAlarmStatus(String alarmID) {

		String loginUrl = "http://www.website4fooddelivery.in/securityalarm/ckAlrmStatusForNotificationCancellation.php";
		String response = "";

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

			String data = URLEncoder.encode("alarmID", "UTF-8") + "="
					+ URLEncoder.encode(alarmID, "UTF-8");

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

	}// chckAlarmStatusAndCancellNotification

	// **************************************function to delete the record from
	// create alarm table if not exist in show alarm
	// table*************************

	public void callDelereRecIfNotExitInShAmTbl() {

		deleteRecordIfNotEXinSWalrmTbl deleteRecordIfNotEXinSWalrmTbl = new deleteRecordIfNotEXinSWalrmTbl();
		deleteRecordIfNotEXinSWalrmTbl.execute("start");

	}// callDelereRecIfNotExitInShAmTbl

}// FetchAlram
