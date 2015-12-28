package no.osloportalen.harvester.news.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;

import junit.framework.TestCase;


public class RulesEngineTester extends TestCase {

//	@Test
//	public void testMostBasicVetting() {
//		RulesEngine rulesEngine = RulesEngine.initiate();
//		String myTestString1 = "Oslo er en flott by med frogner og greier!!";
//		String myTestString2 = "Når vi reiser ned fra anla og ned til Oslo sentrum, opplever du en flott atmosfære";
//		
//		float vettingResult = rulesEngine.vettLine(myTestString1);
//		System.out.println("VettingResult: " + vettingResult);
//		assertEquals(100.0f, vettingResult);
//		
////		vettingResult = rulesEngine.vettLine(myTestString2);
////		assertTrue("More than one keyword", vettingResult > 100.0f);
//
//	}

//	@Test
//	public void testCharCount() {
//
//		String word1 = "Stovner";
//		String word2 = "frogner";
//
//		assertEquals(false, RulesEngine.hasSimilarCharacters(word1, word2));
//
//		word1 = "Sronger";
//		word2 = "frogner";
//
//		assertEquals(true, RulesEngine.hasSimilarCharacters(word1, word2));
//
//		word1 = "Oslo";
//		word2 = "frogner";
//
//		assertEquals(false, RulesEngine.hasSimilarCharacters(word1, word2));
//
//		
//		word1 = "Srogner";
//		word2 = "frogner";
//
//		assertEquals(true, RulesEngine.hasSimilarCharacters(word1, word2));
//
//		
//	}
	
	
	@Test
	public void testKeyboardCloseness() {
		double heatScore = RulesEngine.calculateKeyboardHeatForChar('f', 'p');
		assertTrue("Heat should be more than 2", (heatScore > 2D));

		heatScore = RulesEngine.calculateKeyboardHeatForChar('s', 'f');
		assertTrue("Heat between S and F should be 2", (heatScore == 2D));

		heatScore = RulesEngine.calculateKeyboardHeatForChar('s', 't');
		assertTrue("Heat between S and F should be more than 3", (heatScore >= 3D));

	}
	
	
//	@Test
//	public void testWordSimilarity() {
//		RulesEngine rulesEngine = RulesEngine.initiate();
//		String myTestString1 = "Olso";
//		
//		float vettingResult = rulesEngine.vettLine(myTestString1);
//		assertEquals(100.0f, vettingResult);
//	}
//
//	@Test
//	public void testWordLength() {
//		String myTestString1 = "Olso";
//		String myTestString2 = "Olsos";
//
//		String myTestString3 = "Hønefoss";
//		String myTestString4 = "Olsoss";
//	
//		boolean isSimilarLength = RulesEngine.isOfSimilarLength(myTestString1, myTestString2);
//		assertTrue("Should be similar length", isSimilarLength);
//		
//		isSimilarLength = RulesEngine.isOfSimilarLength(myTestString3, myTestString4);
//		assertTrue("Should not be similar length", !isSimilarLength);
//	}

	
}
