package intient.saml2.validator.bean;

import java.util.GregorianCalendar;

public class RequestValidity {
	
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
	 */
	public enum Cause { 
		VERSION, ID, ID_DUPLICATE, ISSUEINSTANT, ISSUEINSTANT_EXCEED_SKEW, 
		ISSUER, DESTINATION
	}
	
	// Considered state (and cause of errors) based on assertion validation checks
	private Status status;
	private Cause cause;

	// Request Details
	private String id;
	private GregorianCalendar issueInstant;
	private String issuer;
	
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
}
