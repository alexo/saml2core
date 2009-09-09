package intient.saml2.validator.impl;

import groovy.util.GroovyTestCase;
import groovy.mock.interceptor.*

import org.oasis.saml2.assertion.*
import org.w3._2000._09.xmldsig_.KeyInfo

import intient.saml2.validator.bean.*
import intient.saml2.identifiers.*
import intient.saml2.identifiers.impl.*
import intient.saml2.identifiers.exception.*

import intient.saml2.constants.ConfirmationMethodConstants;
import intient.saml2.constants.VersionConstants

import javax.xml.datatype.XMLGregorianCalendar
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory

public class AssertionValidatorTest extends GroovyTestCase  {

	String id, id2
	XMLGregorianCalendar calendar
		
	void testInvalidVersion() {
		def (aval, ass, rec) = basic()
		
		ass.version = "invalid string"
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.VERSION
	}

	void testNullID() {
		def (aval, ass, rec) = basic()
		
		ass.version = VersionConstants.saml20
		ass.id = null
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.ID
	}

	void testDuplicateID() {
		def (aval, ass, rec) = basic()

		def registerIdentifier = { String genID -> throw new IdentifierCollisionException("test ICE") }
		def identCache = [registerIdentifier: registerIdentifier] as IdentifierCacheImpl
		aval.identifierCache = identCache
		
		ass.version = VersionConstants.saml20
		ass.id = id
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.ID_DUPLICATE
	}

	void testNullIssueInstant() {
		def (aval, ass, rec) = basicWithCache()
	
		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = null
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.ISSUEINSTANT
	}

	void testSkewedIssueInstant() {
		def (aval, ass, rec) = basicWithCache()
	
		def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.HOUR, 1)
		XMLGregorianCalendar calendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);
		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.ISSUEINSTANT_EXCEED_SKEW
	}

	void testNullIssuer() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = null
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.ISSUER
	}

	void testProcessSubjectWithNullSubjectContent() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = null

		aval.processSubject = true
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT
	}

	void testNonProcessSubjectWithSubjectContent() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()

		aval.processSubject = false
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_IDENTIFIER
	}

	void testNonProcessSubjectWithNullSubjectContent() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = null

		aval.processSubject = false
		aval.processConditions = false
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testSubjectWithNullBaseID() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.baseID = null

		aval.processSubject = false
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_IDENTIFIER
	}

	void testSubjectWithNullSubjectConfirmationData() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

		aval.processSubject = false
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testSubjectWithSubjectConfirmationDataNullMethod() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.holderOfKey
		def confirmationData = new SubjectConfirmationDataType()
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_METHOD
	}

	void testSubjectWithSubjectConfirmationDataInvalidMethod() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.holderOfKey
		def confirmationData = new SubjectConfirmationDataType()
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_METHOD
	}

	void testSubjectWithSubjectConfirmationDataInvalidNotBefore() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.HOUR, 1)
		XMLGregorianCalendar calendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = calendar	
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_NOTBEFORE
	}

	void testSubjectWithSubjectConfirmationDataInvalidNotOnAfter() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.HOUR, -1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_NOTONAFTER
	}

	void testSubjectWithSubjectConfirmationDataNullRecipient() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.recipient = null
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		rec.recipientURI = "http://service.example.com"
		aval.processSubjectConfirmationRecipient = true
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_RECIPIENT
	}

	void testSubjectWithSubjectConfirmationDataInvalidRecipient() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.recipient = "http://service2.example.com"
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		rec.recipientURI = "http://service.example.com"
		aval.processSubjectConfirmationRecipient = true
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_RECIPIENT
	}

	void testSubjectWithSubjectConfirmationDataInvalidInResponseToID() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = this.id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.inResponseTo = "abc"
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_INRESPONSETO
	}

	void testSubjectWithSubjectConfirmationDataNullAddress() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = this.id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.inResponseTo = this.id2
		confirmationData.address = null
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		aval.processSubjectConfirmationAddress = true
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_ADDRESS
	}

	void testSubjectWithSubjectConfirmationDataInvalidAddress() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = this.id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.inResponseTo = this.id2
		confirmationData.address = "1.2.3.4"
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		rec.trustedAddresses.add("127.0.0.1")
		aval.processSubjectConfirmationAddress = true
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_ADDRESS
	}

	void testSubjectWithSubjectConfirmationDataValidAddress() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = this.id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.bearer
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.inResponseTo = this.id2
		confirmationData.address = "1.2.3.4"
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		rec.trustedAddresses.add("1.2.3.4")
		aval.processSubjectConfirmationAddress = true
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testSubjectWithSubjectConfirmationDataHolderOfKeyNoKeyInfo() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = this.id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.holderOfKey
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.inResponseTo = this.id2
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		rec.validConfirmationMethods.add(ConfirmationMethodConstants.holderOfKey)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.SUBJECT_CONFIRMATION_METHOD_HOLDEROFKEY
	}

	void testSubjectWithSubjectConfirmationDataHolderOfKeyValidKeyInfo() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = this.id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.subject = new Subject()
		ass.subject.nameID = new NameIDType()
		ass.subject.subjectConfirmation = []

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.MINUTE, 1)
		XMLGregorianCalendar notOnAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		def confirmation = new SubjectConfirmation()
		confirmation.method = ConfirmationMethodConstants.holderOfKey
		def confirmationData = new SubjectConfirmationDataType()
		confirmationData.notBefore = this.calendar	
		confirmationData.notOnOrAfter = notOnAfter
		confirmationData.inResponseTo = this.id2
		def key = [:] as KeyInfo
		confirmationData.content.add(key)
		confirmation.subjectConfirmationData = confirmationData
		ass.subject.subjectConfirmation.add(confirmation)
		
		rec.validConfirmationMethods.add(ConfirmationMethodConstants.holderOfKey)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testProcessConditionsWithNullConditionsContent() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = null

		aval.processConditions = true
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.CONDITIONS
	}

	void testNonProcessConditionsWithCondtionsContent() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		aval.processConditions = false
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testNonProcessConditionsWithNullConditionsContent() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = null

		aval.processConditions = false
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testConditionsNotBefore() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.HOUR, 1)
		XMLGregorianCalendar notBefore =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		ass.conditions.notBefore = notBefore	
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.CONDITIONS_NOTBEFORE
	}

	void testConditionsNotOnAfter() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

        def skewed = new GregorianCalendar()
		skewed.add(GregorianCalendar.HOUR, -1)
		XMLGregorianCalendar notOnOrAfter =  DatatypeFactory.newInstance().newXMLGregorianCalendar(skewed);

		ass.conditions.notOnOrAfter = notOnOrAfter	
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.CONDITIONS_NOTONAFTER
	}

	void testConditionsOneTimeUse() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		def oneTimeUse = new OneTimeUse()
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(oneTimeUse)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
		assertTrue av.oneTimeUse
	}

	void testConditionsProxyRestriction() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		def proxyRestriction = new ProxyRestriction()
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(proxyRestriction)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
		assertTrue av.proxyRestriction
	}

	void testConditionsNoValidAudience() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		def audienceRestriction = new AudienceRestriction()
		audienceRestriction.audiences.add('intient:test:firewalls')
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(audienceRestriction)
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.CONDITIONS_AUDIENCE_RESTRICTION
	}

	void testConditionsValidAudience() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		def audienceRestriction = new AudienceRestriction()
		audienceRestriction.audiences.add('intient:test:firewalls')
		audienceRestriction.audiences.add('intient:test:vpn')
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(audienceRestriction)
		
		rec.audienceMemberships.add('intient:test:firewalls')
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testConditionsValidAudienceValidConjunction() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		def audienceRestriction = new AudienceRestriction()
		audienceRestriction.audiences.add('intient:test:firewalls')
		
		def audienceRestriction2 = new AudienceRestriction()
		audienceRestriction2.audiences.add('intient:test:vpn')
		
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(audienceRestriction)
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(audienceRestriction2)
		
		rec.audienceMemberships.add('intient:test:firewalls')
		rec.audienceMemberships.add('intient:test:vpn')
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.VALID
	}

	void testConditionsValidAudienceInvalidConjunction() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		def audienceRestriction = new AudienceRestriction()
		audienceRestriction.audiences.add('intient:test:firewalls')
		
		def audienceRestriction2 = new AudienceRestriction()
		audienceRestriction2.audiences.add('intient:test:vpn')
		
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(audienceRestriction)
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(audienceRestriction2)
		
		rec.audienceMemberships.add('intient:test:firewalls')
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INVALID
		assertTrue av.cause == AssertionValidity.Cause.CONDITIONS_AUDIENCE_RESTRICTION
	}

	void testConditionsValidAudienceExtendedConditions() {
		def (aval, ass, rec) = basicWithCache()

		ass.version = VersionConstants.saml20
		ass.id = id
		ass.issueInstant = calendar
		ass.issuer = new NameIDType()
		ass.conditions = new Conditions()

		def condition = [:] as ConditionAbstractType
		
		ass.conditions.conditionsAndOneTimeUsesAndAudienceRestrictions.add(condition)
		
		rec.audienceMemberships.add('intient:test:firewalls')
		
		def av = aval.validate(ass, rec)
		assertTrue av.status == AssertionValidity.Status.INDETERMINATE
		assertTrue av.cause == AssertionValidity.Cause.CONDITIONS_EXTENDED
	}
	
	// helpers
	void setUp() {
		calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar())
		id = "_123"
		id2 = "_098"
	}
	
	def basic = {
		def av = new AssertionValidatorImpl()
		def ass = new Assertion()
		def rec = new AssertionRecipient()

		return [av, ass, rec]
	}

	def basicWithCache = {
		def aval = new AssertionValidatorImpl()
		def ass = new Assertion()
		def rec = new AssertionRecipient()

		def registerIdentifier = { String genID ->  }
		def containsIdentifier = { if(it.equals(this.id2)) return true; else return false}
		def identCache = [registerIdentifier: registerIdentifier, containsIdentifier : containsIdentifier] as IdentifierCacheImpl
		aval.identifierCache = identCache

		aval.allowedTimeSkew = 120000

		rec.validConfirmationMethods.add(ConfirmationMethodConstants.bearer)

		return [aval, ass, rec]
	}

}