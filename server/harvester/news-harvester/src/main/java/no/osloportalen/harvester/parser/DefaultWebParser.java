package no.osloportalen.harvester.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DefaultWebParser implements Parser {

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
//			System.out.println("============= PARSER ==========================================================");
			for (Element paragraphElement : paragraphElements) {
				if ( "".equals(paragraphElement.attr("class"))) {
//					System.out.println("Paragraph content = " + paragraphElement.html());
//					System.out.println("TEXT content = " + paragraphElement.text());

					htmlContentBuffer.append(paragraphElement.html());
				}
			}
//			System.out.println("================================================================================");
		}

		// System.out.println("============= HTML
		// =============================================================");
		// System.out.println(html);
//		System.out.println("================================================================================");
		System.out.println("============= PARSED CONTENT ===================================================");
		System.out.println(htmlContentBuffer.toString());
		System.out.println("================================================================================");
		
		return htmlContentBuffer.toString();
	}

}
