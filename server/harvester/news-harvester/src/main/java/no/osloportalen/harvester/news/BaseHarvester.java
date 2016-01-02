package no.osloportalen.harvester.news;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import no.osloportalen.harvester.parser.Parser;
import no.osloportalen.harvester.parser.DefaultWebParser;
import no.osloportalen.storage.BasicStorageFactory;
import no.osloportalen.storage.couchdb.repository.NewsContentRepository;
import no.osloportalen.storage.model.NewsContent;

public abstract class BaseHarvester extends WebCrawler {
	private String parsedContent = new String();
	private Page page;

	protected enum WHICH_PARSER {
		radikalportal, dagbladet, aftenposten
	};

	protected String CURRENT_PARSER = new String();

	protected void parseHTMLContent(Page page) {

		if ( page.getParseData() instanceof HtmlParseData ) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			this.page = page;
//			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Parser parser = null;

			// if ( CURRENT_PARSER.equals( WHICH_PARSER.radikalportal.name() ) )
			// {
			parser = new DefaultWebParser();
			// }

			parsedContent = parser.doParse( html );
		}
	}

	protected void persistContent() {

		if ( !isContentRelevant() ) {
			return;
		}

		NewsContent newsContent = NewsContent.convertFromPage( this.page );
		newsContent.setContent( parsedContent );
		if ( isPageAlreadySeen( page.getWebURL().toString() ) ) {
			return;
		}
		try {
			BasicStorageFactory.getNewsContentRepository().add( newsContent );
		} catch (Exception e) {
			System.out.println( "Fuck it! " + e.getMessage() );
		}
		// Response couchDBResponse = client.save(newsContent);
		// System.out.println("Content stored with id: " +
		// couchDBResponse.getId());
		// Response response = client.save(htmlParseData);

	}

	private boolean isPageAlreadySeen(String string) {
		NewsContent storedContent = null;
		try {
			storedContent = BasicStorageFactory.getNewsContentRepository().get( string );
		} catch (Exception e) {
			System.out.println( "Fuck it! " + e.getMessage() );
		}

		if ( storedContent != null ) {
			return true;
		}

		return false;
	}

	protected boolean isDocumentAlreadyHarvested(String url) {
		NewsContentRepository repo = BasicStorageFactory.getNewsContentRepository();
		List<NewsContent> contents = repo.findByUrl( url );

		if ( contents != null && contents.size() > 0 ) {
			return true;
		}

		return false;
	}

	private boolean isContentRelevant() {
		// int score = 0;
		if ( "".equals( parsedContent ) ) {
			System.out.println( "No content to persist" );
			return false;
		}

		if ( page == null ) {
			return false;
		}

		return true;
	}

	protected void decideWhichParser(String href) {
		if ( href.contains( "radikalportal.no" ) ) {
			CURRENT_PARSER = WHICH_PARSER.radikalportal.name();
		}
	}
}
