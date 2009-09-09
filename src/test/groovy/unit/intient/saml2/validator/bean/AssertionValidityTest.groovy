package intient.saml2.validator.bean

import groovy.util.GroovyTestCase

import intient.saml2.constants.*
import org.w3._2000._09.xmldsig_.KeyInfo

public class AssertionValidityTest extends GroovyTestCase {

	void testStatus() {
		def av = new AssertionValidity()
		assertNull av.status
		av.status = AssertionValidity.Status.VALID
		assertTrue av.status == AssertionValidity.Status.VALID

		av.status = AssertionValidity.Status.INDETERMINATE
		assertTrue av.status == AssertionValidity.Status.INDETERMINATE
	}

	void testCause() {
		def av = new AssertionValidity()
		assertNull av.cause

		av.cause = AssertionValidity.Cause.CONDITIONS_NOTONAFTER
		assertTrue av.cause == AssertionValidity.Cause.CONDITIONS_NOTONAFTER

		av.cause = AssertionValidity.Cause.VERSION
		assertTrue av.cause == AssertionValidity.Cause.VERSION
	}

	void testID() {
		def av = new AssertionValidity()
		assertNull av.id
		av.id = '123'
		assertTrue av.id.equals("123")
	}

	void testIssueInstant() {
		def av = new AssertionValidity()
		assertNull av.issueInstant

		def time = [:] as GregorianCalendar
		av.issueInstant = time
		assertEquals av.issueInstant, time
	}

	void testIssuer() {
		def av = new AssertionValidity()
		assertNull av.issuer
		av.issuer = "http://sp1.example.com"
		assertTrue av.issuer.equals("http://sp1.example.com")
	}

	void testSubjectConfirmationMethods() {
		def av = new AssertionValidity()
		assertNotNull av.subjectConfirmationMethods

		def subjectConfirmationMethods = [ConfirmationMethodConstants.holderOfKey, ConfirmationMethodConstants.bearer]
        av.subjectConfirmationMethods = subjectConfirmationMethods

        assertEquals av.subjectConfirmationMethods, subjectConfirmationMethods
        assertTrue av.subjectConfirmationMethods.contains(ConfirmationMethodConstants.holderOfKey)
        assertTrue av.subjectConfirmationMethods.contains(ConfirmationMethodConstants.bearer)
	}

	void testSubjectConfirmationKeyInfo() {
		def av = new AssertionValidity()
		assertNotNull av.subjectConfirmationKeyInfo

		def key = [:] as KeyInfo
		def subjectConfirmationKeyInfo = [key]
        av.subjectConfirmationKeyInfo = subjectConfirmationKeyInfo
        assertTrue av.subjectConfirmationKeyInfo.contains(key)
	}

	void testOneTimeUse() {
		def av = new AssertionValidity() 
		assertFalse av.oneTimeUse
		av.oneTimeUse = true
		assertTrue av.oneTimeUse
	}

	void testProxyRestriction() {
		def av = new AssertionValidity() 
		assertFalse av.proxyRestriction
		av.proxyRestriction = true
		assertTrue av.proxyRestriction
	}
}