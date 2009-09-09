package intient.saml2.constants;

/**
 * Stores references to all SAML 2.0 attribute format values. Reference: saml-core-2.0-os.pdf, 8.2
 * 
 * @author Bradley Beddoes
 */
public class AttributeFormatConstants {
	/**
	 * The interpretation of the attribute name is left to individual implementations.
	 */
	public static final String unspecified = "urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified";

	/**
	 * The attribute name follows the convention for URI references [RFC 2396].
	 */
	public static final String uri = "urn:oasis:names:tc:SAML:2.0:attrname-format:uri";

	/**
	 * The class of strings acceptable as the attribute name MUST be drawn from the set of values belonging to the
	 * primitive type xs:Name as defined in [Schema2] Section 3.3.6.
	 */
	public static final String basic = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";

}
