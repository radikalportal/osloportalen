package no.osloportalen.web.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import no.osloportalen.storage.BasicStorageFactory;
import no.osloportalen.storage.couchdb.repository.NewsContentRepository;
import no.osloportalen.storage.model.NewsContent;
import no.osloportalen.web.models.NewsContentTest;

@Path("/news-feeder")
@Produces(MediaType.APPLICATION_JSON)
public class NewsFeederResource {
	private final String template;
	private final String defaultName;
	private final AtomicLong counter;

	public NewsFeederResource(String template, String defaultName) {
		this.template = template;
		this.defaultName = defaultName;
		this.counter = new AtomicLong();
	}

	@GET
	@Timed
	public NewsContentTest sayHello(@QueryParam("name") Optional<String> name) {
		final String value = String.format( template, name.or( defaultName ) );
		NewsContentTest contentTest = new NewsContentTest();
		contentTest.setName( value );
		return contentTest;
	}

	@Path("/all")
	@GET
	@Timed
	public List<NewsContent> getAll() {

		NewsContentRepository repo = BasicStorageFactory.getNewsContentRepository();
		return repo.getAll();
	}

}
