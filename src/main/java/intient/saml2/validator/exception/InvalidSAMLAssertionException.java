package intient.saml2.validator.exception;

/**
 * Thrown when an invalid SAML assertion has been presented.
 * 
 * @author Bradley Beddoes
 */
public class InvalidSAMLAssertionException extends Exception
{
	/**
	 * Thrown when an invalid SAML assertion has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 * @param cause Any exception which caused this exception to be thrown, may be null
	 */
	public InvalidSAMLAssertionException(String message, Exception cause)
	{
		super(message, cause);
	}
	
	/**
	 * Thrown when an invalid SAML assertion has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 */
	public InvalidSAMLAssertionException(String message)
	{
		super(message);
	}
}
