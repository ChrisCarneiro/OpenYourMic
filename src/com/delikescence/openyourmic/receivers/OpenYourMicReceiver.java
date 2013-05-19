package com.delikescence.openyourmic.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class OpenYourMicReceiver extends BroadcastReceiver{
	
	private BroadcastReceivable mActivity = null;
	
	public OpenYourMicReceiver(Handler handler)
	{
		super();	
	}

	public BroadcastReceivable getActivity() {
		return mActivity;
	}

	public void setActivity(BroadcastReceivable activity) {
		this.mActivity = activity;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		mActivity.onReceiveBroadcast(context,intent);
	}
	
	public interface BroadcastReceivable
	{	 
		public void onReceiveBroadcast(Context context, Intent intent);		 
	}
}
