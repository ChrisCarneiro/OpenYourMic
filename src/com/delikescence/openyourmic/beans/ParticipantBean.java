package com.delikescence.openyourmic.beans;

import java.io.Serializable;

public class ParticipantBean implements Serializable {

	/**
	 * This bean is turned into a Serialized object by the Jackson Library. We're going to use to create the Json String.
	 *  I find using jackson here could be avoided but for consistency's sake and scalability 
	 *  I chose to use it the same way I did with the EventBean
	 *  @see EventBean
	 * 
	 */
	private static final long serialVersionUID = -8625633373339841708L;

	private String participantFirstname;
	private String participantLastname;
   	private String participantComments;
   	
   	public ParticipantBean(){}
   	
   	public String getParticipantFirstname() {
		return participantFirstname;
	}
	public void setParticipantFirstname(String participantFirstname) {
		this.participantFirstname = participantFirstname;
	}
	public String getParticipantLastname() {
		return participantLastname;
	}
	public void setParticipantLastname(String participantLastname) {
		this.participantLastname = participantLastname;
	}
	public String getParticipantComments() {
		return participantComments;
	}
	public void setParticipantComments(String participantComments) {
		this.participantComments = participantComments;
	}
	
}
