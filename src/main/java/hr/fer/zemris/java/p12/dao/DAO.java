package hr.fer.zemris.java.p12.dao;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Interface to Data Subsystem.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public interface DAO {

	/**
	 * Method returns list of available polls.
	 * 
	 * @return list of polls
	 * @throws DAOException if loading of polls fails
	 */
	List<Poll> getPolls() throws DAOException;
	
	/**
	 * Method returns poll associated to the given {@code id}.
	 * 
	 * @param id            ID of the poll which is returned
	 * @return              poll that is associated to the given ID
	 * @throws DAOException if loading of the poll fails
	 */
	Poll getPoll(long id) throws DAOException;
	
	/**
	 * Method returns poll options for the poll that has given {@code pollID}.
	 * 
	 * @param pollID        ID of the poll whose options are returned
	 * @return				poll options for the poll that has given {@code pollID}
	 * @throws DAOException if loading polls fails
	 */
	List<PollOptions> getPollOptions(long pollID) throws DAOException;
	
	/**
	 * Method adds vote to the poll option specified by given {@code id}.
	 * 
	 * @param id            ID of the poll option
	 * @throws DAOException if updating of the number of votes fails
	 */
	void addPollOptionVote(long id) throws DAOException;
	
	/**
	 * Method creates polls if they are not already created.
	 * 
	 * @param dataSource    DataSource object
	 * @throws DAOException if creating of the polls fails
	 */
	void createPolls(DataSource dataSource) throws DAOException;
	
	/**
	 * Method creates poll options if they are not already created.
	 * 
	 * @param dataSource    DataSource object
	 * @throws DAOException if creating of the poll options fails
	 */
	void createPollOptions(DataSource dataSource) throws DAOException;
	
	/**
	 * Method checks if the polls are empty.
	 * 
	 * @param dataSource    DataSource object
	 * @return              <code>true</code> if polls are empty, otherwise <code>false</code>
	 * @throws DAOException if checking fails
	 */
	boolean isPollEmpty(DataSource dataSource) throws DAOException;
	
	/**
	 * Method fills polls with data.
	 * 
	 * @param sce           ServletContextEvent object
	 * @param dataSource    DataSource object
	 * @param pollFilePath  path of the file that contains polls data
	 * @throws DAOException if fill of the polls fails
	 */
	void fillPoll(ServletContextEvent sce, DataSource dataSource, String pollFilePath) throws DAOException;
	
}