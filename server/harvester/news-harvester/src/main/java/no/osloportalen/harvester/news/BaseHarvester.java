package no.osloportalen.harvester.news;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import no.osloportalen.harvester.parser.Parser;
import no.osloportalen.harvester.parser.RadikalPortalParser;
import no.osloportalen.storage.couchdb.CouchDBFactory;
import no.osloportalen.storage.model.NewsContent;

public abstract class BaseHarvester extends WebCrawler {
	private String parsedContent = new String();
	private Page page;
	protected enum WHICH_PARSER {
		radikalportal, dagbladet, aftenposten
	};

	protected String CURRENT_PARSER = new String();

	protected void parseHTMLContent(Page page) {

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			this.page = page;
//			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Parser parser = null;
			
			if ( CURRENT_PARSER.equals(WHICH_PARSER.radikalportal.name())) {
				parser = new RadikalPortalParser();
			}

			parsedContent = parser.doParse(html);
		}
	}

	protected void persistContent() {
		if ("".equals(parsedContent)) {
			System.out.println("No content to persist");
			return;
		}
		
		if (page == null) {
			return;
		}
		
		CouchDBFactory storageFactory = new CouchDBFactory();
		CouchDbClient client = storageFactory.get();
		NewsContent newsContent = NewsContent.convertFromPage(this.page);
		newsContent.setContent(parsedContent);
		Response couchDBResponse = client.save(newsContent);
		System.out.println("Content stored with id: " + couchDBResponse.getId());
//		Response response = client.save(htmlParseData);
		
	}
	
	protected void decideWhichParser(String href) {
		if (href.contains("radikalportal.no")) {
			CURRENT_PARSER = WHICH_PARSER.radikalportal.name();
		}
	}
}
