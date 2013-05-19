package com.delikescence.openyourmic.fragment;

import com.delikescence.openyourmic.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragt {
	
	//TODO reorganize this crappy class..
	
	public GoogleMapFragt() {
		
		
	}
	
	public GoogleMapFragt(double latitude, double longitude, GoogleMap gMap) {
		
		 LatLng eventLocation= new LatLng(latitude, longitude);
		 
		 gMap.addMarker(new MarkerOptions()
		 		.position(eventLocation)
				.title("Bukowski Bar")
	     		.snippet("Rooooock")
	     		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
	     		);
	     		//.icon(BitmapDescriptorFactory.fromResource(R.drawable.bg_btn_primary)));                 		
		 gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, Constants.DEFAULT_MAP_ZOOM));
		 gMap.animateCamera(CameraUpdateFactory.zoomTo(Constants.DEFAULT_MAP_ZOOM),2000,null);
		
	}
}