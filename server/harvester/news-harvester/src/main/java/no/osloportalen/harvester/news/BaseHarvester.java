package no.osloportalen.harvester.news;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import no.osloportalen.harvester.parser.Parser;
import no.osloportalen.harvester.parser.RadikalPortalParser;

public abstract class BaseHarvester extends WebCrawler {
	protected enum WHICH_PARSER {
		radikalportal, dagbladet, aftenposten
	};

	protected String CURRENT_PARSER = new String();

	protected void parseHTMLContent(Page page) {

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Parser parser = null;
			if ( CURRENT_PARSER.equals(WHICH_PARSER.radikalportal.name())) {
				parser = new RadikalPortalParser();
			}

			String parsedContent = parser.doParse(html);
		}
	}

	protected void decideWhichParser(String href) {
		if (href.contains("radikalportal.no")) {
			CURRENT_PARSER = WHICH_PARSER.radikalportal.name();
		}
	}
}
