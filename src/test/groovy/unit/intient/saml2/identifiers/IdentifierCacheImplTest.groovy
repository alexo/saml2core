package intient.saml2.identifiers

import java.util.Date;

import groovy.util.GroovyTestCase

import intient.saml2.identifiers.impl.*
import intient.saml2.identifiers.exception.*

class IdentifierCacheImplTest extends GroovyTestCase {

	void testRegisterIdentifier() {
		def ic = new IdentifierCacheImpl()

		assertFalse ic.containsIdentifier('test')
		assertFalse ic.containsIdentifier('test2')
		
		ic.registerIdentifier('test')
		ic.registerIdentifier('test2')
		
		assertTrue ic.containsIdentifier('test2')
		assertTrue ic.containsIdentifier('test')
	}

	void testRegisterIdentifierMultiple() {
		def ic = new IdentifierCacheImpl()

		assertFalse ic.containsIdentifier('test79')
		(0 .. 100).each {
			ic.registerIdentifier("test$it")
		}
		assertTrue ic.containsIdentifier('test79')
	}

	void testRegisterIdentifierDuplicate() {
		def ic = new IdentifierCacheImpl()

		assertFalse ic.containsIdentifier('test')
		assertFalse ic.containsIdentifier('test2')
		
		ic.registerIdentifier('test')
		assertTrue ic.containsIdentifier('test')
		assertFalse ic.containsIdentifier('test2')
		
		def failure = shouldFail(IdentifierCollisionException.class) {
			ic.registerIdentifier('test')
		}

		assertTrue failure.contains("Identifier collision when attempting to add values to the cache - test")

		// Ensure subsequent success
		ic.registerIdentifier('test2')
		assertTrue ic.containsIdentifier('test2')
	}

	void testCleanCache() {
		def ic = new IdentifierCacheImpl()
		
		(0 .. 100).each {
			ic.registerIdentifier("test$it")
			assertTrue ic.containsIdentifier("test$it")
		}
		(101 .. 200).each {
			assertFalse ic.containsIdentifier("test$it")
		}
		
		sleep(200)

		(101 .. 200).each {
			ic.registerIdentifier("test$it")
			assertTrue ic.containsIdentifier("test$it")
		}
		(0 .. 100).each {
			assertTrue ic.containsIdentifier("test$it")
		}

		ic.cleanCache(150)	
		(0 .. 100).each {
			assertFalse ic.containsIdentifier("test$it")
		}
		(101 .. 200).each {
			assertTrue ic.containsIdentifier("test$it")
		}
	}
	
}