package no.osloportalen.harvester.news;

import java.util.Set;
import java.util.regex.Pattern;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import no.osloportalen.storage.couchdb.CouchDBFactory;

public class BasicHarvester extends BaseHarvester {

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		decideWhichParser(href);
		return !FILTERS.matcher(href).matches() && !href.startsWith("http://radikalportal.no/tagg") && href.startsWith("http://radikalportal.no/");
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		
		System.out.println("================================================================================");
		System.out.println("URL: " + url);

		parseHTMLContent(page);
		
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			
			CouchDBFactory storageFactory = new CouchDBFactory();
			CouchDbClient client = storageFactory.get();
//			Response response = client.save(htmlParseData);
			
			

//			System.out.println("============= TEXT =============================================================");
//			System.out.println(text);
//			System.out.println("================================================================================");
			System.out.println("================================================================================");
			System.out.println("Number of outgoing links: " + links.size());
			System.out.println("================================================================================");
//			System.out.println("============= COUCHDB ==========================================================");
//			System.out.println("Response ID = " + response.getId());
//			System.out.println("Response = " + response.toString());
//			System.out.println("================================================================================");
		}
	}
}
