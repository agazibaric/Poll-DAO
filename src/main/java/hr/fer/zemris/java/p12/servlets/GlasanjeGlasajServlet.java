package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;


/**
 * Web servlet that is called when voting is performed.
 * It gets bend ID for which it's voted from context parameters under the name 'optionID'.
 * Then it adds one vote to the band which has that ID and updates voting results.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long voteID;
		try {
			voteID = Long.parseLong(req.getParameter("optionID"));
		} catch (NullPointerException | NumberFormatException ex) {
			return;
		}
		DAOProvider.getDao().addPollOptionVote(voteID);
		req.setAttribute("pollID", req.getAttribute("pollID"));
		req.getRequestDispatcher("glasanje-rezultati").forward(req, resp);
		
	}
	
}
