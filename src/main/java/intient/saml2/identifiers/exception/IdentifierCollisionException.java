package intient.saml2.identifiers.exception;

/**
 * Thrown to indicate that there has been a collision between identifiers.
 * 
 * @author Bradley Beddoes
 */
public class IdentifierCollisionException extends Exception {
	private static final long serialVersionUID = 2741095609753726458L;

	/**
	 * Thrown when there has been a collision between identifiers.
	 * 
	 * @param message
	 *            Human readable message indicating why this exception was thrown
	 * @param cause
	 *            Any exception which caused this exception to be thrown, may be null
	 */
	public IdentifierCollisionException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * Thrown when there has been a collision between identifiers.
	 * 
	 * @param message
	 *            Human readable message indicating why this exception was thrown
	 */
	public IdentifierCollisionException(String message) {
		super(message);
	}
}
