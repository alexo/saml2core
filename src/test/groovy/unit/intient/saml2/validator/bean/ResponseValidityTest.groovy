package intient.saml2.validator.bean

import groovy.util.GroovyTestCase

public class ResponseValidityTest extends GroovyTestCase  {
	
	void testStatus() {
		def rv = new ResponseValidity()
		assertNull rv.status
		rv.status = ResponseValidity.Status.VALID
		assertTrue rv.status == ResponseValidity.Status.VALID

		rv.status = ResponseValidity.Status.INDETERMINATE
		assertTrue rv.status == ResponseValidity.Status.INDETERMINATE
	}

	void testCause() {
		def rv = new ResponseValidity()
		assertNull rv.cause

		rv.cause = ResponseValidity.Cause.DESTINATION
		assertTrue rv.cause == ResponseValidity.Cause.DESTINATION

		rv.cause = ResponseValidity.Cause.VERSION
		assertTrue rv.cause == ResponseValidity.Cause.VERSION
	}

	void testID() {
		def rv = new ResponseValidity()
		assertNull rv.id
		rv.id = '123'
		assertTrue rv.id.equals("123")
	}

	void testIssueInstant() {
		def rv = new ResponseValidity()
		assertNull rv.issueInstant

		def time = [:] as GregorianCalendar
		rv.issueInstant = time
		assertEquals rv.issueInstant, time
	}

	void testIssuer() {
		def rv = new ResponseValidity()
		assertNull rv.issuer
		rv.issuer = "http://service.example.com"
		assertTrue rv.issuer.equals("http://service.example.com")
	}

	void testStatusCode() {
		def rv = new ResponseValidity()
		assertNull rv.statusCode
		rv.statusCode = "success"
		assertTrue rv.statusCode.equals("success")
	}

	void testSecondLevelStatusCode() {
		def rv = new ResponseValidity()
		assertNull rv.secondLevelStatusCode
		rv.secondLevelStatusCode = "success"
		assertTrue rv.secondLevelStatusCode.equals("success")
	}

	void testStatusMessage() {
		def rv = new ResponseValidity()
		assertNull rv.statusMessage
		rv.statusMessage = "success"
		assertTrue rv.statusMessage.equals("success")
	}
}