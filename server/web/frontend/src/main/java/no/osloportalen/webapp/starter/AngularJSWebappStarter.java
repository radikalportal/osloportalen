package no.osloportalen.webapp.starter;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class AngularJSWebappStarter {
	public static void main(String[] args) throws Exception {
		// The simple Jetty config here will serve static content from the
		// webapp directory
		String webappDirLocation = "src/main/webapp/";

		// The port that we should run on can be set into an environment
		// variable
		// Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv( "PORT" );
		if ( webPort == null || webPort.isEmpty() ) {
			webPort = "9000";
		}
		Server server = new Server( Integer.valueOf( webPort ) );

		// WebAppContext webapp = new WebAppContext();
		// webapp.setContextPath( "/" );
		// webapp.setDescriptor( webappDirLocation + "/WEB-INF/web.xml" );
		// webapp.setResourceBase( webappDirLocation );

		ContextHandler baseContext = new ContextHandler();
		baseContext.setContextPath( "/" );
		baseContext.setResourceBase( webappDirLocation );

		ContextHandler cssContext = new ContextHandler();
		cssContext.setContextPath( "/css" );
		cssContext.setResourceBase( webappDirLocation );
		
		ResourceHandler baseResourceHandler = new ResourceHandler();

		baseResourceHandler.setDirectoriesListed( true );
		baseResourceHandler.setWelcomeFiles( new String[] { "index.html" } );
		baseResourceHandler.setResourceBase( webappDirLocation );
		baseContext.setHandler( baseResourceHandler );

		ResourceHandler cssResourceHandler = new ResourceHandler();
		cssResourceHandler.setDirectoriesListed( true );
		cssResourceHandler.setResourceBase( webappDirLocation + "/css" );
		
		cssContext.setHandler( cssResourceHandler );

		HandlerList handlers = new HandlerList();
		handlers.setHandlers( new Handler[] { baseContext, cssContext } );
		// handlers.setHandlers( new Handler[]{ cssContextHandler, new
		// DefaultHandler() } );
		// handlers.setHandlers(new Handler[] { resource_handler, new
		// DefaultHandler() });

		server.setHandler( handlers );

		// server.setHandler( webapp );
		server.start();
		System.out.println(server.dump());
		server.join();

	}

}
