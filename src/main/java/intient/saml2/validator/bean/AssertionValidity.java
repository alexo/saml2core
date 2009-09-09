package intient.saml2.validator.bean;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.w3._2000._09.xmldsig_.KeyInfo;

public class AssertionValidity {

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
	 * SUBJECT - Assertion was expected to supply subject data but did not
	 * SUBJECT_IDENTIFIER - Assertion subject did not contain BaseID or NameID or EncryptedID
	 * SUBJECT_CONFIRMATION_METHOD - Assertion supplies a method for confirmation that is not acceptable to recipient
	 * SUBJECT_CONFIRMATION_METHOD_HOLDEROFKEY - Assertion sets confirmation method to holder of key but does not supply KeyInfo
	 * SUBJECT_CONFIRMATION_NOTBEFORE - Assertion subject confirmation does not meet required NotBefore requirements
	 * SUBJECT_CONFIRMATION_NOTONAFTER - Assertion subject confirmation was processed after NotOnAfter value
	 * SUBJECT_CONFIRMATION_RECIPIENT - Assertion subject confirmation recipient did match required value
	 * SUBJECT_CONFIRMATION_INRESPONSETO - Assertion subject confirmation specified an InResponseTo ID that was not generated locally
	 * SUBJECT_CONFIRMATION_ADDRESS - Assertion subject confirmation specified a network address as the source which isn't expected
	 * CONDITIONS - Assertion was expected to provide conditions statements but none specified
	 * CONDITIONS_NOTBEFORE - Conditions do not meet required NotBefore requirements
	 * CONDITIONS_NOTONAFTER - Assertion conditions were processed after NotOnAfter value
	 * CONDITIONS_EXTENDED - Assertion conditions included non standard conditions elements which require manual verification
	 * CONDITIONS_AUDIENCE_RESTRICTION - Assertion conditions supplied audience restrictions that didn't match local memberships
	 */
	public enum Cause {
		VERSION, ID, ID_DUPLICATE, ISSUEINSTANT, ISSUEINSTANT_EXCEED_SKEW, 
		ISSUER, SUBJECT, SUBJECT_IDENTIFIER, SUBJECT_CONFIRMATION_METHOD, SUBJECT_CONFIRMATION_METHOD_HOLDEROFKEY,
		SUBJECT_CONFIRMATION_NOTBEFORE, SUBJECT_CONFIRMATION_NOTONAFTER, SUBJECT_CONFIRMATION_RECIPIENT,
		SUBJECT_CONFIRMATION_INRESPONSETO, SUBJECT_CONFIRMATION_ADDRESS,
		CONDITIONS, CONDITIONS_NOTBEFORE, CONDITIONS_NOTONAFTER, CONDITIONS_EXTENDED,
		CONDITIONS_AUDIENCE_RESTRICTION
	}

	// Considered state (and cause of errors) based on assertion validation checks
	private Status status;
	private Cause cause;

	// Assertion Details
	private String id;
	private GregorianCalendar issueInstant;
	private String issuer;
	
	// Subject and confirmation
	private List<String> subjectConfirmationMethods;
	private List<KeyInfo> subjectConfirmationKeyInfo;
	
	// Conditions
	private boolean oneTimeUse;
	private boolean proxyRestriction;

	
	public AssertionValidity() {
		this.subjectConfirmationMethods = new ArrayList<String>();
		this.subjectConfirmationKeyInfo = new ArrayList<KeyInfo>();
	}
	
	// Bean me up Scotty
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isOneTimeUse() {
		return oneTimeUse;
	}

	public void setOneTimeUse(boolean oneTimeUse) {
		this.oneTimeUse = oneTimeUse;
	}

	public boolean isProxyRestriction() {
		return proxyRestriction;
	}

	public void setProxyRestriction(boolean proxyRestriction) {
		this.proxyRestriction = proxyRestriction;
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

	public List<KeyInfo> getSubjectConfirmationKeyInfo() {
		return subjectConfirmationKeyInfo;
	}

	public void setSubjectConfirmationKeyInfo(List<KeyInfo> subjectConfirmationKeyInfo) {
		this.subjectConfirmationKeyInfo = subjectConfirmationKeyInfo;
	}

	public List<String> getSubjectConfirmationMethods() {
		return subjectConfirmationMethods;
	}

	public void setSubjectConfirmationMethods(List<String> subjectConfirmationMethods) {
		this.subjectConfirmationMethods = subjectConfirmationMethods;
	}
}
