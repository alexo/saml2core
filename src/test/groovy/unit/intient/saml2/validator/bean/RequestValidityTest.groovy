package intient.saml2.validator.bean

import groovy.util.GroovyTestCase

public class RequestValidityTest extends GroovyTestCase  {
	
	void testStatus() {
		def rv = new RequestValidity()
		assertNull rv.status
		rv.status = RequestValidity.Status.VALID
		assertTrue rv.status == RequestValidity.Status.VALID

		rv.status = RequestValidity.Status.INDETERMINATE
		assertTrue rv.status == RequestValidity.Status.INDETERMINATE
	}

	void testCause() {
		def rv = new RequestValidity()
		assertNull rv.cause

		rv.cause = RequestValidity.Cause.DESTINATION
		assertTrue rv.cause == RequestValidity.Cause.DESTINATION

		rv.cause = RequestValidity.Cause.VERSION
		assertTrue rv.cause == RequestValidity.Cause.VERSION
	}

	void testID() {
		def rv = new RequestValidity()
		assertNull rv.id
		rv.id = '123'
		assertTrue rv.id.equals("123")
	}

	void testIssueInstant() {
		def rv = new RequestValidity()
		assertNull rv.issueInstant

		def time = [:] as GregorianCalendar
		rv.issueInstant = time
		assertEquals rv.issueInstant, time
	}

	void testIssuer() {
		def rv = new RequestValidity()
		assertNull rv.issuer
		rv.issuer = "http://service.example.com"
		assertTrue rv.issuer.equals("http://service.example.com")
	}
}