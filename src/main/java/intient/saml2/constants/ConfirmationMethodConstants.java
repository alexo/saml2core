package intient.saml2.constants;

/**
 * Stores references to all SAML 2.0 confirmation method identifiers values. Reference: saml-profiles-2.0-os.pdf, 3.0
 * 
 * @author Bradley Beddoes
 */
public class ConfirmationMethodConstants {
	/**
	 * One or more <ds:KeyInfo> elements MUST be present within the <SubjectConfirmationData> element. An xsi:type
	 * attribute MAY be present in the <SubjectConfirmationData> element and, if present, MUST be set to
	 * saml:KeyInfoConfirmationDataType (the namespace prefix is arbitrary but must reference the SAML assertion
	 * namespace).
	 */
	public static final String holderOfKey = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";

	/**
	 * Indicates that no other information is available about the context of use of the assertion. The relying party
	 * SHOULD utilize other means to determine if it should process the assertion further, subject to optional
	 * constraints on confirmation using the attributes that MAY be present in the <SubjectConfirmationData> element, as
	 * defined by [SAMLCore].
	 */
	public static final String senderVouches = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";

	/**
	 * The subject of the assertion is the bearer of the assertion, subject to optional constraints on confirmation
	 * using the attributes that MAY be present in the <SubjectConfirmationData> element, as defined by [SAMLCore].
	 */
	public static final String bearer = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
}
