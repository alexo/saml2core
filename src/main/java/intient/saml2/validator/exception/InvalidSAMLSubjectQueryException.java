package intient.saml2.validator.exception;

/**
 * Thrown when an invalid SAML subject query has been presented.
 * 
 * @author Bradley Beddoes
 */
public class InvalidSAMLSubjectQueryException extends Exception
{	
	/**
	 * Thrown when an invalid SAML subject query has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 * @param cause Any exception which caused this exception to be thrown, may be null
	 */
	public InvalidSAMLSubjectQueryException(String message, Exception cause)
	{
		super(message, cause);
	}
	
	/**
	 * Thrown when an invalid SAML subject query has been presented.
	 * 
	 * @param message Human readable message indicating why this exception was thrown
	 */
	public InvalidSAMLSubjectQueryException(String message)
	{
		super(message);
	}
}
