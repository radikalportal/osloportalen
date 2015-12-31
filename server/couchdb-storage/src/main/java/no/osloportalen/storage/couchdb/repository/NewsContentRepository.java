package no.osloportalen.storage.couchdb.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

import no.osloportalen.storage.model.NewsContent;

public class NewsContentRepository extends CouchDbRepositorySupport<NewsContent> {

	public NewsContentRepository(Class<NewsContent> newsContent, CouchDbConnector db) {
		super( newsContent, db );
	}

	public List<NewsContent> findByUrl(String url) {
		return queryView("url", url);
	}
	
}
