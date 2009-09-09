package intient.saml2.constants;

/**
 * Stores references to all SAML 2.0 bindings values. Reference: saml-bindings-2.0-os.pdf, 3.0
 * 
 * @author Bradley Beddoes
 */
public class BindingConstants {
	/**
	 * The SAML SOAP binding defines how to use SOAP to send and receive SAML requests and responses.
	 */
	public static String soap = "urn:oasis:names:tc:SAML:2.0:bindings:SOAP";

	/**
	 * This binding leverages the Reverse HTTP Binding for SOAP specification [PAOS].
	 */
	public static String paos = "urn:oasis:names:tc:SAML:2.0:bindings:PAOS";

	/**
	 * The HTTP Redirect binding defines a mechanism by which SAML protocol messages can be transmitted within URL
	 * parameters.
	 */
	public static String httpRedirect = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";

	/**
	 * The HTTP POST binding defines a mechanism by which SAML protocol messages may be transmitted within the
	 * base64-encoded content of an HTML form control.
	 */
	public static String httpPost = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";

	/**
	 * In the HTTP Artifact binding, the SAML request, the SAML response, or both are transmitted by reference using a
	 * small stand-in called an artifact. A separate, synchronous binding, such as the SAML SOAP binding, is used to
	 * exchange the artifact for the actual protocol message using the artifact resolution protocol defined in the SAML
	 * assertions and protocols specification [SAMLCore].
	 */
	public static String httpArtifact = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact";

	/**
	 * URIs are a protocol-independent means of referring to a resource. This binding is not a general SAML
	 * request/response binding, but rather supports the encapsulation of a <samlp:AssertionIDRequest> message with a
	 * single <saml:AssertionIDRef> into the resolution of a URI. The result of a successful request is a SAML
	 * <saml:Assertion> element (but not a complete SAML response).
	 */
	public static String uri = "urn:oasis:names:tc:SAML:2.0:bindings:URI";

	/**
	 * Encoding method associated HTTP Redirect Binding
	 */
	public static String deflateEncoding = "urn:oasis:names:tc:SAML:2.0:bindings:URL-Encoding:DEFLATE";

}
