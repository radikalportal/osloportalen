package no.osloportalen.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lightcouch.CouchDbClient;

import no.osloportalen.storage.model.NewsContent;
import no.osloportalen.storage.couchdb.CouchDBFactory;

public class NewsFeedServlet extends HttpServlet {

	private static final long serialVersionUID = 6408424569798224350L;

	public NewsFeedServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Answering a GET request...");
		response.getWriter().write("Hello world!");
		
		CouchDbClient client = CouchDBFactory.get();
//		client.find(NewsContent.class, id, params);
		
	}

}
