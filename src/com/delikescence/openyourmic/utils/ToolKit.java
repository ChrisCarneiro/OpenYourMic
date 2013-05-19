package com.delikescence.openyourmic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * This class is a set of useful methods that aren't directly related to the purpose of the whole application
 * @author Chris Carneiro
 *
 */

public class ToolKit {
	
	//private static String LOG =ToolKit.class.getName();
	
	private ToolKit(){throw new AssertionError();}//prevents instantiation
	/**
	 * @param context
	 * @return whether the device is connected to the Internet
	 */
	public static boolean isNetworkAvailable(Context context){
		
		boolean status=false;
	    try{
	        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	        {
	            status= true;
	        }
	        else 
	        {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	            {
	                status= true;
	            }
	        }
	    }catch(Exception e){
	        e.printStackTrace();  
	        return false;
	    }
	    return status;
	}

}
