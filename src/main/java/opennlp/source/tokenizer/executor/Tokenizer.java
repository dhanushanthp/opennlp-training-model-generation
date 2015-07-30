package opennlp.source.tokenizer.executor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {
	private static final Logger LOG = LoggerFactory.getLogger(Tokenizer.class);
	private static InputStream modelInToken = null;
	private static TokenizerModel tokenModel;

	static {
		try {
			modelInToken = new FileInputStream(Config.getModelDataPath() + "en-token.bin");
			tokenModel = new TokenizerModel(modelInToken);
			LOG.info("Token model has been loaded from " + Config.getModelDataPath() + "en-token.bin");
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
			String[] result = getTokens(ReadTxtFile.getString("build-training-models/paragraph.txt"));
			for (String string : result) {
				System.out.println(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
