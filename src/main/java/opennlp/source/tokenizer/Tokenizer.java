package opennlp.source.tokenizer;

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
			String[] result = getTokens("Neil Alden Armstrong (August 5, 1930 – August 25, 2012) was an American astronaut and the first person to walk on the Moon. "
					+ "He was also an aerospace engineer , naval aviator , test pilot , and university professor. "
					+ "Before becoming an astronaut, Armstrong was an officer in the U.S. Navy and served in the Korean War. ");
			System.out.println(Arrays.toString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
