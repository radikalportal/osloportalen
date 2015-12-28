package no.osloportalen.harvester.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import no.osloportalen.harvester.news.BasicHarvester;
import no.osloportalen.harvester.news.processor.RulesEngine;

/**
 * Hello world!
 *
 */
public class HarvestRunner {

	private final static int numberOfHarvesters = 1;
	private final static int maxDepthOfHarvesting = 1;
	private static Map<String, Integer> keyWords = new HashMap<String, Integer>();
	private static RulesEngine rulesEngine;
	

	public static void main(String[] args) throws Exception {
		System.out.println("Yum yum!");

		startRuleEngine();

//		HarvestRunner.startHarvesting();
		System.out.println("Ahhh.. That was good!");
	}

	private static void startRuleEngine() {
		rulesEngine = new RulesEngine();
	}

	public static HarvestRunner startHarvesting() throws Exception {

		System.out.println("Initializing the harvester");
		List<String> webPagesToVisit = buildWebPagesToVisit();

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder("/Users/kjella/Documents/work/Private/osloportal/server/harvester/storage");
		config.setUserAgentString("OsloPortalen innhøster - v0.3a (http://www.osloportalen.no)");
		config.setMaxDepthOfCrawling(maxDepthOfHarvesting);

		PageFetcher fetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, fetcher);
		CrawlController controller = new CrawlController(config, fetcher, robotstxtServer);

		for (String webURL : webPagesToVisit) {
			controller.addSeed(webURL);
		}

		controller.start(BasicHarvester.class, numberOfHarvesters);
		return null;
	}

	private static final List<String> buildWebPagesToVisit() {
		List<String> webPagesToVisit = new ArrayList<String>();
		webPagesToVisit.add("http://radikalportal.no/osloportalen");
		// webPagesToVisit.add("http://radikalportal.no");
		// webPagesToVisit.add("http://www.db.no");
		// webPagesToVisit.add("http://www.aftenposten.no");
		// webPagesToVisit.add("http://www.vg.no");
		return webPagesToVisit;
	}

}
