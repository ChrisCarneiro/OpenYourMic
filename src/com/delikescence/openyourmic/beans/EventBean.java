package com.delikescence.openyourmic.beans;


import java.io.Serializable;
import java.util.List;

public class EventBean implements Serializable{
	
	/**
	 * 
	 * This class is the bean Jackson needs to turn the parsed JSON file into a POJO.
	 * @author Chris Carneiro
	 * 
	 */
	private static final long serialVersionUID = 2529753590174343755L;	
	private int id;
	private String eventTitle;
   	private String eventDescription;
   	private List<Double> eventGeoLocation;
   	
   	public EventBean(){}//Default constructor needed so that Jackson Lib is able to instantiate the class
   	
   	public EventBean(String eventTitle, String eventDescription){
   		
   		this.eventTitle=eventTitle;
   		this.eventDescription=eventDescription;   		
   	}
   	
   	public String getEventTitle(){
		return this.eventTitle;
	}
	public void setEventTitle(String eventTitle){
		this.eventTitle = eventTitle;
	}
   	public int getId(){
		return this.id;
	}
   	public void setId(int id){
		this.id = id;
	}
 	public String getEventDescription(){
		return this.eventDescription;
	}
	public void setEventDescription(String eventDescription){
		this.eventDescription = eventDescription;
	}
 	public List<Double> getEventGeoLocation(){
		return this.eventGeoLocation;
	}
	public void setEventGeoLocation(List<Double> eventGeoLocation){
		this.eventGeoLocation = eventGeoLocation;
	}
	
}
