package no.osloportalen.storage;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import no.osloportalen.storage.couchdb.repository.NewsContentRepository;
import no.osloportalen.storage.model.NewsContent;

public class BasicStorageFactory {
	private static CouchDbConnector DB_CONNECTOR = null;
	private static NewsContentRepository NEWS_CONTENT_REPO = null;

	public static void main(String[] args) {
		BasicStorageFactory.initializeConnection();
		NewsContentRepository repo = BasicStorageFactory.getNewsContentRepository();
		List<NewsContent> contents = repo.findByUrl( "http://radikalportal.no/2015/12/22/rapport-fraa-en-krigssone/");
		System.out.println( "Contents found: " + contents.size() );
	} 
	
	private static void initializeConnection() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext( "application-context.xml" );
		if ( applicationContext != null ) {
			DB_CONNECTOR = (CouchDbConnector) applicationContext.getBean( "osloPortalenDatabase" );
		}
	}

	public static final NewsContentRepository getNewsContentRepository() {
		if ( null == DB_CONNECTOR ) {
			initializeConnection();
		}

		if ( NEWS_CONTENT_REPO == null ) {
			NEWS_CONTENT_REPO = new NewsContentRepository( NewsContent.class, DB_CONNECTOR );
		}

		return NEWS_CONTENT_REPO;
	}

}
