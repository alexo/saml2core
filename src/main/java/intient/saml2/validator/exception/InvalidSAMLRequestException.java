package intient.saml2.validator.exception;

/**
 * Thrown when an invalid SAML request has been presented.
 * 
 * @author Bradley Beddoes
 */
public class InvalidSAMLRequestException extends Exception
{	
	/**
	 * Thrown when an invalid SAML request has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 * @param cause Any exception which caused this exception to be thrown, may be null
	 */
	public InvalidSAMLRequestException(String message, Exception cause)
	{
		super(message, cause);
	}
	
	/**
	 * Thrown when an invalid SAML request has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 */
	public InvalidSAMLRequestException(String message)
	{
		super(message);
	}
}
