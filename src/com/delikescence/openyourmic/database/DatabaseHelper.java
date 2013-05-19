package com.delikescence.openyourmic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	public DatabaseHelper(Context context) {
		super(context,EventContract.DATABASE_NAME,null,EventContract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{		
		database.execSQL(EventContract.SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL(EventContract.SQL_DELETE_ENTRIES);
        onCreate(database);		
	}

	@Override
	public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }
}
