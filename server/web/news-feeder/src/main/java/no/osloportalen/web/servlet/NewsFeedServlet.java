package no.osloportalen.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/base" })
public class NewsFeedServlet extends HttpServlet {

	private static final long serialVersionUID = 6408424569798224350L;

	public NewsFeedServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Answering a GET request...");
	}

}
