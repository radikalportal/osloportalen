package no.osloportalen.harvester.news;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Hello world!
 *
 */
public class Harvester {

	private final static int numberOfHarvesters = 1;
	private final static int maxDepthOfHarvesting = 1;
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");

		Harvester.startHarvesting();
	}

	public static Harvester startHarvesting() throws Exception {

		System.out.println("Initializing the harvester");
		List<String> webPagesToVisit = buildWebPagesToVisit();

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder("/home/savuud/osloportal/server/harvester/storage");
		config.setUserAgentString("OsloPortalen innhøster - v0.3a (http://www.osloportalen.no)");
		config.setMaxDepthOfCrawling(maxDepthOfHarvesting);
		
		PageFetcher fetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, fetcher);
		CrawlController controller = new CrawlController(config, fetcher, robotstxtServer);
		
		for (String webURL : webPagesToVisit ) {
			controller.addSeed(webURL);
		}

		controller.start(BasicHarvester.class, numberOfHarvesters);
		
		return null;
	}

	private static final List<String> buildWebPagesToVisit() {
		List<String> webPagesToVisit = new ArrayList<String>();
		webPagesToVisit.add("http://radikalportal.no/osloportalen");
//		webPagesToVisit.add("http://radikalportal.no");
//		webPagesToVisit.add("http://www.db.no");
//		webPagesToVisit.add("http://www.aftenposten.no");
//		webPagesToVisit.add("http://www.vg.no");
		return webPagesToVisit;
	}

}
