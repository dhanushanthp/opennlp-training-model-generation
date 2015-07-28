package stanford.source.tokenizer;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

public class TokenizerDemo {

	public static void main(String[] args) throws IOException {
		// option #1: By sentence.
		// DocumentPreprocessor dp = new DocumentPreprocessor(arg);
		// for (List<HasWord> sentence : dp) {
		// System.out.println(sentence);
		// }
		// option #2: By token
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new FileReader("build-training-models/paragraph.txt"),
				new CoreLabelTokenFactory(), "");
		while (ptbt.hasNext()) {
			CoreLabel label = ptbt.next();
			System.out.println(label);
		}
	}
}