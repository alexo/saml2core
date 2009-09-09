package intient.saml2.validator.exception;

/** 
 * Thrown when an invalid SAML response has been presented.
 * @author Bradley Beddoes
 */
public class InvalidSAMLResponseException extends Exception
{

	/**
	 * Thrown when an invalid SAML response has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 * @param cause Any exception which caused this exception to be thrown, may be null
	 */
	public InvalidSAMLResponseException(String message, Exception cause)
	{
		super(message, cause);
	}
	
	/**
	 * Thrown when an invalid SAML response has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 */
	public InvalidSAMLResponseException(String message)
	{
		super(message);
	}
}
