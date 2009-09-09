package intient.saml2.identifiers;

import groovy.util.GroovyTestCase;

import intient.saml2.identifiers.impl.*
import intient.saml2.identifiers.exception.*

class IdentifierGeneratorImplTest extends GroovyTestCase {

	void testGenerateXmlID() {
		def ic = createIdentifierCache()
		def ig = new IdentifierGeneratorImpl(ic)

		def xmlID = ig.generateXmlID()

		assertNotNull xmlID
		assertTrue xmlID.startsWith('_')
		assertTrue xmlID.length() == 41
	}

	void testGenerateUniqueID() {
		def ic = createIdentifierCache()
		def ig = new IdentifierGeneratorImpl(ic)

		def uniqueID = ig.generateUniqueID()

		assertNotNull uniqueID
		assertTrue uniqueID.length() == 40
	}

	void testGenerateUSessionID() {
		def ic = createIdentifierCache()
		def ig = new IdentifierGeneratorImpl(ic)

		def sessionID = ig.generateSessionID()

		assertNotNull sessionID
		assertTrue sessionID.contains('-')
		assertTrue sessionID.length() >= 13
	}

	//helpers
	def createIdentifierCache() {
		def ic = [registerIdentifier: {String identifier -> }] as IdentifierCache
	}
}