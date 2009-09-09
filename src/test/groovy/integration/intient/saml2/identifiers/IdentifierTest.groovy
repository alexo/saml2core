package intient.saml2.identifiers;

import groovy.util.GroovyTestCase;

import intient.saml2.identifiers.impl.*
import intient.saml2.identifiers.exception.*

import java.util.concurrent.CyclicBarrier

public class IdentifierTest extends GroovyTestCase {

	void testValidConcurrentUniqueGeneration() {
		def ic = new IdentifierCacheImpl()
		def ig = new IdentifierGeneratorImpl(ic)
		def barrier = new CyclicBarrier(500)

		(0 .. 499).collect {
			Thread.start { barrier.await(); 100.times { ig.generateXmlID() } }
		}*.join()

	}	
}