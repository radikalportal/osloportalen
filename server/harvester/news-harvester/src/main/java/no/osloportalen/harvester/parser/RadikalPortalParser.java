package no.osloportalen.harvester.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RadikalPortalParser implements Parser {

	public String doParse(String htmlContent) {
		StringBuffer htmlContentBuffer = new StringBuffer();
		Document doc = Jsoup.parse(htmlContent);
		Elements articleElements = doc.getElementsByTag("article");
		Element articleElement = null;
		if (articleElements != null && !articleElements.isEmpty()) {
			articleElement = articleElements.get(0);
		}

		if (articleElement != null) {
			Elements paragraphElements = articleElement.getElementsByTag("p");
			for (Element paragraphElement : paragraphElements) {
				if ( "".equals(paragraphElement.attr("class"))) {
					htmlContentBuffer.append(paragraphElement.text());
				}
			}
		}

		// System.out.println("============= HTML
		// =============================================================");
		// System.out.println(html);
		System.out.println("================================================================================");
		System.out.println("============= PARSER ==========================================================");
		System.out.println("HTML content = " + htmlContentBuffer.toString());
		System.out.println("================================================================================");
		
		return htmlContentBuffer.toString();
	}

}
