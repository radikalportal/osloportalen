package no.osloportalen.web.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lightcouch.CouchDbClient;
import org.lightcouch.NoDocumentException;

import no.osloportalen.storage.couchdb.CouchDBFactory;
import no.osloportalen.storage.model.NewsContent;

public class NewsFeedServlet extends HttpServlet {

	private static final long serialVersionUID = 6408424569798224350L;

	public NewsFeedServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Answering a GET request...");
		StringWriter writer = new StringWriter();
		writer.append("Hello world!");

		CouchDbClient client = CouchDBFactory.get();
		List<NewsContent> contentList = null;
		System.out.println(client.getDBUri().toString());
		// ... and this is why we have services..
		try {
			contentList = client.view("_all_docs").query(NewsContent.class);
		} catch (NoDocumentException nde) {
			System.out.println("Aww snap! No such document existed.. what the begeeeezes: " + nde.toString());
			return;
		}
		
		System.out.println("COntentlist = " + contentList.size());
		for (NewsContent content : contentList) {
//			System.out.println(content.getUrl());
			writer.write(content.getUrl() + " " + content.getContent());
		}
		// client.find(NewsContent.class, id, params);
		response.getWriter().write(writer.toString());
	}

}
