package no.osloportalen.web;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import no.osloportalen.web.configuration.NewsFeederConfiguration;
import no.osloportalen.web.healthchecks.NewsFeederHealthCheck;
import no.osloportalen.web.resources.NewsFeederResource;

public class NewsFeederApplication extends Application<NewsFeederConfiguration> {

	public static void main(String[] args) throws Exception {
		new NewsFeederApplication().run( args );
	}

	@Override
	public void run(NewsFeederConfiguration configuration, Environment environment) throws Exception {
		final NewsFeederResource resource = new NewsFeederResource( configuration.getTemplate(), configuration.getDefaultName() );
		environment.jersey().register( resource );

		final NewsFeederHealthCheck healthCheck = new NewsFeederHealthCheck( configuration.getTemplate() );
		environment.healthChecks().register( "template", healthCheck );
		environment.jersey().register( resource );

	}

	@Override
	public void initialize(Bootstrap<NewsFeederConfiguration> bootstrap) {

	}

}
