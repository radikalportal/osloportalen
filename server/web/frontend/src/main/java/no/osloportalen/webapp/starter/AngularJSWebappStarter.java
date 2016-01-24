package no.osloportalen.webapp.starter;

import static spark.Spark.port;

import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import no.osloportalen.webapp.config.DefaultWebConfig;
import spark.servlet.SparkApplication;

public class AngularJSWebappStarter implements SparkApplication {
	private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger( AngularJSWebappStarter.class.getName() );
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( AngularJSWebappStarter.class );
		// ImmutarePollService service = ctx.getBean( ImmutarePollService.class
		// );
		// List<ScoreCard> scoreCards = service.calculateInteresetResponse();
		configureServer();
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
		configureServer();
		WebAppContext webContext = new WebAppContext();
		String[] virtualHosts = new String[1];
		virtualHosts[0] = "local.osloportalen.no";
		webContext.setVirtualHosts( virtualHosts );

		new DefaultWebConfig();
		ctx.registerShutdownHook();
		ctx.close();

	}
	
	private static void configureServer() {
		String httpPort = System.getenv( "PORT" );
		logger.debug("Heroku wants our app to listen on port:" + httpPort);
		port(Integer.parseInt( httpPort ));
	}

}
