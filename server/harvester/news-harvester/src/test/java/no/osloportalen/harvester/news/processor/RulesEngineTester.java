package no.osloportalen.harvester.news.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;

import junit.framework.TestCase;

public class RulesEngineTester extends TestCase {

	@Test
	public void testMostBasicVetting() {
		RulesEngine rulesEngine = RulesEngine.initiate();
		String myTestString1 = "Oslo er en flott by med frogner og greier!!";
		String myTestString2 = "Når vi reiser ned fra anla og ned til Oslo sentrum, opplever du en flott atmosfære";
		String myTestString3 = "Det å bo i Trondheim er kjempeflott.. og mye bedre enn å bo på østlandet";

		double vettingResult = rulesEngine.doAnalyzeSingleLineOfWords( myTestString1 );
		System.out.println( "VettingResult: " + vettingResult );
		assertTrue( "Contains Oslo and should be recognized", vettingResult >= 1.0D);

		vettingResult = rulesEngine.doAnalyzeSingleLineOfWords(myTestString2);
		assertTrue("More than one keyword", vettingResult >= 1.0D);

		vettingResult = rulesEngine.doAnalyzeSingleLineOfWords(myTestString3);
		assertTrue("Different city entirely", vettingResult < 1.0D);

		
	}
	
	@Test
	public void testSimilarCharactersMissplaced() {
		assertTrue(RulesEngine.analyzeCharacterForCharacterMatch("lit", "til"));
		assertFalse(RulesEngine.analyzeCharacterForCharacterMatch("lit", "tii"));
		assertTrue(RulesEngine.analyzeCharacterForCharacterMatch("hund", "dnuh"));
		assertTrue(RulesEngine.analyzeCharacterForCharacterMatch("alna", "anla"));
		assertFalse(RulesEngine.analyzeCharacterForCharacterMatch("ensjøbyen", "enn"));
		assertFalse(RulesEngine.analyzeCharacterForCharacterMatch("ensjø", "enn"));
		assertFalse(RulesEngine.analyzeCharacterForCharacterMatch("enn", "ensjøbyen"));
		// This is actually within tolerance since I cannot overload each "filter" with too much complexity
		assertTrue(RulesEngine.analyzeCharacterForCharacterMatch("enn", "ensjø"));
		
	}

	// @Test
	// public void testCharCount() {
	//
	// String word1 = "Stovner";
	// String word2 = "frogner";
	//
	// assertEquals(false, RulesEngine.hasSimilarCharacters(word1, word2));
	//
	// word1 = "Sronger";
	// word2 = "frogner";
	//
	// assertEquals(true, RulesEngine.hasSimilarCharacters(word1, word2));
	//
	///	@Test
//	public void testKeyboardCloseness() {
//	double heatScore = RulesEngine.calculateKeyboardHeatForChar( 'f', 'p' );
//	assertTrue( "Heat should be more than 2", ( heatScore > 2D ) );
//
//	heatScore = RulesEngine.calculateKeyboardHeatForChar( 's', 'f' );
//	assertTrue( "Heat between S and F should be 2", ( heatScore == 2D ) );
//
//	heatScore = RulesEngine.calculateKeyboardHeatForChar( 's', 't' );
//	assertTrue( "Heat between S and F should be more than 3", ( heatScore >= 3D ) );
//
//}/ word1 = "Oslo";
	// word2 = "frogner";
	//
	// assertEquals(false, RulesEngine.hasSimilarCharacters(word1, word2));
	//
	//
	// word1 = "Srogner";
	// word2 = "frogner";
	//
	// assertEquals(true, RulesEngine.hasSimilarCharacters(word1, word2));
	//
	//
	// }

//	@Test
//	public void testKeyboardCloseness() {
//		double heatScore = RulesEngine.calculateKeyboardHeatForChar( 'f', 'p' );
//		assertTrue( "Heat should be more than 2", ( heatScore > 2D ) );
//
//		heatScore = RulesEngine.calculateKeyboardHeatForChar( 's', 'f' );
//		assertTrue( "Heat between S and F should be 2", ( heatScore == 2D ) );
//
//		heatScore = RulesEngine.calculateKeyboardHeatForChar( 's', 't' );
//		assertTrue( "Heat between S and F should be more than 3", ( heatScore >= 3D ) );
//
//	}

	// @Test
	// public void testWordSimilarity() {
	// RulesEngine rulesEngine = RulesEngine.initiate();
	// String myTestString1 = "Olso";
	//
	// float vettingResult = rulesEngine.vettLine(myTestString1);
	// assertEquals(100.0f, vettingResult);
	// }
	//
	// @Test
	// public void testWordLength() {
	// String myTestString1 = "Olso";
	// String myTestString2 = "Olsos";
	//
	// String myTestString3 = "Hønefoss";
	// String myTestString4 = "Olsoss";
	//
	// boolean isSimilarLength = RulesEngine.isOfSimilarLength(myTestString1,
	// myTestString2);
	// assertTrue("Should be similar length", isSimilarLength);
	//
	// isSimilarLength = RulesEngine.isOfSimilarLength(myTestString3,
	// myTestString4);
	// assertTrue("Should not be similar length", !isSimilarLength);
	// }

//	@Test
//	public void testCharacterMatchingSpeed() {
//		char[] charstotest= "Lorem ipsum dolor sit amet, dictas voluptatum ad mea. Simul elitr placerat usu ne, eam option accusam dissentiet no, eius eligendi explicari ea vix. Ei nec volumus fastidii signiferumque, officiis accusata ea vim, dignissim neglegentur ne cum. Et tamquam pertinax est. Mel no quot complectitur, per lucilius ullamcorper te. Est hinc exerci vituperata ei, autem eleifend maiestatis cu mei, et quando lobortis senserit per. Melius latine his ne. Nam dico tation facete ad, eu sed quas ceteros, aliquid definitionem in has. Mel sale minim recteque ex, omnium albucius mel eu. Vis ex equidem assueverit.Feugiat accusamus quo in. Justo eirmod eu mea. Accumsan invidunt nec in, pro populo vituperatoribus ei, quis gubergren forensibus sit ne. Te vim aliquando voluptatum, mucius ridens perfecto in per. Eam hinc inermis convenire cu, assum deseruisse ex vim.Vocibus perpetua temporibus ne sea, ne usu enim voluptua, eos omnes minimum platonem cu. In per utinam ceteros, pri iracundia voluptatum argumentum ut. Agam suavitate suscipiantur vix et. Cum prima solet ei. Cu qui mucius iriure argumentum. Sea ex everti iracundia, ius eius principes et.Ei graeci definitiones duo. Ex eam decore laoreet, ea iriure utroque temporibus est, per in meis dicta. Error noluisse nec no, eam ex regione nonumes expetendis. Ne sed mollis moderatius, ut vidisse delectus consectetuer vim. Possit impedit vim in, magna legendos ei mea. Vel ei clita tation graece.Mentitum philosophia vis cu, quo suas cibo te, mentitum imperdiet cu sit. Eam dolore labores vivendum id. Qui ex convenire ocurreret. Vel congue singulis temporibus ut. Id vitae eruditi eam, simul saperet iudicabit has cu. Te duo aeterno referrentur, eam ad rebum tincidunt.Omnium senserit forensibus ea nam, sed error nemore partiendo ne, ex sed nulla maiorum. Eum cu eirmod habemus tincidunt, amet philosophia disputationi nam eu. At ipsum laoreet eum, idque paulo aliquando mea ea. Sit cu laudem mollis. Has ea suavitate principes signiferumque.Aeterno quaerendum te vim, adhuc porro inermis qui cu. Sit dicta omnes deleniti ne. Apeirian liberavisse his ei, sit ne verear aperiam. Eu essent equidem mandamus eam, eos id porro malis, sea ne malis deterruisset consequuntur.Primis oblique te eam, ne pro malis qualisque. Fuisset conceptam contentiones id cum, etiam volumus te vim. Ea vis liber debitis officiis. Ut est sonet malorum recusabo. Et graece tractatos referrentur pro. Ea labore nemore pri.Quo eius essent et, te euismod consectetuer mel. Has eu evertitur interpretaris. Et sea quando dolorum intellegat, nonumy appetere scripserit vim ei, vix zril tacimates in. Illud ornatus ei pro, diam assum vix cu. Adhuc graecis accumsan qui ut, stet omnis appareat mei ut, ex vide brute aliquip sed. An cum fierent hendrerit, est graece scribentur an, his veritus fuisset torquatos ei. Duo te nostrud intellegebat, cu vix dictas debitis, sed aliquando delicatissimi id.Eos et omnis apeirian, at oblique qualisque eam. Et usu urbanitas assentior deterruisset. Ei eos simul populo deserunt. Sumo suavitate adolescens vis in, sit decore accumsan albucius ad. Per tibique menandri accommodare eu, ea mea quem erroribus. Per id possit eirmod, ne eam errem tractatos inciderint.Ne quo tantas oportere, cu nec omittam sententiae cotidieque. Pro aperiam vivendum eu, in his habeo choro. Graecis accusamus inciderint nam te, usu minim nonumy at. Usu eu sint urbanitas argumentum, cum ea eligendi molestiae. Ad sed prima bonorum philosophia, per ea denique signiferumque, inani solet ne per. Ullum paulo suavitate cu mea, te est admodum omnesque suscipit, sit eu sanctus evertitur reformidans.Ad vim fastidii reformidans, gloriatur delicatissimi ex eos, dolore pertinax vix at. Eros veri pertinacia id eum. Mea oblique diceret percipitur ut, ex sale legimus tincidunt usu. Omnes adipisci percipitur ei has, sea ad illud putent pertinax. Hendrerit mediocritatem usu id.Vix autem labitur lucilius ut. Soleat verterem mei te. Ea vel dicam facilisi adipiscing, omnis iudico te mei. Consequat democritum in usu, at pri numquam ponderum ullamcorper, ei eam esse appareat euripidis. Nec at virtute quaerendum, id pro diam scriptorem.Vis tale munere an, ne mea nisl mucius oportere. Ridens patrioque mel ad. Sea an gubergren voluptatibus, eu nibh falli percipit mea. Vix cu audire rationibus.Tollit torquatos eu nec. Cum ne solet volumus suscipiantur, quot iracundia vel et. Tale dolorum adversarium ad has, has nemore nostrud deleniti in. Omnium ullamcorper at nam, an debitis concludaturque usu. Vim soluta admodum eligendi at, ne consul splendide per, elitr eloquentiam pro ea.Pro id ignota verterem, eum ne modus eirmod. Adhuc legendos mea at, eu duo fugit nominavi. Id zril intellegat nec, an velit sonet splendide eos. At eam suas elitr soleat, dicant possit omnesque at vis, mea ut nullam probatus adipisci. Vim cu choro mnesarchum disputationi, cu labores feugait constituam pri.Vim at sale clita intellegam. Ut mundi periculis usu, porro suscipiantur at sit, vel ea liber aeterno ponderum. Duo in enim possit, mei ex dicat placerat, nam maiorum ocurreret te. Te quando utamur convenire cum, sit praesent philosophia ad, decore facete scripta id est.".toCharArray();
//
//		double timeMillisStart = System.currentTimeMillis();
//		int reverseI = charstotest.length - 1;
//		for ( int i = 0; i < charstotest.length; i++ ) {
//			if (charstotest[i] == charstotest[reverseI] )
//				continue;
//			reverseI--;
//		}
//		double timeMillisStop = System.currentTimeMillis();
//		double timeToProcess = timeMillisStop - timeMillisStart;
//		assertNotNull(timeToProcess);
//		System.out.println( "Equality with '==' took: " + timeToProcess  +  "ms" );
//		timeMillisStart = 0;
//		timeMillisStop = 0;
//		timeMillisStart = System.currentTimeMillis();
//		reverseI = charstotest.length - 1;
//		for ( int i = 0; i < charstotest.length; i++ ) {
//			// This uses autoboxing
//			if (new Character(charstotest[i]).equals( charstotest[reverseI]) )
//				continue;
//			reverseI--;
//		}
//		timeMillisStop = System.currentTimeMillis();
//		timeToProcess = timeMillisStop - timeMillisStart;
//		assertNotNull(timeMillisStart);
//		System.out.println( "Equality with .equals method took: " + timeToProcess + "ms"  );
//
//	
//	}
}
