package hr.fer.zemris.java.p12.dao;

/**
 * Class represents exception that happened during interacting with {@link DAO} object.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DAOException() {
	}

	/**
	 * Constructor that creates new {@link DAOException} object.
	 * 
	 * @param message            message about invalid situation
	 * @param cause 			 cause of the exception
	 * @param enableSuppression  to enable suppression flag
	 * @param writableStackTrace writable stack trace flag
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor that creates new {@link DAOException} object.
	 * 
	 * @param message message about invalid situation
	 * @param cause   cause of the exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor that creates new {@link DAOException} object.
	 * 
	 * @param message message about invalid situation
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor that creates new {@link DAOException} object.
	 * 
	 * @param cause cause of the exception
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}