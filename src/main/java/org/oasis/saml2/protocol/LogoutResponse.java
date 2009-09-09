package org.oasis.saml2.protocol;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.oasis.saml2.protocol.StatusResponseType;

/**
 * Ensures creation of a LogoutRequest is simplified for library programmers, abstracts away JAXBElement requirements.
 * 
 * @author Bradley Beddoes
 */
public class LogoutResponse extends JAXBElement<StatusResponseType> {
	private static final long serialVersionUID = 7193969025966813274L;

	public LogoutResponse(StatusResponseType value) {
		super(new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutResponse"), StatusResponseType.class, value);
	}
}
