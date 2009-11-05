package intient.saml2.helpers.keys;

import java.security.PublicKey;

import org.oasis.saml2.metadata.KeyTypes;

/**
 * Stores important data relating to keys (purpose of the key and the value of the Public Key itself). This was
 * implemented in addition to the JAXB generated KeyDescriptor as it provides much easier access to key data for client
 * applications.
 * 
 * @author Bradley Beddoes
 */
public class KeyData {
	private KeyTypes use;
	private PublicKey pk;

	/**
	 * Generates a key descriptor that stores the purpose of the public key and the public key itself
	 * 
	 * @param use
	 *            purpose of this key
	 * @param pk
	 *            the public key for this descriptor in metadata
	 */
	public KeyData(KeyTypes use, PublicKey pk) {
		super();
		this.use = use;
		this.pk = pk;
	}

	/**
	 * Generates a key descriptor that stores the purpose of the public key and the public key itself
	 * 
	 * @param pk
	 *            the public key for this descriptor in metadata
	 */
	public KeyData(PublicKey pk) {
		super();
		this.pk = pk;
	}

	/**
	 * @return The public key
	 */
	public PublicKey getPk() {
		return this.pk;
	}

	/**
	 * @return The intended use of this key
	 */
	public KeyTypes getUse() {
		return this.use;
	}

}
