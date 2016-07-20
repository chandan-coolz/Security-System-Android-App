package com.coolcreation.dboperation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOperation extends SQLiteOpenHelper {

	// db object;

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "cooluserManager";

	// Table Name
	private static final String TABLE_NAME = "cool_user_login_1234655889";

	public DbOperation(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		// TODO Auto-generated constructor stub
	}// DbOperation

	@Override
	public void onCreate(SQLiteDatabase dbObj) {
		// TODO Auto-generated method stub
		// Database creation sql statement

	}// oncreate

	// to check weather table exist or not

	public boolean doesTableExist() {
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery(
				"select DISTINCT tbl_name from sqlite_master where tbl_name = '"
						+ TABLE_NAME + "'", null);

		if (cursor != null) {
			if (cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
			cursor.close();
		}
		return false;
	}

	// to crete table

	public void createTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String DATABASE_CREATE = "create table " + TABLE_NAME + "(id "
				+ " integer primary key autoincrement, user_id "
				+ " text not null);";

		db.execSQL(DATABASE_CREATE);
	}

	// to write a recod

	// to add record
	public void addRecord(String cooluser_id) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("id", 1); // id
		values.put("user_id", cooluser_id); // cooluser_id

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection

	}// addRecord

	// get a record

	public String readRecord() {
		SQLiteDatabase db = this.getReadableDatabase();
		String cooluser_id = "";
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME
				+ " where id='1'", null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {

				// cooluser_id= cursor.getString(1);
				cursor.moveToFirst();
				cooluser_id = cursor
						.getString(cursor.getColumnIndex("user_id"));

			}

			cursor.close();
		}

		return cooluser_id;
	}// readRecord

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

	}// onUpgrade

}// DbOperation
