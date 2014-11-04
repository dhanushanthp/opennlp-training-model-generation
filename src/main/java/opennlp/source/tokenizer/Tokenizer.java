package opennlp.source.tokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {

	public String[] tokenizer() throws InvalidFormatException, IOException {
		InputStream is = new FileInputStream("en-token.bin");
		TokenizerModel model = new TokenizerModel(is);
		TokenizerME tokenizer = new TokenizerME(model);

		String tokens[] = tokenizer.tokenize("it's not a correct way to implement it, isn't it?	");

		is.close();
		return tokens;
	}

	public static void main(String[] args) throws InvalidFormatException,
			IOException {
		Tokenizer t = new Tokenizer();

		for (String a : t.tokenizer())
			System.out.println(a);

	}

}
