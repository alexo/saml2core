package intient.saml2.validator.impl;

import groovy.util.GroovyTestCase;
import intient.saml2.identifiers.impl.*
import intient.saml2.identifiers.exception.*
import intient.saml2.validator.bean.*
import intient.saml2.constants.ConfirmationMethodConstants;
import intient.saml2.constants.StatusCodeConstants;
import intient.saml2.constants.VersionConstants

import org.oasis.saml2.protocol.*

import javax.xml.datatype.XMLGregorianCalendar
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory

public class StatusResponseValidatorTest extends GroovyTestCase  {
	
	String id, id2
	XMLGregorianCalendar calendar
	
	void testInvalidVersion() {
		def (rval, res, rec) = basic()
		
		res.version = "invalid string"
		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.VERSION
	}

	void testNullID() {
		def (rval, res, rec) = basic()
		
		res.version = VersionConstants.saml20
		res.id = null
		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.ID
	}

	void testDuplicateID() {
		def (rval, res, rec) = basic()

		def registerIdentifier = { String genID -> throw new IdentifierCollisionException("test ICE") }
		def identCache = [registerIdentifier: registerIdentifier] as IdentifierCacheImpl
		rval.identifierCache = identCache
		
		res.version = VersionConstants.saml20
		res.id = id
		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.ID_DUPLICATE
	}

	void testNullIssueInstant() {
		def (rval, res, rec) = basicWithCache()
	
		res.version = VersionConstants.saml20
		res.id = id
		res.issueInstant = null
		
		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.ISSUEINSTANT
	}

	void testSkewedIssueInstant() {
		def (rval, res, rec) = basicWithCache()
	
		def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.HOUR, 1)
		XMLGregorianCalendar calendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);
		res.version = VersionConstants.saml20
		res.id = id
		res.issueInstant = calendar
		
		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.ISSUEINSTANT_EXCEED_SKEW
	}

	void testInvalidDestination() {
		def (rval, res, rec) = basicWithCache()
		
		res.version = VersionConstants.saml20
		res.id = id
		res.issueInstant = calendar
		res.destination = "http://service.example.com"

		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.DESTINATION
	}

	void testValidDestination() {
		def (rval, res, rec) = basicWithCache()
		
		res.version = VersionConstants.saml20
		res.id = id
		res.issueInstant = calendar
		res.destination = "http://service.example.com"
		res.status = new Status()
		res.status.statusCode = new StatusCode()
		res.status.statusCode.value = StatusCodeConstants.success

		rec.localDestinations.add("http://service.example.com")

		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.VALID
	}

	void testValidStatus() {
		def (rval, res, rec) = basicWithCache()
		
		res.version = VersionConstants.saml20
		res.id = id
		res.issueInstant = calendar
		res.status = new Status()
		res.status.statusCode = new StatusCode()
		res.status.statusCode.value = StatusCodeConstants.success

		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.VALID
	}

	void testInvalidStatus() {
		def (rval, res, rec) = basicWithCache()
		
		res.version = VersionConstants.saml20
		res.id = id
		res.issueInstant = calendar
		res.status = new Status()
		res.status.statusCode = new StatusCode()
		res.status.statusCode.value = StatusCodeConstants.responder

		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.STATUS
		assertTrue rv.statusCode.equals(StatusCodeConstants.responder)
	}

	void testInvalidStatusWithSecondLevelCode() {
		def (rval, res, rec) = basicWithCache()
		
		res.version = VersionConstants.saml20
		res.id = id
		res.issueInstant = calendar
		res.status = new Status()
		res.status.statusCode = new StatusCode()
		res.status.statusCode.value = StatusCodeConstants.responder
		res.status.statusCode.statusCode = new StatusCode()
		res.status.statusCode.statusCode.value = StatusCodeConstants.invalidNameIDPolicy

		def rv = rval.validate(res, rec)
		assertTrue rv.status == ResponseValidity.Status.INVALID
		assertTrue rv.cause == ResponseValidity.Cause.STATUS
		assertTrue rv.statusCode.equals(StatusCodeConstants.responder)
		assertTrue rv.secondLevelStatusCode.equals(StatusCodeConstants.invalidNameIDPolicy)
	}

	// helpers
	void setUp() {
		calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar())
		id = "_123"
		id2 = "_098"
	}
	
	def basic = {
		def rv = new StatusResponseValidatorImpl()
		def res = new Response()	
		def rec = new ResponseRecipient()

		return [rv, res, rec]
	}

	def basicWithCache = {
		def rv = new StatusResponseValidatorImpl()
		def res = new Response()
		def rec = new ResponseRecipient()

		def registerIdentifier = { String genID ->  }
		def containsIdentifier = { if(it.equals(this.id2)) return true; else return false}
		def identCache = [registerIdentifier: registerIdentifier, containsIdentifier : containsIdentifier] as IdentifierCacheImpl
		rv.identifierCache = identCache

		rv.allowedTimeSkew = 120000

		return [rv, res, rec]
	}
}