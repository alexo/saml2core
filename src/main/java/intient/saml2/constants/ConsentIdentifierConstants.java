package intient.saml2.constants;

/**
 * Stores references to all SAML 2.0 consent identifiers values. References: saml-core-2.0-os.pdf, 8.4
 * 
 * @author Bradley Beddoes
 */
public class ConsentIdentifierConstants {
	/** No claim as to principal consent is being made. */
	public static final String unspecified = "urn:oasis:names:tc:SAML:2.0:consent:unspecified";

	/** Indicates that a principal’s consent has been obtained by the issuer of the message. */
	public static final String obtained = "urn:oasis:names:tc:SAML:2.0:consent:obtained";

	/**
	 * Indicates that a principal’s consent has been obtained by the issuer of the message at some point prior to the
	 * action that initiated the message.
	 */
	public static final String prior = "urn:oasis:names:tc:SAML:2.0:consent:prior";

	/**
	 * Indicates that a principal's consent has been implicitly obtained by the issuer of the message during the action
	 * that initiated the message, as part of a broader indication of consent. Implicit consent is typically more
	 * proximal to the action in time and presentation than prior consent, such as part of a session of activities.
	 */
	public static final String currentImplicit = "urn:oasis:names:tc:SAML:2.0:consent:current-implicit";

}
