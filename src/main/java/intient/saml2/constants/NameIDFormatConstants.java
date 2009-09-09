package intient.saml2.constants;

/**
 * Stores references to all SAML 2.0 name format values. References: saml-core-2.0-os.pdf, 3.4.1.1 and 8.3
 * 
 * @author Bradley Beddoes
 */
public class NameIDFormatConstants {

	/**
	 * The special Format value urn:oasis:names:tc:SAML:2.0:nameid-format:encrypted indicates that the resulting
	 * assertion(s) MUST contain <EncryptedID> elements instead of plaintext. The underlying name identifier's
	 * unencrypted form can be of any type supported by the identity provider for the requested subject.
	 */
	public static final String encrypted = "urn:oasis:names:tc:SAML:2.0:nameid-format:encrypted";

	/** The interpretation of the content of the element is left to individual implementations. */
	public static final String unspecified = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";

	/**
	 * Indicates that the content of the element is in the form of an email address, specifically "addr-spec" as defined
	 * in IETF RFC 2822 [RFC 2822] Section 3.4.1. An addr-spec has the form local-part@domain. Note that an addr-spec
	 * has no phrase (such as a common name) before it, has no comment (text surrounded in parentheses) after it, and is
	 * not surrounded by "<" and ">".
	 */
	public static final String emailAddress = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";

	/**
	 * Indicates that the content of the element is in the form specified for the contents of the <ds:X509SubjectName>
	 * element in the XML Signature Recommendation [XMLSig]. Implementors should note that the XML Signature
	 * specification specifies encoding rules for X.509 subject names that differ from the rules given in IETF RFC 2253
	 * [RFC 2253].
	 */
	public static final String X509SubjectName = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";

	/**
	 * Indicates that the content of the element is a Windows domain qualified name. A Windows domain qualified user
	 * name is a string of the form "DomainName\UserName". The domain name and "\" separator MAY be omitted.
	 */
	public static final String windowsDomainQualName = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";

	/**
	 * Indicates that the content of the element is in the form of a Kerberos principal name using the format
	 * name[/instance]@REALM. The syntax, format and characters allowed for the name, instance, and realm are described
	 * in IETF RFC 1510 [RFC 1510].
	 */
	public static final String kerberos = "urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos";

	/**
	 * Indicates that the content of the element is the identifier of an entity that provides SAML-based services (such
	 * as a SAML authority, requester, or responder) or is a participant in SAML profiles (such as a service provider
	 * supporting the browser SSO profile). Such an identifier can be used in the <Issuer> element to identify the
	 * issuer of a SAML request, response, or assertion, or within the <NameID> element to make assertions about system
	 * entities that can issue SAML requests, responses, and assertions. It can also be used in other elements and
	 * attributes whose purpose is to identify a system entity in various protocol exchanges. The syntax of such an
	 * identifier is a URI of not more than 1024 characters in length. It is RECOMMENDED that a system entity use a URL
	 * containing its own domain name to identify itself. The NameQualifier, SPNameQualifier, and SPProvidedID
	 * attributes MUST be omitted.
	 */
	public static final String entity = "urn:oasis:names:tc:SAML:2.0:nameid-format:entity";

	/**
	 * Indicates that the content of the element is a persistent opaque identifier for a principal that is specific to
	 * an identity provider and a service provider or affiliation of service providers. Persistent name identifiers
	 */
	public static final String persistent = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";

	/**
	 * Indicates that the content of the element is an identifier with transient semantics and SHOULD be treated as an
	 * opaque and temporary value by the relying party. Transient identifier values MUST be generated in accordance with
	 * the rules for SAML identifiers (see Section 1.3.4), and MUST NOT exceed a length of 256 characters.
	 */
	public static final String trans = "urn:oasis:names:tc:SAML:2.0:nameid-format:transient";
}
