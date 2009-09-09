package intient.saml2.validator;

import intient.saml2.validator.bean.RequestRecipient;
import intient.saml2.validator.bean.RequestValidity;

import org.oasis.saml2.protocol.RequestAbstractType;

public interface RequestValidator {

	public RequestValidity validate(RequestAbstractType request, RequestRecipient recipient);
	
}
