package no.osloportalen.webapp.starter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import no.osloportalen.webapp.config.DefaultWebConfig;
import spark.servlet.SparkApplication;


public class AngularJSWebappStarter implements SparkApplication {
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( AngularJSWebappStarter.class );
//		ImmutarePollService service = ctx.getBean( ImmutarePollService.class );
		// List<ScoreCard> scoreCards = service.calculateInteresetResponse();

		new DefaultWebConfig(  );
		ctx.registerShutdownHook();
		ctx.close();
	}

	@Override
	public void init() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( AngularJSWebappStarter.class );
//		ImmutarePollService service = ctx.getBean( ImmutarePollService.class );
		// List<ScoreCard> scoreCards = service.calculateInteresetResponse();

		new DefaultWebConfig(  );
		ctx.registerShutdownHook();
		ctx.close();
	}


}
