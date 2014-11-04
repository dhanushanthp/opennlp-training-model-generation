package opennlp.source.phraser;

import java.util.Map;

public class ConceptExtractorTest {

	public static void main(String[] args) {
		ConceptExtractor c = new ConceptExtractor();
		 Map<String, String> phrases = c.getConcept("I like to learn natural language processing and Artificial Intellegence");
		 System.out.println(phrases.keySet());
	}

}
