package no.osloportalen.webapp.starter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import no.osloportalen.webapp.config.DefaultWebConfig;
import spark.servlet.SparkApplication;
import static spark.Spark.*;

public class AngularJSWebappStarter implements SparkApplication {
	
	public static void main(String[] args) {
        port(getHerokuAssignedPort());
        get("/hello", (req, res) -> "Hello Heroku World");
    }
	@Override
	public void init() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( AngularJSWebappStarter.class );
		// ImmutarePollService service = ctx.getBean( ImmutarePollService.class
		// );
		// List<ScoreCard> scoreCards = service.calculateInteresetResponse();

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
