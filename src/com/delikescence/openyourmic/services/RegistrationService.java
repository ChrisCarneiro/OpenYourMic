package com.delikescence.openyourmic.services;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;

import com.delikescence.openyourmic.beans.ParticipantBean;
import com.delikescence.openyourmic.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegistrationService extends IntentService {

	private static final String REGISTRATIONSERVICE = RegistrationService.class.getName();
	public RegistrationService() {
		super(REGISTRATIONSERVICE);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try 
		{	
			registerParticipant(intent);	
			
		} catch(IOException ioEx) {	
			
			ioEx.printStackTrace();
		} 
	}
	/**
	 * Sends an http post request to a php Web Service
	 * @param participationIntent
	 * @throws IOException
	 */
	private void registerParticipant(Intent participationIntent) throws IOException {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postMethod = new HttpPost(Constants.URL_TO_SERVICE);
		ParticipantBean participantObject = (ParticipantBean) participationIntent.getSerializableExtra(Constants.PARTICIPANT_BEAN);
			
		String participantInfoJsonString = jsonFromBean(participantObject);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		// "participantData" is the key to get the json from the php $_POST associative array.
		nameValuePairs.add(new BasicNameValuePair("participantData", participantInfoJsonString)); 
		postMethod.setHeader("Content-type", "application/x-www-form-urlencoded");
		
		//Used to send the pair as an encoded url list inside the post request
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
		urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);	
		
		postMethod.setEntity(urlEncodedFormEntity); //this is optional 
		
		/**************************This allows to go through the htaccess authentication*****************
			StringBuilder authentication = new StringBuilder().append("username").append(":").append("pass");
	        String result = Base64.encodeBytes(authentication.toString().getBytes());
	        postMethod.setHeader("Authorization", "Basic " + result);
        */
        
		HttpResponse response= httpClient.execute(postMethod); 
		
		HttpEntity resEntity = response.getEntity();
		String resp = EntityUtils.toString(resEntity);
		System.out.println("response " + resp);
			
	}
	/**
	 * @param bean
	 * @return Json string from a bean using Jackson library
	 * @throws IOException
	 */
	private String jsonFromBean(Object bean) throws IOException   {
		
		ParticipantBean javaBean = (ParticipantBean)bean;
		ObjectMapper pojoMapper = new ObjectMapper();
		
		String jsonString = pojoMapper.writeValueAsString(javaBean); //Creates Json string from a Javabean
		return jsonString;
	}
}
