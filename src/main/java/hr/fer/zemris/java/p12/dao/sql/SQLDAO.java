package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection(); 
		try (
			PreparedStatement pst = con.prepareStatement("SELECT id, title, message FROM Polls ORDER BY id");
			ResultSet rs = pst.executeQuery()) {

			while (rs != null && rs.next()) {
				Poll poll = new Poll();
				poll.setId(rs.getLong(1));
				poll.setTitle(rs.getString(2));
				poll.setMessage(rs.getString(3));
				polls.add(poll);
			}
		} catch (SQLException e) {
			throw new DAOException("Error occurred during selecting polls.", e);
		}
		return polls;
	}
	
	@Override
	public void addPollOptionVote(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection(); 
		try (
			PreparedStatement pst = con.prepareStatement(
					"UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id = ?")) {
			
			pst.setLong(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error occurred during selecting polls.", e);
		}
	}
	
	@Override
	public Poll getPoll(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection(); 
		try (
			PreparedStatement pst = con.prepareStatement(
					"SELECT id, title, message FROM Polls WHERE id = ?")) {
			
			
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs != null && rs.next()) {
				Poll poll = new Poll();
				poll.setId(rs.getLong(1));
				poll.setTitle(rs.getString(2));
				poll.setMessage(rs.getString(3));
				return poll;
			}
			rs.close();
		} catch (SQLException e) {
			throw new DAOException("Error occurred during selecting poll.", e);
		}
		return null;
	}

	@Override
	public List<PollOptions> getPollOptions(long pollID) throws DAOException {
		List<PollOptions> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		try (
			PreparedStatement pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, pollID, votesCount " +
					"FROM PollOptions WHERE pollID = ? " +
					"ORDER BY id")) {
			
			pst.setLong(1, pollID);
			ResultSet rs = pst.executeQuery();
			
			while (rs != null && rs.next()) {
				PollOptions pollOption = new PollOptions();
				pollOption.setId(rs.getLong(1));
				pollOption.setOptionTitle(rs.getString(2));
				pollOption.setOptionLink(rs.getString(3));
				pollOption.setPollID(rs.getLong(4));
				pollOption.setVotesCount(rs.getLong(5));
				pollOptions.add(pollOption);
			}
			rs.close();
		} catch (SQLException e) {
			throw new DAOException("Error occurred during selecting poll options.", e);
		}
		return pollOptions;
	}

	@Override
	public void createPolls(DataSource dataSource) throws DAOException {
		try (
			Connection con = dataSource.getConnection(); 
			PreparedStatement pst = con.prepareStatement(
					    	"CREATE TABLE Polls " + 
					    	"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + 
					    	"title VARCHAR(150) NOT NULL, " + 
							"message CLOB(2048) NOT NULL)")) {
			
			
			DatabaseMetaData dbm = con.getMetaData();
			
			// Check if "POLLS" table exists
			ResultSet rs = dbm.getTables(null, "IVICA", "POLLS", null);
			if (!rs.next()) {
				// Table does not exist, create it:
				pst.executeUpdate();
			}
			// else do nothing
			
			rs.close();
		} catch (SQLException e) {
			throw new DAOException("Error occurred during creating polls.", e);
		}
	}
	
	@Override
	public void createPollOptions(DataSource dataSource) throws DAOException {
		try (
			Connection con = dataSource.getConnection(); 
			PreparedStatement pst = con.prepareStatement(
						    "CREATE TABLE PollOptions " + 
						    "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + 
						    "optionTitle VARCHAR(100) NOT NULL, " + 
						    "optionLink VARCHAR(150) NOT NULL, " + 
						    "pollID BIGINT, " + 
						    "votesCount BIGINT, " + 
						    "FOREIGN KEY (pollID) REFERENCES Polls(id))")) {

			DatabaseMetaData dbm = con.getMetaData();
			// Check if "POLLOPTIONS" table exists
			ResultSet rs = dbm.getTables(null, "IVICA", "POLLOPTIONS", null);
			if (!rs.next()) {
				// Table does not exist, create it:
				pst.executeUpdate();
			}
			// else do nothing
			
			rs.close();
		} catch (SQLException e) {
			throw new DAOException("Error occurred during creating poll options.", e);
		}
	}

	@Override
	public boolean isPollEmpty(DataSource dataSource) throws DAOException {
		try (
			Connection con = dataSource.getConnection(); 
			PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM Polls")) {

			ResultSet rs = pst.executeQuery();
			rs.next();
			return rs.getLong(1) == 0L;
		} catch (SQLException e) {
			throw new DAOException("Error occurred during select on polls.", e);
		}
	}

	@Override
	public void fillPoll(ServletContextEvent sce, DataSource dataSource, String pollFilePath) throws DAOException {
		Path pollDefPath = Paths.get(sce.getServletContext().getRealPath(pollFilePath));
		try (
			Connection con = dataSource.getConnection();
			PreparedStatement pst = con.prepareStatement(
						"INSERT INTO Polls (title, message) " + 
						"VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
			
			List<String> polls = Files.readAllLines(pollDefPath);
			for (String poll : polls) {
				String[] pollParts = poll.split("\t");
				pst.setString(1, pollParts[0]); // Title
				pst.setString(2, pollParts[1]); // Message
				
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
				rs.next();
				Long pollID = rs.getLong(1);
				
				Path pollOptionsPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/" + pollParts[2]));
				fillPollOptions(pollID, con, pollOptionsPath);
				rs.close();
				pst.clearParameters();
			}
			
		} catch (SQLException ex) {
			throw new DAOException("Error occurred during insert values into poll.", ex);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method fills poll options table with values.
	 * 
	 * @param pollID          poll's ID
	 * @param con             Connection object
	 * @param pollOptionsPath path of the file that contains poll options definition
	 */
	private void fillPollOptions(Long pollID, Connection con, Path pollOptionsPath) {	
		try (
			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO PollOptions " + 
					"(optionTitle, optionLink, pollID, votesCount) " +
					"VALUES (?, ?, ?, ?)")) {
			
			List<String> bands = Files.readAllLines(pollOptionsPath);
			for (String band : bands) {
				String[] bandParts = band.split("\t");
				pst.setString(1, bandParts[0]); // Title
				pst.setString(2, bandParts[1]); // Link
				pst.setLong(3, pollID); // Poll ID
				pst.setLong(4, 0); // Number of votes
				
				pst.executeUpdate();
				pst.clearParameters();
			}
		} catch (SQLException | IOException e) {
			throw new DAOException("Error occurred during insert values into poll options.", e);
		}
	}

}