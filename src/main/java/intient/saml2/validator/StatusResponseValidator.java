package intient.saml2.validator;

import intient.saml2.validator.bean.ResponseRecipient;
import intient.saml2.validator.bean.ResponseValidity;
import intient.saml2.validator.exception.InvalidSAMLResponseException;

import org.oasis.saml2.protocol.StatusResponseType;

/**
 * Validates Response element to SAML 2 requirements.
 * 
 * @author Bradley Beddoes
 */
public interface StatusResponseValidator {

	public ResponseValidity validate(StatusResponseType response, ResponseRecipient recipient);
}
