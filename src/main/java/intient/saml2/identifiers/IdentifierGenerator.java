package intient.saml2.identifiers;

/**
 * Generates random identifiers that comply with SAML 2 specification and XML ID requirements.
 * 
 * @author Bradley Beddoes
 */
public interface IdentifierGenerator {
	/**
	 * Generates a random identifier to be used with SAML 2 documents and the ID attribute. Format: _(SHA1 20 bytes)
	 * 
	 * @return Returns a valid identifier
	 */
	public String generateXmlID();

	/**
	 * This function generates a random identifier to be used as a SAML 2 transient NameID. Format: (SHA1 20 bytes)
	 * 
	 * @return Returns a valid identifier
	 */
	public String generateUniqueID();

	/**
	 * This function generates a random identifier to be used in supplying session identifiers to user-agents. Format:
	 * (SHA1 16 bytes)-(EPOCH Milliseconds)
	 * 
	 * @return Returns a valid identifier
	 */
	public String generateSessionID();
}
