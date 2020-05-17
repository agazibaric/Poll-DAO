package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * Web servlet that loads voting results from {@link DAO} and 
 * sends them further to the '/WEB-INF/pages/glasanjeRez.jsp' for displaying the results.
 * It also sends to the above mentioned .jsp list of winners.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException | NullPointerException ex) {
			return;
		}
		
		List<PollOptions> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		pollOptions.sort(PollOptions.BY_VOTES_COUNT);
		List<PollOptions> winners = getWinners(pollOptions);
		
		req.setAttribute("pollOptions", pollOptions);
		req.setAttribute("winners", winners);
		req.setAttribute("pollID", pollID);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}
	
	/**
	 * Method returns list of winners of the poll whose options is given in {@code list}.
	 * 
	 * @param list list of poll options
	 * @return     list of winners of the poll
	 */
	private List<PollOptions> getWinners(List<PollOptions> list) {
		long max = 0;
		for (PollOptions option : list) {
			if (option.getVotesCount() > max) {
				max = option.getVotesCount();
			}
		}
		final long finalMax = max;
		return list.stream()
				.filter(b -> b.getVotesCount() == finalMax)
				.collect(Collectors.toList());
	}

}
