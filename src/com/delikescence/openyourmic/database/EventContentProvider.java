package com.delikescence.openyourmic.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class EventContentProvider extends ContentProvider{

	private static final String CONTENT_AUTHORITY="com.delikescence.openyourmic.database.EventContentProvider";
	private static final String EVENT_DB_TABLE="events"; //This is the path matching the table name in the contract class
	/**
	 * This variable defines the id by which a single row is retrieved in the events table 
	 * the value can be whatever you want I find 1 is a good one since we use it to fetch one row from the DB
	 */
	private static final int EVENT_ID = 1; 
										
	public static final Uri EVENTS_CONTENT_URI=Uri.parse (
			"content://" + CONTENT_AUTHORITY +"/"+ EVENT_DB_TABLE
			);//Path Structure pointing to the DB table "events"
	
	public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(CONTENT_AUTHORITY, EVENT_DB_TABLE+ "/#", EVENT_ID);	
		sURIMatcher.addURI(CONTENT_AUTHORITY, EVENT_DB_TABLE, EVENT_ID);
	}
		
	private DatabaseHelper mDatabase;
	
	/**
	 * Deletes rows from the provider. Uses the arguments to select the table 
	 * and the rows to delete.
	 * @return the number of rows deleted.
	 */
	@Override
	public int delete(Uri from, String where, String[] selectionArgs) {
		
		int uriType = sURIMatcher.match(from);
	    final SQLiteDatabase sqlDB = mDatabase.getWritableDatabase();
	    int rowsDeleted = 0;
	    
	    if(EVENT_ID == uriType) 
	    {
	    	 String id = from.getLastPathSegment();
	    	 System.out.println("id" +id);
	    	if (TextUtils.isEmpty(where)) {
	    		rowsDeleted = sqlDB.delete(EventContract.DB_TABLE_NAME,
		                    EventContract._ID + "=" + id, null); // Deletes all entries if no selection clause
		    } 
	    	else 
	    	{
	    		rowsDeleted = sqlDB.delete(EventContract.DB_TABLE_NAME,
		                    where + " and " + EventContract._ID + "=" + id,
		                    selectionArgs); //deletes only the row passed in selectionArgs
		    }		    	 
		}
	    else
	    {
	    	 throw new IllegalArgumentException("Unknown or Invalid URI " + from);
	    }
	    
	    getContext().getContentResolver().notifyChange(from, null);
	    return rowsDeleted;
	
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Inserts a new row into the DB through the provider. Uses the arguments to select 
	 * the destination table and to get the column values to use. 
	 * @return a content URI for the newly-inserted row.
	 */
	@Override
	public Uri insert(Uri from, ContentValues eventValues) {

		int uriType = sURIMatcher.match(from);
	    final SQLiteDatabase sqlDB = mDatabase.getWritableDatabase();
	    long row_id = 0;
	    Uri rowsInserted = EVENTS_CONTENT_URI;
	    
	    if(EVENT_ID == uriType)
	    {
	    	row_id = sqlDB.insertOrThrow(EventContract.DB_TABLE_NAME, null, eventValues);
	    	if( row_id > 0) 
	    	{    		
	    		rowsInserted = ContentUris.withAppendedId(EVENTS_CONTENT_URI, row_id);
	    		getContext().getContentResolver().notifyChange(from, null);
	    	}    	
	    }
	    else
	    {    	
	    	throw new IllegalArgumentException("Unknown URI");
	    }
	    
		return rowsInserted;
	}

	@Override
	public boolean onCreate() {
		mDatabase = new DatabaseHelper(getContext());
		return true;
	}
	
	/**
	 * @return the data as a Cursor object.
	 * Retrieves data from the provider. Uses the arguments to select the table to query, 
	 * the rows and columns to return, and the order the result is sorted in. 
	 */
	@Override
	public Cursor query(Uri from, String[] projection, String where, String[] selectionArgs,
			String orderBy) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(EventContract.DB_TABLE_NAME);
		
		 Cursor cursor = queryBuilder.query(mDatabase.getReadableDatabase(),
		            projection, null, selectionArgs, null, null, orderBy);		  
		 cursor.setNotificationUri(getContext().getContentResolver(), from);
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
