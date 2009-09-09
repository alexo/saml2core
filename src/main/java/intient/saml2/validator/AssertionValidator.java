package intient.saml2.validator;

import intient.saml2.validator.bean.AssertionRecipient;
import intient.saml2.validator.bean.AssertionValidity;
import intient.saml2.validator.exception.InvalidSAMLAssertionException;

import org.oasis.saml2.assertion.Assertion;

/**
 * Validates an Assertion element to SAML 2.0 requirements.
 * 
 * @author Bradley Beddoes
 */
public interface AssertionValidator {
	/**
	 * Validate a SAML 2 Assertion against the specification and details supplied by the assertion recipient. Does not
	 * perform any cryptography validation.
	 * 
	 * @param assertion
	 *            An Assertion to validate
	 * @param recipient
	 *            Details provided by the assertion recipient required to validate the assertion.
	 * @return Returns an AssertionValidity object with as much populated detail about the assertion as possible. When
	 *         the assertion is found to be invalid further processing should not occur and the caller should not expect
	 *         that the entire assertion has been processed. If assertion state is found to be indeterminate then
	 *         processing can continue if the implementation wishes. Unless the status of the result is valid the
	 *         assertion should not be trusted. For error states callers should interrogate AssertionValidity.cause for a more
	 *         fine grained reason for the fault.
	 * @throws InvalidSAMLAssertionException
	 *             If there is an error validating the assertion.
	 */
	public AssertionValidity validate(Assertion assertion, AssertionRecipient recipient)
			throws InvalidSAMLAssertionException;
}
