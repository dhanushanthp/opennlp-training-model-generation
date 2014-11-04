package opennlp.source.phraser;

import java.io.IOException;
import java.util.Map;

import opennlp.tools.util.InvalidFormatException;

public class ConceptExtractorTest {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		ConceptExtractor c = new ConceptExtractor();
		String[] input = { "The aggregate demand (AD) curve slopes (cs) downward",
				"The aggregate demand (AD) curve slopes downward, reflecting the fact that the aggregate quantity of goods and services demanded, Y, falls when the price level, P, rises",
				"I like to learn natural language processing and Artificial Intellegence" };
		 Map<String, String> phrases = c.getConcept(input[0]);
		 System.out.println(phrases.keySet());
	}

}
