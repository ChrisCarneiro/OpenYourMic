package com.delikescence.openyourmic.utils;

import android.database.Cursor;
import android.database.SQLException;

public class DBUtil {
	
	public static void closeConnection (Cursor conn){
	    try {
	        conn.close();
	    } catch(SQLException ex) {
	        System.out.println("Cannot close Cursor");
	        throw new RuntimeException(ex);
	    } 
	}

}
