package hr.fer.zemris.java.p12.model;

/**
 * Class represents poll that is characterized by ID, title, message.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public class Poll {
	
	/**
	 * Poll's ID.
	 */
	private long id;
	/**
	 * Poll's title.
	 */
	private String title;
	/**
	 * Poll's message.
	 */
	private String message;

	/**
	 * Constructor that creates new {@link Poll} object.
	 * 
	 * @param id      {@link #id}
	 * @param title   {@link #title}
	 * @param message {@link #message}
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Default constructor that creates new {@link Poll} object. <br>
	 * All values are set to the default values.
	 */
	public Poll() {
	}

	/**
	 * Method returns poll's ID.
	 * 
	 * @return poll's ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Method sets poll's ID.
	 * 
	 * @param id new poll's ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Method returns poll's title.
	 * 
	 * @return poll's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method sets poll's title.
	 * 
	 * @param title new poll's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method returns poll's message.
	 * 
	 * @return poll's message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Method sets poll's message.
	 * 
	 * @param message new poll's message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
