package intient.saml2.identifiers.exception;

/**
 * Thrown to indicate that there has been a collision between identifiers when generating a new id.
 * 
 * @author Bradley Beddoes
 */
public class IdentifierGeneratorException extends RuntimeException {
	private static final long serialVersionUID = 9221602804735591409L;

	/**
	 * Thrown when there has been a collision between identifiers when generating a new id.
	 * 
	 * @param message
	 *            Human readable message indicating why this exception was thrown
	 * @param cause
	 *            Any exception which caused this exception to be thrown, may be null
	 */
	public IdentifierGeneratorException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * Thrown when there has been a collision between identifiers when generating a new id.
	 * 
	 * @param message
	 *            Human readable message indicating why this exception was thrown
	 */
	public IdentifierGeneratorException(String message) {
		super(message);
	}
}
