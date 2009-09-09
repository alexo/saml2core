package intient.saml2.constants;

/**
 * Stores references to all SAML 2.0 authentication context classes values. Reference: saml-authn-context-2.0-os.pdf,
 * 3.0
 * 
 * @author Bradley Beddoes
 */
public class AuthenticationContextConstants {
	/**
	 * The Internet Protocol class is applicable when a principal is authenticated through the use of a provided IP
	 * address.
	 */
	public static final String internetProtocol = "urn:oasis:names:tc:SAML:2.0:ac:classes:InternetProtocol";

	/**
	 * The Internet Protocol Password class is applicable when a principal is authenticated through the use of a
	 * provided IP address, in addition to a username/password.
	 */
	public static final String internetProtocolPassword = "urn:oasis:names:tc:SAML:2.0:ac:classes:InternetProtocolPassword";

	/**
	 * This class is applicable when the principal has authenticated using a password to a local authentication
	 * authority, in order to acquire a Kerberos ticket.
	 */
	public static final String kerberos = "urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos";

	/**
	 * Reflects mobile customer registration procedures and an authentication of the mobile device without requiring
	 * explicit end-user interaction.
	 */
	public static final String mobileOneFactorUnreg = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorUnregistered";

	/**
	 * Reflects mobile customer registration procedures and a two-factor based authentication, such as secure device and
	 * user PIN.
	 */
	public static final String mobileTwoFactoryUnreg = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorUnregistered";

	/**
	 * Reflects mobile contract customer registration procedures and a single factor authentication.
	 */
	public static final String mobileOneFactorContract = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract";

	/**
	 * Reflects mobile contract customer registration procedures and a two-factor based authentication.
	 */
	public static final String mobileTwoFactorContract = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorContract";

	/**
	 * The Password class is applicable when a principal authenticates to an authentication authority through the
	 * presentation of a password over an unprotected HTTP session.
	 */
	public static final String password = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";

	/**
	 * The PasswordProtectedTransport class is applicable when a principal authenticates to an authentication authority
	 * through the presentation of a password over a protected session.
	 */
	public static final String passwordProtectedTransport = "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport";

	/**
	 * The PreviousSession class is applicable when a principal had authenticated to an authentication authority at some
	 * point in the past using any authentication context supported by that authentication authority. Consequently, a
	 * subsequent authentication event that the authentication authority will assert to the relying party may be
	 * significantly separated in time from the principal's current resource access request. The context for the
	 * previously authenticated session is explicitly not included in this context class because the user has not
	 * authenticated during this session, and so the mechanism that the user employed to authenticate in a previous
	 * session should not be used as part of a decision on whether to now allow access to a resource.
	 */
	public static final String previousSession = "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession";

	/**
	 * The X509 context class indicates that the principal authenticated by means of a digital signature where the key
	 * was validated as part of an X.509 Public Key Infrastructure.
	 */
	public static final String x509 = "urn:oasis:names:tc:SAML:2.0:ac:classes:X509";

	/**
	 * The PGP context class indicates that the principal authenticated by means of a digital signature where the key
	 * was validated as part of a PGP Public Key Infrastructure.
	 */
	public static final String pgp = "urn:oasis:names:tc:SAML:2.0:ac:classes:PGP";

	/**
	 * The SPKI context class indicates that the principal authenticated by means of a digital signature where the key
	 * was validated via an SPKI Infrastructure.
	 */
	public static final String spki = "urn:oasis:names:tc:SAML:2.0:ac:classes:SPKI";

	/**
	 * This context class indicates that the principal authenticated by means of a digital signature according to the
	 * processing rules specified in the XML Digital Signature specification [XMLSig]
	 */
	public static final String xmldsig = "urn:oasis:names:tc:SAML:2.0:ac:classes:XMLDSig";

	/**
	 * The Smartcard class is identified when a principal authenticates to an authentication authority using a
	 * smartcard.
	 */
	public static final String smartcard = "urn:oasis:names:tc:SAML:2.0:ac:classes:Smartcard";

	/**
	 * The SmartcardPKI class is applicable when a principal authenticates to an authentication authority through a
	 * two-factor authentication mechanism using a smartcard with enclosed private key and a PIN.
	 */
	public static final String smartcardPKI = "urn:oasis:names:tc:SAML:2.0:ac:classes:SmartcardPKI";

	/**
	 * The Software-PKI class is applicable when a principal uses an X.509 certificate stored in software to
	 * authenticate to the authentication authority.
	 */
	public static final String softwarePKI = "urn:oasis:names:tc:SAML:2.0:ac:classes:SoftwarePKI";

	/**
	 * This class is used to indicate that the principal authenticated via the provision of a fixed-line telephone
	 * number, transported via a telephony protocol such as ADSL.
	 */
	public static final String telephony = "urn:oasis:names:tc:SAML:2.0:ac:classes:Telephony";

	/**
	 * Indicates that the principal is "roaming" (perhaps using a phone card) and authenticates via the means of the
	 * line number, a user suffix, and a password element.
	 */
	public static final String nomadTelephony = "urn:oasis:names:tc:SAML:2.0:ac:classes:NomadTelephony";

	/**
	 * This class is used to indicate that the principal authenticated via the provision of a fixed-line telephone
	 * number and a user suffix, transported via a telephony protocol such as ADSL.
	 */
	public static final String personalTelephony = "urn:oasis:names:tc:SAML:2.0:ac:classes:PersonalTelephony";

	/**
	 * Indicates that the principal authenticated via the means of the line number, a user suffix, and a password
	 * element.
	 */
	public static final String authenticatedTelephony = "urn:oasis:names:tc:SAML:2.0:ac:classes:AuthenticatedTelephony";

	/**
	 * The Secure Remote Password class is applicable when the authentication was performed by means of Secure Remote
	 * Password as specified in [RFC 2945].
	 */
	public static final String secureRemotePassword = "urn:oasis:names:tc:SAML:2.0:ac:classes:SecureRemotePassword";

	/**
	 * This class indicates that the principal authenticated by means of a client certificate, secured with the SSL/TLS
	 * transport.
	 */
	public static final String tlsClient = "urn:oasis:names:tc:SAML:2.0:ac:classes:TLSClient";

	/**
	 * The TimeSyncToken class is applicable when a principal authenticates through a time synchronization token.
	 */
	public static final String timeSyncToken = "urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken";

	/**
	 * The Unspecified class indicates that the authentication was performed by unspecified means.
	 */
	public static final String unspecified = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";

}
