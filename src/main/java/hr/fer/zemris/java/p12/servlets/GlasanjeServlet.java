package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Web servlet that loads poll informations from {@link DAO}
 * and sends list of {@link PollOptions} objects further to the '/WEB-INF/pages/glasanjeIndex.jsp'
 * for displaying the voting options.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id;
		try {
			id = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException | NullPointerException ex) {
			return;
		}
		
		List<PollOptions> pollOptions = DAOProvider.getDao().getPollOptions(id);
		Collections.sort(pollOptions);
		req.setAttribute("pollOptions", pollOptions);

		Poll currentPoll = getCurrentPoll(DAOProvider.getDao().getPolls(), id);
		if (currentPoll == null)
			return;

		req.setAttribute("poll", currentPoll);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
	/**
	 * Method returns poll that user requested.
	 * 
	 * @param pollsList list of all available polls
	 * @param id        poll ID
	 * @return          current poll or <code>null</code> if given <code>id</code> is invalid
	 */
	private Poll getCurrentPoll(List<Poll> pollsList, long id) {
		for (Poll p : pollsList) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

}
