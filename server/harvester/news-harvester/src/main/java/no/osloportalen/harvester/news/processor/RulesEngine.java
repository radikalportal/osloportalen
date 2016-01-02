package no.osloportalen.harvester.news.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RulesEngine {
	private static final String DEFAULT_PROCESSING_RULES = "OsloPortalen.tsv";
	private static Map<String, Integer> keyWords = new HashMap<String, Integer>();
	private static RulesEngine rulesEngineCached;

	private Logger logger = LogManager.getLogger( RulesEngine.class );

	public RulesEngine() {
		super();
	}

	public static RulesEngine initiate() {
		if ( rulesEngineCached == null ) {
			rulesEngineCached = new RulesEngine();
			rulesEngineCached.loadRulesList( DEFAULT_PROCESSING_RULES );
		}
		return rulesEngineCached;
	}

	public static final RulesEngine getRulesEngine() {
		if ( rulesEngineCached != null ) {
			return rulesEngineCached;
		}
		return null;
	}

	private void loadRulesList(String OsloPortalenFile) {

		try {
			FileReader reader = new FileReader( new File( OsloPortalenFile ) );
			BufferedReader bufferedReader = new BufferedReader( reader );
			String line;
			int count = 0;
			while ( ( line = bufferedReader.readLine() ) != null ) {
				String[] parts = line.split( "\t" );
				if ( count > 0 ) {
					// System.out.print( "Navn : " + StringUtils.lowerCase(
					// parts[0] ) );
					// System.out.println( " Score : " + parts[1] );
					keyWords.put( StringUtils.lowerCase( parts[0] ), 100 );
					// System.out.println(line);
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

	public double doAnalyzeSingleLineOfWords(String lineToAnalyze) {
		double confidenceLevelForLine = 0.0;
		if ( "".equals( lineToAnalyze ) ) {
			// logger.debug("No content to analyze");
		}

		// 1. Correct misspelled words
		// 2. Count all the occurrences of keywords in the line
		// System.out.println( "Starting to analyze line: \"" + lineToAnalyze +
		// "\"" );
		System.out.println( "========================================================================================" );
		System.out.println( "ANALYZING line \"" + lineToAnalyze + "\"..." );
		System.out.println( "----------------------------------------------------------------------------------------" );

		String[] words = StringUtils.split( lineToAnalyze );
		for ( String lineWord : words ) {
			if ( "".equals( lineWord ) ) {
				break;
			}

			System.out.println( "----------------------------------------------------------------------------------------" );
			System.out.println( "ANALYZING word \"" + lineWord + "\" against registered keyWords..." );
			System.out.println( "----------------------------------------------------------------------------------------" );

			if ( keyWords.containsKey( StringUtils.lowerCase( lineWord ) ) ) {
				System.out.println( "Found an exact match... Confidence increased to 100%, no point to continue" );
				confidenceLevelForLine = 1.0;
				break;
			}

			for ( Map.Entry<String, Integer> entry : keyWords.entrySet() ) {
				String keyWord = entry.getKey();
				Integer keyWordScore = entry.getValue();
				// System.out.print( "*" );
				// System.out.println( "KeyWord: \"" + keyWord + "\"" );

				if ( areWordsOfSimilarLength( lineWord, keyWord ) ) {
					// We increase confidence because the length of the words
					// are
					// somewhat similar
					// confidenceLevelForLine += 0.005;
					System.out.println();
					System.out.println( "Potential size match between \"" + keyWord + "\" and \"" + lineWord + "\"!" );
				}

				boolean charactersMatch = analyzeCharacterForCharacterMatch( lineWord, keyWord );
				if ( charactersMatch ) {
					System.out.println( "Increase confidence level. Characters match but are misplaced. \"" + keyWord + "\" and \"" + lineWord + "\"!" );
					confidenceLevelForLine += 0.15;
				}
				// Create heat map for each word. This should indicate if
				// characters not matching could be the result of a "keyboard
				// misshap".

				int levenshteinScore = LevenshteinDistance( StringUtils.lowerCase( lineWord ), StringUtils.lowerCase( keyWord ) );
				double minimumScoreBeforeActivation = Math.ceil( lineWord.length() * 0.25 );
				if ( lineWord.length() <= 4 ) {
					minimumScoreBeforeActivation = 2;
				}
				// System.out.println( "Lev: [Must have " +
				// minimumScoreBeforeActivation + " score. Actual score is: " +
				// levenshteinScore + "]");

				if ( lineWord.length() >= 4 && levenshteinScore <= minimumScoreBeforeActivation ) {
					// We increase confidence because amount of character that
					// needs repositioning to match is low
					confidenceLevelForLine += 0.3;

					System.out.print( "LevenshteinDistance is : " + levenshteinScore + " and is therefore: " );
					System.out.println( "Similar enough! **" );

					// analyzeWordForKeyboardMisshap( lineWord, keyWord );

					lineWord = keyWord;
					break;
				} else {
					// System.out.println( "Too unlike!" );
				}

			}
			System.out.println( "Current confidence level is: " + confidenceLevelForLine );
			System.out.println( "--------------------------------------------------------------------------------------" );
		}
		System.out.println( "Overall confidence level is: " + confidenceLevelForLine );
		System.out.println( "========================================================================================" );

		return confidenceLevelForLine;
	}

	public static boolean analyzeCharacterForCharacterMatch(String lineWord, String keyWord) {
		int wordShortestLength = 0;
		int wordLongestLength = 0;
		boolean tooDifferentSizesToDoAMatch = false;
		int lastPosition = 0;
		if ( lineWord.length() < keyWord.length() ) {
			wordShortestLength = lineWord.length();
			wordLongestLength = keyWord.length();
		} else {
			wordShortestLength = keyWord.length();
			wordLongestLength = lineWord.length();
		}

		if ( wordLongestLength - wordShortestLength > ( Math.ceil( wordShortestLength * 1.15 ) ) ) {
			tooDifferentSizesToDoAMatch = true;
		}

		if ( tooDifferentSizesToDoAMatch ) {
			// System.out.println( "Asked to compare strings that are tooo
			// different in sizes" );
			return false;
		}

		boolean doesCharacterMatch = true;
		boolean[] multipleTruths = new boolean[wordShortestLength];
		// System.out.println( "Checking " + wordShortestLength );
		char[] lineWordChars = lineWord.toCharArray();
		char[] keyWordChars = keyWord.toCharArray();
		for ( int i = 0; i < wordShortestLength; i++ ) {
			// System.out.println( "Analyzing " + lineWordChars[i] );
			multipleTruths[i] = false;
			for ( int y = 0; y < wordShortestLength; y++ ) {
				// System.out.println( lineWordChars[i] + "==" + keyWordChars[y]
				// + "?" );
				if ( lineWordChars[i] == keyWordChars[y] ) {
					// System.out.println( i + " is true" );
					multipleTruths[i] = true;
				}
			}
			// System.out.println( "Did I find " + lineWordChars[i] + "? " +
			// multipleTruths[i] );
		}
		for ( int j = 0; j < multipleTruths.length; j++ ) {
			// System.out.println( "Was pos " + j + " true? " +
			// multipleTruths[j]);
			if ( !multipleTruths[j] ) {
				doesCharacterMatch = false;
				break;
			}
		}
		// System.out.println( "returning " + doesCharacterMatch );
		// System.out.println(
		// "---------------------------------------------------------" );
		return doesCharacterMatch;
	}

	private static void analyzeWordForKeyboardMisshap(String lineWord, String keyWord) {
		int wordShortestLength = 0;
		if ( lineWord.length() < keyWord.length() ) {
			wordShortestLength = lineWord.length();
		} else {
			wordShortestLength = keyWord.length();
		}
		char[] lineWordCharArray = lineWord.toCharArray();
		char[] keyWordCharArray = keyWord.toCharArray();
		boolean foundAccidentalyTypedCharacter = false;
		for ( int i = 0; i < wordShortestLength; i++ ) {
			char lineWordChar = lineWordCharArray[i];
			char keyWordChar = keyWordCharArray[i];

			double heatScore = calculateKeyboardHeatForChar( lineWordChar, keyWordChar );
			if ( heatScore >= 2 ) {
				System.out.println( "Heat score is too high too consider that " + lineWordChar + " was misspelled and actually meant: " + keyWordChar );
				foundAccidentalyTypedCharacter = false;
			}

			System.out.println( "Heatscore for " + ( i + 1 ) + " character \"" + lineWordChar + "\"/\"" + keyWordChar + "\" is: " + heatScore );
		}
		if ( !foundAccidentalyTypedCharacter ) {
			System.out.println( "We can confidentally claim that this word was not misspelled by hitting the wrong key because of keyboard size restrictions..." );
		}

	}

	public static boolean hasSimilarCharacters(String word1, String word2) {

		System.out.println( "Checking if Word1 (\"" + word1 + "\") is similar to word2 (\"" + word2 + "\")" );
		int longestWordCharCount = 0;
		if ( word1.length() > word2.length() ) {
			longestWordCharCount = word1.length();
		} else {
			longestWordCharCount = word2.length();
		}
		char[] word1Chars = word1.toCharArray();
		char[] word2Chars = word2.toCharArray();
		int countSimilar = 0;
		double maxScore = Math.pow( word2.length(), 2.0 );
		System.out.println( "MaxScore = " + maxScore );
		for ( int word1Count = 0; word1Count < word1Chars.length; word1Count++ ) {
			Character word1Char = word1Chars[word1Count];
			boolean wasWord1CharFound = false;
			Integer char1Location = null;
			Integer char1OffsetPos = null;
			for ( int word2Count = 0; word2Count < word2Chars.length; word2Count++ ) {
				int offsetPos = word1Chars.length - word2Count;
				Character word2Char = word2Chars[word2Count];
				if ( word1Char.equals( word2Char ) ) {
					wasWord1CharFound = true;
					char1Location = word2Count;
					// System.out.println("\"" + word1Char + "\" was found in
					// pos: " + char1Location + " score: " + score);
					char1OffsetPos = offsetPos;
					// countSimilar += offsetPos;
				}
			}
			if ( wasWord1CharFound ) {
				System.out.println( "\"" + word1Char + "\" was found at location: \"" + char1Location + "\" which is offset by: " + char1OffsetPos );
			} else {
				System.out.println( "\"" + word1Char + "\" was not found at all!" );
			}
		}
		System.out.println( "Word1 (\"" + word1 + "\") and word2 (\"" + word2 + "\") has " + countSimilar + " similar characters of " + longestWordCharCount + " possible..." );
		double currentScore = Math.ceil( countSimilar * 1.2 );
		System.out.println( "adjusted 20% score: " + currentScore );
		if ( currentScore >= maxScore ) {
			System.out.println( "The score is within 20% of maxScore.. I will call that a success... " );
			return true;
		}

		return false;
	}

	public static boolean areWordsOfSimilarLength(CharSequence word1, CharSequence word2) {
		/*
		 * Is a word of similar length? We take the amount of characters and
		 * have a 10% tolerance in difference in length
		 */

		if ( word1.length() <= 4 && word2.length() <= 4 ) {
			return false;
		}

		if ( ( word1 == null || "".equals( word1 ) ) || ( word2 == null || "".equals( word2 ) ) ) {
			return false;
		}

		int word1Length = word1.length();
		int word2Length = word2.length();
		// System.out.println("Word 1 length: " + word1Length);
		// System.out.println("Word 2 length: " + word2Length);
		if ( word1Length == word2Length ) {
			// System.out.println("Equal length...");
			return true;
		}

		int shortestWordLength;
		int longestWordLength;
		if ( word1Length < word2Length ) {
			shortestWordLength = word1Length;
			longestWordLength = word2Length;
		} else {
			shortestWordLength = word2Length;
			longestWordLength = word1Length;
		}

		double smallestWordStretched = Math.ceil( shortestWordLength * 1.1 );

		// System.out.println("Stretched smallest word with 10%: " +
		// smallestWordStretched);

		// System.out.println("Is " + word2Length + " > " + word1Length + " && "
		// + longestWordLength + " <= " + smallestWordStretched);
		if ( longestWordLength <= smallestWordStretched ) {
			// System.out.println("Stretched match");
			return true;
		}

		return false;
	}

	public static int LevenshteinDistance(CharSequence lhs, CharSequence rhs) {
		int len0 = lhs.length() + 1;
		int len1 = rhs.length() + 1;

		// the array of distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];

		// initial cost of skipping prefix in String s0
		for ( int i = 0; i < len0; i++ )
			cost[i] = i;

		// dynamically computing the array of distances

		// transformation cost for each letter in s1
		for ( int j = 1; j < len1; j++ ) {
			// initial cost of skipping prefix in String s1
			newcost[0] = j;

			// transformation cost for each letter in s0
			for ( int i = 1; i < len0; i++ ) {
				// matching current letters in both strings
				int match = ( lhs.charAt( i - 1 ) == rhs.charAt( j - 1 ) ) ? 0 : 1;

				// computing cost for each transformation
				int cost_replace = cost[i - 1] + match;
				int cost_insert = cost[i] + 1;
				int cost_delete = newcost[i - 1] + 1;

				// keep minimum cost
				newcost[i] = Math.min( Math.min( cost_insert, cost_delete ), cost_replace );
			}

			// swap cost/newcost arrays
			int[] swap = cost;
			cost = newcost;
			newcost = swap;
		}

		// the distance is the cost for transforming all letters in both strings
		return cost[len0 - 1];
	}

	public static final double calculateKeyboardHeatForChar(Character currentCharacter, Character otherCharacter) {
		Map<Integer, String> keyboardMapping = new HashMap<Integer, String>();
		// This is first attempt to map keyboard character location on a grid
		keyboardMapping.put( 1, "qwertyuiopå" );
		keyboardMapping.put( 2, "asdfghjkløæ" );
		keyboardMapping.put( 3, "zxcvbnm" );

		Character charTypedByDrunkGuy = Character.toLowerCase( currentCharacter );
		otherCharacter = Character.toLowerCase( otherCharacter );
		int charXCoordinate = 0;
		int charYCoordinate = 0;

		for ( Integer row : keyboardMapping.keySet() ) {
			// Looking for +charToLookFor
			String charactersInRow = keyboardMapping.get( row );

			char[] allCharactersInARow = charactersInRow.toCharArray();
			for ( int i = 0; i < allCharactersInARow.length; i++ ) {
				if ( charTypedByDrunkGuy.equals( allCharactersInARow[i] ) ) {
					charXCoordinate = i + 1;
					charYCoordinate = row;
				}
			}
		}

		// System.out.println( "Coordinate for " + charTypedByDrunkGuy + " is: "
		// + charXCoordinate + ", " + charYCoordinate );

		for ( Integer row : keyboardMapping.keySet() ) {
			String charactersInRow = keyboardMapping.get( row );
			char[] allCharactersInARow = charactersInRow.toCharArray();
			for ( int i = 0; i < allCharactersInARow.length; i++ ) {
				Character currentChar = allCharactersInARow[i];
				int currentXPos = i + 1;
				int currentYPos = row;
				int xd = currentXPos - charXCoordinate;
				int yd = currentYPos - charYCoordinate;
				double heatDistance = Math.sqrt( xd * xd + yd * yd );

				if ( currentChar.equals( otherCharacter ) ) {
					System.out.print( "Heat for character \"" + currentChar + "\" is : " );
					System.out.println( heatDistance );
					return heatDistance;
				}
			}
		}
		return 900.0;
	}

}
