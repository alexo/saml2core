package intient.saml2.identifiers;

import intient.saml2.identifiers.exception.IdentifierCollisionException;

/**
 * Specifies requirements to prevent replay attacks as per dictated requirement of SAML 2 specification.
 * 
 * @author Bradley Beddoes
 */
public interface IdentifierCache {
	/**
	 * Registers an identifier as having been used.
	 * 
	 * @param identifier
	 *            The identifier to add to the cache.
	 * @throws IdentifierCollisionException
	 *             if the identifier exists in the cache.
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	public void registerIdentifier(String identifier) throws IdentifierCollisionException;

	/**
	 * Returns boolean statement determining if the cache holds an identifier or not.
	 * 
	 * @param identifier
	 *            The identifier to check for in the cache
	 * @return true / false
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	public boolean containsIdentifier(String identifier);

	/**
	 * Clear any entries from the cache that are older than specified age.
	 * 
	 * @param age
	 *            The age in milliseconds which an entry remains valid.
	 * @return The number of entries removed from the cache.
	 */
	public int cleanCache(int age);
}
