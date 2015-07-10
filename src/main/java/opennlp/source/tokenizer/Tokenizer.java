package opennlp.source.tokenizer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import opennlp.source.phraser.ConceptExtractor;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {

	private static final InputStream modelInToken;
	private static TokenizerModel tokenModel;

	static {
		modelInToken = ConceptExtractor.class.getResourceAsStream("/opennlp/en-token.bin");
		try {
			tokenModel = new TokenizerModel(modelInToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		TokenizerME tokenizer = new TokenizerME(tokenModel);
		String tokens[] = tokenizer.tokenize("it's not a correct way to implement it, isn't it?	");
		System.out.println(Arrays.toString(tokens));
	}

}
