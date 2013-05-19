package com.delikescence.openyourmic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.delikescence.openyourmic.beans.ParticipantBean;
import com.delikescence.openyourmic.services.RegistrationService;
import com.delikescence.openyourmic.utils.Constants;

public class RegistrationActivity extends Activity implements View.OnClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		Button registrationParticipateButton = (Button)(findViewById(R.id.registration_participate_button_id));
		registrationParticipateButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public void onClick(View registrationForm) {
		ParticipantBean participant = new ParticipantBean();
		
		EditText participantFirstName = (EditText)findViewById(R.id.registration_participant_firstname_id);
		EditText participantLastName = (EditText)findViewById(R.id.registration_participant_lastname_id);
		EditText participantComment = (EditText)findViewById(R.id.registration_participant_comment_id);
		
		String firstname = participantFirstName.getText().toString(); //gets the text the user has input
		String lastname = participantLastName.getText().toString();
		String comment = participantComment.getText().toString();
		
		participant.setParticipantFirstname(firstname);
		participant.setParticipantLastname(lastname);
		participant.setParticipantComments(comment);
		
		Intent participantIntent = new Intent(this, RegistrationService.class);
		
		participantIntent.setAction(Constants.POST_BEAN); 
		participantIntent.addCategory(Intent.CATEGORY_DEFAULT);
		participantIntent.putExtra(Constants.PARTICIPANT_BEAN, participant); 
		
		startService(participantIntent);
		
		Toast.makeText(getApplicationContext(), "Merci! Nous t'appelerons quand ce sera ton tour!", Toast.LENGTH_SHORT).show();
	
	}
}
