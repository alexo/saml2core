package intient.saml2.constants;

/**
 * Stores references to all SAML 2.0 protocol enumeration values. References: saml-metadata-2.0-os.pdf, 2.4.1
 * 
 * @author Bradley Beddoes
 */
public class ProtocolConstants {
	/**
	 * A whitespace-delimited set of URIs that identify the set of protocol specifications supported by the role
	 * element. For SAML V2.0 entities, this set MUST include the SAML protocol namespace URI,
	 * urn:oasis:names:tc:SAML:2.0:protocol. Note that future SAML specifications might share the same namespace URI,
	 * but SHOULD provide alternate "protocol support" identifiers to ensure discrimination when necessary.
	 */
	public static String protocol = "urn:oasis:names:tc:SAML:2.0:protocol";
}
