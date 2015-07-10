package opennlp.source.namefinder.executor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import opennlp.source.phraser.ConceptExtractor;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class NameFinder {
	private static final InputStream modelInSentence;
	private static final InputStream modelInToken;
	private static TokenNameFinderModel sentenceModel;
	private static TokenizerModel tokenModel;

	static {
		modelInSentence = ConceptExtractor.class.getResourceAsStream("/opennlp/en-ner-person.bin");
		modelInToken = ConceptExtractor.class.getResourceAsStream("/opennlp/en-token.bin");
		try {
			sentenceModel = new TokenNameFinderModel(modelInSentence);
			tokenModel = new TokenizerModel(modelInToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		String input = "James B Stewart Common Sense column observes Apple, formerly market laggard, has far distanced Microsoft in share price since January 2014";
		NameFinderME nameFinder = new NameFinderME(sentenceModel);
		TokenizerME tokenizer = new TokenizerME(tokenModel);

		String[] splitTest = tokenizer.tokenize(input);
		Span[] sentences = nameFinder.find(splitTest);
		System.out.println(Arrays.toString(sentences));
		for (Span s : sentences) {
			StringBuilder string = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				string.append((splitTest[i]) + " ");
			}
			System.out.println(string.toString());
		}
	}
}
