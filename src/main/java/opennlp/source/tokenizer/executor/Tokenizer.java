package opennlp.source.tokenizer.executor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import core.util.Config;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {

	private static InputStream modelInToken = null;
	private static TokenizerModel tokenModel;

	static {
		try {
			modelInToken = new FileInputStream(Config.getModelDataPath() + "en-token.bin");
			tokenModel = new TokenizerModel(modelInToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getTokens(String sentence) throws InvalidFormatException, IOException {
		TokenizerME tokenizer = new TokenizerME(tokenModel);
		String tokens[] = tokenizer.tokenize(sentence);
		return tokens;
	}

	public static void main(String[] args) {
		try {
			String[] result = getTokens("“James B Stewart” Common Sense column observes Apple 75%, formerly market laggard, has far distanced Microsoft in share price since January 2014.");
			System.out.println(Arrays.toString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
