package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Web servlet that loads all available polls
 * and sends them in list to the "/WEB-INF/pages/showPolls.jsp" for displaying.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/servleti/index.html")
public class PollServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/showPolls.jsp").forward(req, resp);
		
	}

}
