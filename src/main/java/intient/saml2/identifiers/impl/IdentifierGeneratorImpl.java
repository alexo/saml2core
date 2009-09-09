package intient.saml2.identifiers.impl;

import intient.saml2.identifiers.exception.IdentifierCollisionException;
import intient.saml2.identifiers.exception.IdentifierGeneratorException;
import intient.saml2.identifiers.IdentifierCache;
import intient.saml2.identifiers.IdentifierGenerator;

import java.security.SecureRandom;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates random identifiers that comply with SAML 2 specification and XML ID requirements.
 * 
 * @author Bradley Beddoes
 */
public class IdentifierGeneratorImpl implements IdentifierGenerator {
	private final String XS_ID_DELIM = "_";
	private final String ID_DELIM = "-";
	private final String RNG = "SHA1PRNG";

	private Logger logger = LoggerFactory.getLogger(IdentifierGeneratorImpl.class.getName());

	private IdentifierCache cache;
	private ReentrantLock lock;
	private SecureRandom random;

	/**
	 * Default constructor
	 * 
	 * @param cache
	 *            An identifier cache instance this generator will validate against
	 */
	public IdentifierGeneratorImpl(IdentifierCache cache) {
		this.cache = cache;
		this.lock = new ReentrantLock();

		try {
			// Attempt to get the specified RNG instance
			this.random = SecureRandom.getInstance(this.RNG);
		} catch (Exception e) {
			this.logger
					.error("Attempt to get SecureRandom instance failed - this MAY result in unsecure entropy for generation of ID's and SHOULD be investigated");
			this.logger.debug(e.getLocalizedMessage(), e);
			this.random = new SecureRandom();
		}

		this.random.setSeed(System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see intient.saml2.identifier.IdentifierGenerator#generateXmlID()
	 */
	public String generateXmlID() {
		String id = this.XS_ID_DELIM;
		id = id + generate(20);

		try {
			this.cache.registerIdentifier(id);
			this.logger.debug("Generated xmlID value " + id);
		} catch (IdentifierCollisionException e) {
			this.logger.error(e.getLocalizedMessage());
			this.logger.debug(e.getLocalizedMessage(), e);
			throw new IdentifierGeneratorException(e.getLocalizedMessage(), e);
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see intient.saml2.identifier.IdentifierGenerator#generateUniqueID()
	 */
	public String generateUniqueID() {
		String id = generate(20);

		try {
			this.cache.registerIdentifier(id);
			this.logger.debug("Generated uniqueID value " + id);
		} catch (IdentifierCollisionException e) {
			this.logger.error(e.getLocalizedMessage());
			this.logger.debug(e.getLocalizedMessage(), e);
			throw new IdentifierGeneratorException(e.getLocalizedMessage(), e);
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see intient.saml2.identifier.IdentifierGenerator#generateSessionID()
	 */
	public String generateSessionID() {
		String id = generate(16) + this.ID_DELIM + System.currentTimeMillis();

		try {
			this.cache.registerIdentifier(id);
			this.logger.debug("Generated SessionID value " + id);
		} catch (IdentifierCollisionException e) {
			this.logger.error(e.getLocalizedMessage());
			this.logger.debug(e.getLocalizedMessage(), e);
			throw new IdentifierGeneratorException(e.getLocalizedMessage(), e);
		}
		return id;
	}

	/**
	 * Generates the specified number of random bytes using SecureRandom. Locks to ensure no possible chance of ID
	 * generation collision.
	 * 
	 * @param length
	 *            The number of random bytes to generate
	 * @return The generated random string
	 */
	private String generate(int length) {
		String id;
		byte[] buf;
		buf = new byte[length];

		this.lock.lock();
		try {
			// Seed the specified RNG instance and get bytes
			this.random.setSeed(Thread.currentThread().getName().getBytes());
			this.random.setSeed(System.currentTimeMillis());
			this.random.nextBytes(buf);
		} finally {
			this.lock.unlock();
		}

		id = new String(Hex.encodeHex(buf));
		return id;
	}

}
