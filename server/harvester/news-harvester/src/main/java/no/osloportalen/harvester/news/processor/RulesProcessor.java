package no.osloportalen.harvester.news.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RulesProcessor {

	private static Map<String, Integer> keyWords = new HashMap<String, Integer>();
	
	public RulesProcessor() {
		super();
	}
	
	public RulesProcessor(String OsloPortalenFile) {
		getBasicRules(OsloPortalenFile);
	}
	
	private void getBasicRules(String OsloPortalenFile) {

		try {
			FileReader reader = new FileReader(new File(OsloPortalenFile));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			int count = 0;
			while( (line = bufferedReader.readLine()) != null ) {
				String[] parts = line.split("\t");
				if ( count > 2 ) {
					System.out.print("Part1 : " + parts[0]);
					System.out.println("Part2 : " + parts[1]);
					keyWords.put(parts[0], 100);
//					System.out.println(line);
				}
				count++;
			}
			bufferedReader.close();
			reader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
