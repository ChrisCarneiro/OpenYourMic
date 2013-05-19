package com.delikescence.openyourmic;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delikescence.openyourmic.beans.EventBean;
import com.delikescence.openyourmic.fragment.GoogleMapFragt;
import com.delikescence.openyourmic.receivers.OpenYourMicReceiver;
import com.delikescence.openyourmic.services.EventService;
import com.delikescence.openyourmic.utils.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


public class HomeActivity extends Activity implements OpenYourMicReceiver.BroadcastReceivable , View.OnClickListener {	
	
	private OpenYourMicReceiver mMainActivityReceiver;	
	private Intent mIntentHome;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		mMainActivityReceiver = new OpenYourMicReceiver(new Handler());
		mMainActivityReceiver.setActivity(this);	
		mIntentHome = new Intent(this, EventService.class);
		
		Button homeAttendButton = (Button)(findViewById(R.id.home_attend_button));
		homeAttendButton.setOnClickListener(this);
		//homeAttendButton.setOnClickListener(onClickHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
//	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//        	System.out.println("update");
//        	updateUI(intent);       
//        }
//    };   
  
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

	@Override
	public void onReceiveBroadcast(Context context, Intent eventIntent) {
		updateUI(eventIntent);
	}
	
	@Override
	public void onResume() {
		super.onResume();	
		IntentFilter filter = new IntentFilter(Constants.REST_RESULT);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		Assert.assertNotNull("[SERVICE] IntentActivity null", mIntentHome);
		startService(mIntentHome);
		
		Assert.assertNotNull("The receiver should't be null at this point", mMainActivityReceiver);
		registerReceiver(mMainActivityReceiver,filter);	
	}

	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(mMainActivityReceiver);
		stopService(mIntentHome); 		
	}	
	/**
	 * Update The UIThread with the data the EventBean(Received from the intent) holds
	 * @param Intent
	 */
    private void updateUI(Intent eventIntent) {
    	if(Constants.REST_RESULT.equals(eventIntent.getAction())) 
    	{
			EventBean openYourMicEvent = (EventBean) eventIntent.getSerializableExtra(Constants.RESPONSE_OBJECT);		
			if(openYourMicEvent != null) 
			{				
				TextView homeEventTitle = (TextView)findViewById(R.id.home_event_title_id);
				TextView homeEventDescription = (TextView)findViewById(R.id.home_event_description_id);
				
				homeEventTitle.setText(openYourMicEvent.getEventTitle());
				homeEventDescription.setText(openYourMicEvent.getEventDescription());
				
				double eventLatitude = openYourMicEvent.getEventGeoLocation().get(Constants.EVENT_LATITUDE_INDEX);
				double eventLongitude = openYourMicEvent.getEventGeoLocation().get(Constants.EVENT_LONGITUDE_INDEX);
				
				//TODO Change everything here to a fragmentActivity in a separate class and include the support library v4.
				GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.home_gMapView)).getMap();
				if(gMap != null) {
					new GoogleMapFragt(eventLatitude, eventLongitude, gMap);
				}
			}
		} 	
    }

//    private OnClickListener onClickHandler = new OnClickListener(){
//
//        @Override
//        public void onClick(View homeActivity) {
//            
//        	 Toast.makeText(getApplicationContext(), "You just clicked!", Toast.LENGTH_SHORT).show();
//        }
//    };

	@Override
	public void onClick(View v) {
		Toast.makeText(getApplicationContext(), "You just clicked!", Toast.LENGTH_SHORT).show();
		Intent registerToOpenMic = new Intent(getApplicationContext(), RegistrationActivity.class);
		startActivity(registerToOpenMic);
	}
}
