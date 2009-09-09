package intient.saml2.identifiers.impl;

import intient.saml2.identifiers.exception.IdentifierCollisionException;
import intient.saml2.identifiers.IdentifierCache;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements IdentifierCache interface to prevent replay attacks as per dictated requirement of SAML 2 specification.
 * 
 * @author Bradley Beddoes
 */
public class IdentifierCacheImpl implements IdentifierCache {
	private ConcurrentMap<String, Date> cache;
	private ReentrantLock lock;

	private Logger logger = LoggerFactory.getLogger(IdentifierCacheImpl.class.getName());

	public IdentifierCacheImpl() {
		this.cache = new ConcurrentHashMap<String, Date>();
		this.lock = new ReentrantLock();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see intient.saml2.identifier.IdentifierCache#registerIdentifier(java.lang.String)
	 */
	public void registerIdentifier(String identifier) throws IdentifierCollisionException {
		this.lock.lock();
		try {
			if (this.containsIdentifier(identifier)) {
				this.logger.error("Identifier collision when attempting to add values to the cache - " + identifier);
				throw new IdentifierCollisionException(
						"Identifier collision when attempting to add values to the cache - " + identifier);
			}
			this.cache.put(identifier, new Date());
			this.logger.debug("Identifier added, cache size now " + cache.size());
			return;
		} finally {
			this.lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see intient.saml2.identifier.IdentifierCache#containsIdentifier(java.lang.String)
	 */
	public boolean containsIdentifier(String identifier) {
		return this.cache.containsKey(identifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see intient.saml2.identifier.IdentifierCache#cleanCache(int)
	 */
	public int cleanCache(int age) {
		int numRemoved = 0;

		Set<Entry<String, Date>> entryList = this.cache.entrySet();
		Iterator<Entry<String, Date>> entryIterator = entryList.iterator();

		while (entryIterator.hasNext()) {
			Entry<String, Date> entry = entryIterator.next();

			long now = new Date().getTime();
			long expire = (entry.getValue().getTime() + (age));

			if (expire < now) {
				entryIterator.remove();
				numRemoved++;
			}
		}

		return numRemoved;
	}
}
