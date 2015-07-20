package opennlp.source.pos.executor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
		System.out.println(result);
		return po;
	}

	public static void main(String[] args) throws IOException {
		POSObj resu = POSTag("Neil Alden Armstrong (August 5, 1930 – August 25, 2012) was an American astronaut and the first person to walk on the Moon. "
				+ "He was also an aerospace engineer , naval aviator , test pilot , and university professor. "
				+ "Before becoming an astronaut, Armstrong was an officer in the U.S. Navy and served in the Korean War. ");
//		System.out.println(Arrays.toString(resu.getTags()));
//		System.out.println(Arrays.toString(resu.getTokens()));
	}
}
