package opennlp.source.namefinder.executor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class NameFinderO {
	private static final InputStream modelInName;
	private static final InputStream modelInToken;
	private static TokenNameFinderModel sentenceModel;
	private static TokenizerModel tokenModel;

	static {
		modelInName = NameFinderO.class.getResourceAsStream("/opennlp/my-name-model.bin");
		modelInToken = NameFinderO.class.getResourceAsStream("/opennlp/en-token.bin");
		try {
			sentenceModel = new TokenNameFinderModel(modelInName);
			tokenModel = new TokenizerModel(modelInToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		String input = "where the Kharijites insisted that the imamate is a right for each individual within the Islamic society. Later, some Muslim scholars, such as Amer al-Basri and Abu Hanifa, led movements of boycotting the rulers, paving the way to the waqf (endowments) tradition, which served as an alternative to and asylum from the centralized authorities of the emirs.";
		NameFinderME nameFinder = new NameFinderME(sentenceModel);
		TokenizerME tokenizer = new TokenizerME(tokenModel);

		String[] splitTest = tokenizer.tokenize(input);
		Span[] sentences = nameFinder.find(splitTest);
		for (Span s : sentences) {
			StringBuilder string = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				string.append((splitTest[i]) + " ");
			}
			System.out.println(string.toString());
		}
	}
}
