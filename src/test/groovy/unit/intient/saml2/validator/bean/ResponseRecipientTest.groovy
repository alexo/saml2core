package intient.saml2.validator.bean;

import groovy.util.GroovyTestCase;

public class ResponseRecipientTest extends GroovyTestCase  {

	void testLocalDestinations() {
		def rr = new RequestRecipient()
		assertTrue rr.localDestinations.size() == 0

		rr.localDestinations.add("http://service.example.com")
		assertTrue rr.localDestinations.size() == 1
		assertTrue rr.localDestinations.contains("http://service.example.com")
	}

}