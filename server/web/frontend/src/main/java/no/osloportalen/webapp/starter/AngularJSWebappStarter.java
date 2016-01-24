package no.osloportalen.webapp.starter;

import static spark.Spark.port;

import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import no.osloportalen.webapp.config.DefaultWebConfig;
import spark.servlet.SparkApplication;

public class AngularJSWebappStarter implements SparkApplication {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( AngularJSWebappStarter.class );
		// ImmutarePollService service = ctx.getBean( ImmutarePollService.class
		// );
		// List<ScoreCard> scoreCards = service.calculateInteresetResponse();
		port(getHerokuAssignedPort());
		new DefaultWebConfig();
		ctx.registerShutdownHook();
		ctx.close();

	}
	@Override
	public void init() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( AngularJSWebappStarter.class );
		// ImmutarePollService service = ctx.getBean( ImmutarePollService.class
		// );
		// List<ScoreCard> scoreCards = service.calculateInteresetResponse();
		port(getHerokuAssignedPort());
		WebAppContext webContext = new WebAppContext();
		String[] virtualHosts = new String[1];
		virtualHosts[0] = "local.osloportalen.no";
		webContext.setVirtualHosts( virtualHosts );

		new DefaultWebConfig();
		ctx.registerShutdownHook();
		ctx.close();

	}
	
	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if ( processBuilder.environment().get( "PORT" ) != null ) {
			return Integer.parseInt( processBuilder.environment().get( "PORT" ) );
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on
						// localhost)
	}

}
