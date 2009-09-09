package intient.saml2.validator.bean;

import java.util.ArrayList;
import java.util.List;

public class AssertionRecipient {
	
	// Validate SubjectConfirmation
	private String recipientURI;
	private List<String> trustedAddresses;
	private List<String> validConfirmationMethods;
	
	// Validate conditions
	private List<String> audienceMemberships;
	
	public AssertionRecipient() {
		this.trustedAddresses = new ArrayList<String>();
		this.validConfirmationMethods = new ArrayList<String>();
		this.audienceMemberships = new ArrayList<String>();
	}

	public String getRecipientURI() {
		return recipientURI;
	}

	public void setRecipientURI(String recipientURI) {
		this.recipientURI = recipientURI;
	}

	public List<String> getTrustedAddresses() {
		return trustedAddresses;
	}

	public void setTrustedAddresses(List<String> trustedAddresses) {
		this.trustedAddresses = trustedAddresses;
	}

	public List<String> getAudienceMemberships() {
		return audienceMemberships;
	}

	public void setAudienceMemberships(List<String> audienceMemberships) {
		this.audienceMemberships = audienceMemberships;
	}

	public List<String> getValidConfirmationMethods() {
		return validConfirmationMethods;
	}

	public void setValidConfirmationMethods(List<String> validConfirmationMethods) {
		this.validConfirmationMethods = validConfirmationMethods;
	}
	
}
