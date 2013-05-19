package com.delikescence.openyourmic.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.webkit.URLUtil;

import com.delikescence.openyourmic.beans.EventBean;
import com.delikescence.openyourmic.database.EventContentProvider;
import com.delikescence.openyourmic.database.EventContract;
import com.delikescence.openyourmic.utils.Constants;
import com.delikescence.openyourmic.utils.DBUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventService extends IntentService {
	private static final String EVENTSERVICE = EventService.class.getName();
    
	public EventService() {
		super(EVENTSERVICE);		
	}

	@Override
	protected void onHandleIntent(Intent intent) {		

		try 
		{		
			EventBean eventObject = getEventObject();
			Assert.assertNotNull("Event Object should't be null at this point", eventObject);
			if(eventObject != null) 
			{			
				Intent broadcastIntent = new Intent();
		        broadcastIntent.setAction(Constants.REST_RESULT);	
		        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		        broadcastIntent.putExtra(Constants.RESPONSE_OBJECT, eventObject);
		        sendBroadcast(broadcastIntent);	  
			}
			
		} catch (JsonMappingException jsonMappingEx) {			
			jsonMappingEx.printStackTrace();
			
		} catch (IOException ioEx) {
			ioEx.printStackTrace();	
		}
		
	}	
	
	/**
	 * Parses a remote JSON file	
	 * and returns a serializable eventBean
	 * 
	 * @param URL to the remote JSON file
	 * @return EventBean
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public EventBean getEventObjectFromInternet(String url) throws JsonMappingException, 
																	IOException {	
		EventBean eventObject = null;
	
		if(URLUtil.isHttpUrl(url))
		{
			URL eventPropertiesUrl = new URL(url);							
			ObjectMapper pojoMapper = new ObjectMapper();
			eventObject = pojoMapper.readValue(eventPropertiesUrl, EventBean.class); //POJO Instance		
		}

		return (eventObject  != null ? eventObject : new EventBean(Constants.NO_EVENT,Constants.NO_EVENT_DESC));
	}
	/**
	 * Retrieves event info from Internet 
	 * if it is the first time the app is launched 
	 * or from the SQLite Database
	 * @return EventBean 
	 * @throws JsonMappingException
	 * @throws IOException
	 * @see EventBean
	 */
	public EventBean getEventObject() throws JsonMappingException , 
											 IOException {	
		Cursor dataFromDB = null;
		EventBean eventObject = new EventBean();
		try {
			
			ContentResolver contentResolver = this.getContentResolver();
		
			//Defines the columns that the cursor is gonna select data from.
			final String[] columnsToFetchFrom =
			{ 		EventContract._ID, 
					EventContract.DB_COLUMN_NAME_EVENT_NAME,
					EventContract.DB_COLUMN_NAME_EVENT_DESCRIPTION,
					EventContract.DB_COLUMM_NAME_EVENT_LOC_LATITUDE,
					EventContract.DB_COLUMM_NAME_EVENT_LOC_LONGITUDE
			};
			
			//Fetches the newest event(descending order)
			dataFromDB = contentResolver.query(EventContentProvider.EVENTS_CONTENT_URI, columnsToFetchFrom, null, null, EventContract._ID + " DESC");
			if(dataFromDB != null) 
			{
				final List<Double> listEventLocation= new ArrayList<Double>();
				final int eventTitleColumnIndex = dataFromDB.getColumnIndex(EventContract.DB_COLUMN_NAME_EVENT_NAME);
				final int eventDescriptionColumnIndex = dataFromDB.getColumnIndex(EventContract.DB_COLUMN_NAME_EVENT_DESCRIPTION);
				final int eventLatitudeColumnIndex = dataFromDB.getColumnIndex(EventContract.DB_COLUMM_NAME_EVENT_LOC_LATITUDE);
				final int eventLongitudeColumnIndex = dataFromDB.getColumnIndex(EventContract.DB_COLUMM_NAME_EVENT_LOC_LONGITUDE);		
				
				if(dataFromDB.moveToFirst()) 
				{
					String eventTitle=dataFromDB.getString(eventTitleColumnIndex);
					String eventDescription=dataFromDB.getString(eventDescriptionColumnIndex);
					listEventLocation.add(dataFromDB.getDouble(eventLatitudeColumnIndex));
					listEventLocation.add(dataFromDB.getDouble(eventLongitudeColumnIndex));
						
					eventObject.setEventTitle(eventTitle);
					eventObject.setEventDescription(eventDescription);
					eventObject.setEventGeoLocation(listEventLocation);							
				}
				else
				{		
					eventObject = getEventObjectFromInternet(Constants.URL_TO_EVENT ); //Gets a serializable eventBean.			
					updateEventDatabase(eventObject); //insert into the Database the freshly downloaded data			
				}
				
			}
			
			dataFromDB.close();	
			
		} catch (SQLException sqlEx) {	
			
			sqlEx.printStackTrace();			
		} finally {	
			
			DBUtil.closeConnection(dataFromDB);
		}
		
		return eventObject;
	}
	/**
	 * Takes an eventBean as param 
	 * and insert it into the Database
	 * @param EventBean
	 * @see EventBean
	 */
	private void updateEventDatabase(EventBean eventObject) {
		
		ContentValues eventInfos = new ContentValues();//This object maps column names to the values we want to insert
		ContentResolver contentResolver = getApplicationContext().getContentResolver();
		
		eventInfos.put(EventContract.DB_COLUMN_NAME_EVENT_NAME , 
									eventObject.getEventTitle());
		
		eventInfos.put(EventContract.DB_COLUMN_NAME_EVENT_DESCRIPTION , 
									eventObject.getEventDescription());
		
		eventInfos.put(EventContract.DB_COLUMM_NAME_EVENT_LOC_LATITUDE , 
									eventObject.getEventGeoLocation().get(Constants.EVENT_LATITUDE_INDEX));
		
		eventInfos.put(EventContract.DB_COLUMM_NAME_EVENT_LOC_LONGITUDE , 
									eventObject.getEventGeoLocation().get(Constants.EVENT_LONGITUDE_INDEX));
		
		contentResolver.insert(EventContentProvider.EVENTS_CONTENT_URI, eventInfos);	
	}
	
}
