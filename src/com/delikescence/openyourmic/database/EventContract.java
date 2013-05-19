package com.delikescence.openyourmic.database;

import android.provider.BaseColumns;

public abstract class EventContract implements BaseColumns {
	
	private EventContract(){throw new AssertionError();}//Prevents this class from Instantiation
	
	//Describes Database schema
	public static final String DATABASE_NAME = "OpenYourMicEvents.db";
	public static final int DATABASE_VERSION = 1;
	public static final String DB_TABLE_NAME = "events";
	public static final String DB_COLUMN_NAME_EVENT_ID = "eventId";
	public static final String DB_COLUMN_NAME_EVENT_NAME = "eventTitle";
	public static final String DB_COLUMN_NAME_EVENT_DESCRIPTION = "eventDescription";
	public static final String DB_COLUMM_NAME_EVENT_LOC_LATITUDE = "eventLocLatitude";
	public static final String DB_COLUMM_NAME_EVENT_LOC_LONGITUDE = "eventLocLongitude";
	
	
	public static final String DB_TEXT_TYPE = " TEXT";
	public static final String DB_COMMA_SEP = ",";
	public static final String DB_REAL_TYPE = " REAL";	
	
	public static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + 	EventContract.DB_TABLE_NAME + " (" +
	    EventContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
	    EventContract.DB_COLUMN_NAME_EVENT_ID + DB_TEXT_TYPE + DB_COMMA_SEP +
	    EventContract.DB_COLUMN_NAME_EVENT_NAME + DB_TEXT_TYPE + DB_COMMA_SEP  + 
	    EventContract.DB_COLUMN_NAME_EVENT_DESCRIPTION + DB_TEXT_TYPE + DB_COMMA_SEP + 
	    EventContract.DB_COLUMM_NAME_EVENT_LOC_LATITUDE + DB_REAL_TYPE + DB_COMMA_SEP  +
	    EventContract.DB_COLUMM_NAME_EVENT_LOC_LONGITUDE + DB_REAL_TYPE +
	    
	    " )";
	
	public static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + EventContract.DB_TABLE_NAME;
}
