package no.osloportalen.harvester.news;

import java.util.Set;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class DefaultHarvester extends BaseHarvester {
	private static Logger logger = LogManager.getLogger(DefaultHarvester.class.getName());
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		decideWhichParser(href);
		boolean shouldVisit = !FILTERS.matcher(href).matches(); //&& !href.startsWith("http://radikalportal.no/tagg") && href.startsWith("http://radikalportal.no/");
		if (shouldVisit) {
			logger.debug("Visiting url: \"" + url + "\"");
		}
//		System.out.println( "Should visit? " + shouldVisit );
		return shouldVisit; 
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		
		System.out.println("=================== NEW SESSION =============================================================");
		System.out.println("URL: " + url);
		
		if (!isDocumentAlreadyHarvested(url)) {
			parseHTMLContent(page);
			persistContent();
		}
		
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			
//			System.out.println("============= TEXT =============================================================");
//			System.out.println(page.getParseData().toString());
//			System.out.println("================================================================================");
			System.out.println("================================================================================");
			System.out.println("Number of outgoing links: " + links.size());
//			for ( WebURL webURL : links ) {
//				System.out.print( "Link: " + webURL.getURL() + " --- " );
//			}
//			System.out.println("================================================================================");
//			System.out.println("============= COUCHDB ==========================================================");
//			System.out.println("Response ID = " + response.getId());
//			System.out.println("Response = " + response.toString());
//			System.out.println("================================================================================");
		}
		System.out.println("=================== SESSION END =============================================================");
	}

}
