package opennlp.source.pos.executor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import opennlp.source.tokenizer.executor.Tokenizer;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class PosTagger {
	public static POSObj POSTag(String input) throws IOException {
		POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
		POSTaggerME tagger = new POSTaggerME(model);

//		String tokenWords[] = WhitespaceTokenizer.INSTANCE.tokenize(input);
		String tokenWords[] = Tokenizer.getTokens(input);
		String[] tags = tagger.tag(tokenWords);
		POSSample result = new POSSample(tokenWords, tags);
		POSObj po = new POSObj(tokenWords, tags);
		System.out.println(result);
		return po;
	}

	public static void main(String[] args) throws IOException {
		POSObj resu = POSTag("“James B Stewart“ Common Sense column observes Apple, formerly market laggard, has far distanced Microsoft in share price since January 2014");
//		System.out.println(Arrays.toString(resu.getTags()));
//		System.out.println(Arrays.toString(resu.getTokens()));
	}
}
