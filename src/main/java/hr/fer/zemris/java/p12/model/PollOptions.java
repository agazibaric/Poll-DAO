package hr.fer.zemris.java.p12.model;

import java.util.Comparator;

/**
 * PollOptions contains informations about one option in poll specified by pollID.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public class PollOptions implements Comparable<PollOptions> {
	
	/**
	 * ID of the poll option.
	 */
	private long id;
	/**
	 * Poll option's title.
	 */
	private String optionTitle;
	/**
	 * Poll option's representative link.
	 */
	private String optionLink;
	/**
	 * ID of the poll to whom this option belongs.
	 */
	private long pollID;
	/**
	 * Number of votes for this poll option.
	 */
	private long votesCount;
	/**
	 * Compares poll options by number of votes.
	 */
	public static final Comparator<PollOptions> BY_VOTES_COUNT = 
			(a, b) -> Long.compare(b.getVotesCount(), a.getVotesCount());

	/**
	 * Constructor that creates new {@link PollOptions} object.
	 * 
	 * @param id          {@link #id}
	 * @param optionTitle {@link #optionTitle}
	 * @param optionLink  {@link #optionLink}
	 * @param pollID      {@link #pollID}
	 * @param votesCount  {@link #votesCount}
	 */
	public PollOptions(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}
	
	/**
	 * Default constructor that creates new {@link PollOptions} object. <br>
	 * All values are set to the default values.
	 */
	public PollOptions() {
	}

	/**
	 * Method returns poll option's id.
	 * 
	 * @return poll option's id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Method sets poll option's id.
	 * 
	 * @param id new poll options id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Method returns poll option's title.
	 * 
	 * @return poll option's title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Method sets poll option's title.
	 * 
	 * @param optionTitle new poll option's title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Method returns poll option's link.
	 * 
	 * @return poll option's link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Method sets poll option's link.
	 * 
	 * @param optionLink new poll option's link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Method returns ID of the poll to which this option belongs.
	 * 
	 * @return ID of the poll to which this option belongs
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Method sets ID of the poll to which this option belongs.
	 * 
	 * @param pollID new ID of the poll to which this option belongs
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Method returns number of votes for this poll option.
	 * 
	 * @return number of votes for this poll option
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Method sets number of votes for this poll option.
	 * 
	 * @param votesCount new number of votes for this poll option
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	@Override
	public int compareTo(PollOptions o) {
		return Long.compare(this.id, o.id);
	}

}
