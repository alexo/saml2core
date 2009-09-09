package intient.saml2.validator.bean;

import java.util.GregorianCalendar;

public class ResponseValidity {

	public enum Status {
		VALID, INVALID, INDETERMINATE
	}
	
	/*
	 * VERSION - Invalid SAML version string 
	 * ID - Invalid assertion ID 
	 * ID_DUPLICATE - ID was previously registered, could indicate MIM attack or other problems 
	 * ISSUEINSTANT - Issue Instant was not supplied in the assertion
	 * ISSUEINSTANT_EXCEED_SKEW - Document issue Instant exceeded allowed time skew 
	 * ISSUER - Assertion issuer was null
	 * DESTINATION - URI indicated in request as destination is not supported by the recipient
	 * INRESPONSETO - The ID supplied for InResponseTo was not generated locally
	 * STATUS - The status code of the response was not success
	 */
	public enum Cause { 
		VERSION, ID, ID_DUPLICATE, ISSUEINSTANT, ISSUEINSTANT_EXCEED_SKEW, 
		ISSUER, DESTINATION, INRESPONSETO, STATUS
	}
	
	// Considered state (and cause of errors) based on assertion validation checks
	private Status status;
	private Cause cause;

	// Response Details
	private String id;
	private GregorianCalendar issueInstant;
	private String issuer;
	
	private String statusCode;
	private String secondLevelStatusCode;
	private String statusMessage;
	
	// Bean me up scotty
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Cause getCause() {
		return cause;
	}
	public void setCause(Cause cause) {
		this.cause = cause;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GregorianCalendar getIssueInstant() {
		return issueInstant;
	}
	public void setIssueInstant(GregorianCalendar issueInstant) {
		this.issueInstant = issueInstant;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getSecondLevelStatusCode() {
		return secondLevelStatusCode;
	}
	public void setSecondLevelStatusCode(String secondLevelStatusCode) {
		this.secondLevelStatusCode = secondLevelStatusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
}
