package com.delikescence.openyourmic.utils;

public final class Constants {
	
	public static final String REST_RESULT = "com.delikescence.openyourmic.RETRIEVE_EVENT_DATA";
	public static final String URL_TO_EVENT = "http://delikescence.com/openyourmic.json";
	public static final String URL_TO_SERVICE = "http://chris-carneiro.com/participants.php";
	public static final String NO_EVENT = "No event currently scheduled";
	public static final String NO_EVENT_DESC = "We'll Be Back soon!";
	public static final String RESPONSE_STATUS_CODE = "com.delikescence.openyourmic.services.RESPONSE_STATUS_CODE";
    public static final String RESPONSE_OBJECT = "com.delikescence.openyourmic.services.RESPONSE_OBJECT";
    public static final String POST_BEAN = "com.delikescence.openyourmic.POST_PARTICIPANT_DATA";
    public static final String PARTICIPANT_BEAN = "com.delikescence.openyourmic.services.PARTICIPANT_BEAN";
    public static final int EVENT_LATITUDE_INDEX = 0;
    public static final int EVENT_LONGITUDE_INDEX = 1;
    public static final int DEFAULT_MAP_ZOOM = 15;
    
	private Constants(){	//prevents instantiation
		throw new AssertionError();
	} 
}
