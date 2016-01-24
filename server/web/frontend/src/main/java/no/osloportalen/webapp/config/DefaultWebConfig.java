package no.osloportalen.webapp.config;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class DefaultWebConfig {

	public DefaultWebConfig() {
		// this.service = service;
		staticFileLocation( "/public" );
		setupRoutes();
	}

	private void setupRoutes() {
		/*
		 * Shows a users timeline or if no user is logged in, it will redirect
		 * to the public timeline. This timeline shows the user's messages as
		 * well as all the messages of followed users.
		 */
		get( "/", (req, res) -> {

			return new ModelAndView( null, "frontpage.ftl" );

		} , new FreeMarkerEngine() );

		// before("/", (req, res) -> {
		// User user = getAuthenticatedUser(req);
		// if (user == null) {
		// res.redirect("/public");
		// halt();
		// }
		// }
		// );

		// post("/message", (req, res) -> {
		// User user = getAuthenticatedUser(req);
		// MultiMap<String> params = new MultiMap<String>();
		// UrlEncoded.decodeTo(req.body(), params, "UTF-8", -1);
		// Message m = new Message();
		// m.setUserId(user.getId());
		// m.setPubDate(new Date());
		// BeanUtils.populate(m, params);
		// service.addMessage(m);
		// res.redirect("/");
		// return null;
		// });
		/*
		 * Checks if the user is authenticated
		 */
		// before("/message", (req, res) -> {
		// User authUser = getAuthenticatedUser(req);
		// if (authUser == null) {
		// res.redirect("/login");
		// halt();
		// }
		// });

		/*
		 * Logs the user out and redirects to the public timeline
		 */
		get( "/logout", (req, res) -> {
			// removeAuthenticatedUser(req);
			res.redirect( "/public" );
			return null;
		} );
	}
}
