package intient.saml2.validator.bean

import groovy.util.GroovyTestCase

import intient.saml2.constants.*

public class AssertionRecipientTest extends GroovyTestCase {

	void testRecipientURI() {
		def recipient = new AssertionRecipient()
		assertNull recipient.getRecipientURI()

		def uri = "intient:test:uri"
		recipient.setRecipientURI(uri)
		assertTrue recipient.getRecipientURI().equals(uri)
	}

	void testTrustedAddresses() {
		def recipient = new AssertionRecipient()
		assertNotNull recipient.getTrustedAddresses()

		def trustedAddresses = ['127.0.0.1', 'FEDC:BA98:7654:3210:FEDC:BA98:7654:3210']
        recipient.setTrustedAddresses(trustedAddresses)

        assertEquals recipient.getTrustedAddresses(), trustedAddresses
        assertTrue recipient.getTrustedAddresses().contains('127.0.0.1')
        assertTrue recipient.getTrustedAddresses().contains('FEDC:BA98:7654:3210:FEDC:BA98:7654:3210')
	}

	void testAudienceMembership() {
		def recipient = new AssertionRecipient()
		assertNotNull recipient.audienceMemberships

		def audienceMemberships = ['intient:groups:dmz:firewalls', 'intient:groups:dmz:vpn']
        recipient.audienceMemberships = audienceMemberships

        assertEquals recipient.audienceMemberships, audienceMemberships
        assertTrue recipient.audienceMemberships.contains('intient:groups:dmz:firewalls')
        assertTrue recipient.audienceMemberships.contains('intient:groups:dmz:vpn')
	}

	void testValidConfirmationMethods() {
		def recipient = new AssertionRecipient()
		assertNotNull recipient.validConfirmationMethods

		def validConfirmationMethods = [ConfirmationMethodConstants.holderOfKey, ConfirmationMethodConstants.bearer]
        recipient.validConfirmationMethods = validConfirmationMethods

        assertEquals recipient.validConfirmationMethods, validConfirmationMethods
        assertTrue recipient.validConfirmationMethods.contains(ConfirmationMethodConstants.holderOfKey)
        assertTrue recipient.validConfirmationMethods.contains(ConfirmationMethodConstants.bearer)
	}
}