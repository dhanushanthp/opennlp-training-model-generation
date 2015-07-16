package opennlp.source.postagger;

import java.io.File;
import java.io.IOException;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class PosTagger {
	public static POSObj POSTag(String input) throws IOException {
		POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
		POSTaggerME tagger = new POSTaggerME(model);

		String tokenWords[] = WhitespaceTokenizer.INSTANCE.tokenize(input);
		String[] tags = tagger.tag(tokenWords);
		POSSample result = new POSSample(tokenWords, tags);

		POSObj po = new POSObj(tokenWords, tags);
		return po;
	}

	public static void main(String[] args) throws IOException {
		POSObj resu = POSTag("And the way algorithms implemented is so nice.");
		System.out.println(resu.getTags());
		System.out.println(resu.getTokens());
	}
}
