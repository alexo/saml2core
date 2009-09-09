package intient.saml2.validator.impl;

import groovy.util.GroovyTestCase;
import intient.saml2.identifiers.impl.*
import intient.saml2.identifiers.exception.*
import intient.saml2.validator.bean.*
import intient.saml2.constants.ConfirmationMethodConstants;
import intient.saml2.constants.VersionConstants

import org.oasis.saml2.protocol.*

import javax.xml.datatype.XMLGregorianCalendar
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory

public class RequestValidatorTest extends GroovyTestCase  {
	
	String id, id2
	XMLGregorianCalendar calendar
	
	void testInvalidVersion() {
		def (rval, req, rec) = basic()
		
		req.version = "invalid string"
		def rv = rval.validate(req, rec)
		assertTrue rv.status == RequestValidity.Status.INVALID
		assertTrue rv.cause == RequestValidity.Cause.VERSION
	}

	void testNullID() {
		def (rval, req, rec) = basic()
		
		req.version = VersionConstants.saml20
		req.id = null
		def rv = rval.validate(req, rec)
		assertTrue rv.status == RequestValidity.Status.INVALID
		assertTrue rv.cause == RequestValidity.Cause.ID
	}

	void testDuplicateID() {
		def (rval, req, rec) = basic()

		def registerIdentifier = { String genID -> throw new IdentifierCollisionException("test ICE") }
		def identCache = [registerIdentifier: registerIdentifier] as IdentifierCacheImpl
		rval.identifierCache = identCache
		
		req.version = VersionConstants.saml20
		req.id = id
		def rv = rval.validate(req, rec)
		assertTrue rv.status == RequestValidity.Status.INVALID
		assertTrue rv.cause == RequestValidity.Cause.ID_DUPLICATE
	}

	void testNullIssueInstant() {
		def (rval, req, rec) = basicWithCache()
	
		req.version = VersionConstants.saml20
		req.id = id
		req.issueInstant = null
		
		def rv = rval.validate(req, rec)
		assertTrue rv.status == RequestValidity.Status.INVALID
		assertTrue rv.cause == RequestValidity.Cause.ISSUEINSTANT
	}

	void testSkewedIssueInstant() {
		def (rval, req, rec) = basicWithCache()
	
		def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.HOUR, 1)
		XMLGregorianCalendar calendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);
		req.version = VersionConstants.saml20
		req.id = id
		req.issueInstant = calendar
		
		def rv = rval.validate(req, rec)
		assertTrue rv.status == RequestValidity.Status.INVALID
		assertTrue rv.cause == RequestValidity.Cause.ISSUEINSTANT_EXCEED_SKEW
	}

	void testInvalidDestination() {
		def (rval, req, rec) = basicWithCache()
		
		req.version = VersionConstants.saml20
		req.id = id
		req.issueInstant = calendar
		req.destination = "http://service.example.com"

		def rv = rval.validate(req, rec)
		assertTrue rv.status == RequestValidity.Status.INVALID
		assertTrue rv.cause == RequestValidity.Cause.DESTINATION
	}

	void testValidDestination() {
		def (rval, req, rec) = basicWithCache()
		
		req.version = VersionConstants.saml20
		req.id = id
		req.issueInstant = calendar
		req.destination = "http://service.example.com"

		rec.localDestinations.add("http://service.example.com")

		def rv = rval.validate(req, rec)
		assertTrue rv.status == RequestValidity.Status.VALID
	}

	// helpers
	void setUp() {
		calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar())
		id = "_123"
		id2 = "_098"
	}
	
	def basic = {
		def rv = new RequestValidatorImpl()
		def req = new AuthnRequest()	
		def rec = new RequestRecipient()

		return [rv, req, rec]
	}

	def basicWithCache = {
		def rv = new RequestValidatorImpl()
		def req = new AuthnRequest()
		def rec = new RequestRecipient()

		def registerIdentifier = { String genID ->  }
		def containsIdentifier = { if(it.equals(this.id2)) return true; else return false}
		def identCache = [registerIdentifier: registerIdentifier, containsIdentifier : containsIdentifier] as IdentifierCacheImpl
		rv.identifierCache = identCache

		rv.allowedTimeSkew = 120000

		return [rv, req, rec]
	}
}