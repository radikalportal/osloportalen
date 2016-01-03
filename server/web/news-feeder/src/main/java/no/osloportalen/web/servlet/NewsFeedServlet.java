package no.osloportalen.web.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lightcouch.NoDocumentException;

import com.google.gson.Gson;

import no.osloportalen.storage.BasicStorageFactory;
import no.osloportalen.storage.couchdb.repository.NewsContentRepository;
import no.osloportalen.storage.model.NewsContent;

public class NewsFeedServlet extends HttpServlet {

	private static final long serialVersionUID = 6408424569798224350L;
	private static final Logger logger = LogManager.getLogger( NewsFeedServlet.class.getName() );

	public NewsFeedServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Answering a GET request...");
		StringWriter writer = new StringWriter();
		response.setContentType("application/JSON");
//		writer.append("Hello world!");

		NewsContentRepository newsRepo = BasicStorageFactory.getNewsContentRepository();
		List<NewsContent> contentList = new ArrayList<NewsContent>();
		try {
			contentList = newsRepo.getAll();
			logger.debug("We found " + contentList.size() + " news articles");
		} catch (NoDocumentException nde) {
			System.out.println("Aww snap! No such document existed.. what the begeeeezes: " + nde.toString());
			return;
		}
		
		Gson gson = new Gson();
		writer.write(gson.toJson(contentList));
		response.getWriter().write(writer.toString());
	}
}
