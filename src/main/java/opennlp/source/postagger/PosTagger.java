package opennlp.source.postagger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class PosTagger {
	public static void POSTag() throws IOException {
		String input = "it's not a correct way to implement it, isn't it?";
		POSModel model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
		POSTaggerME tagger = new POSTaggerME(model);

		String tokenWords[] = WhitespaceTokenizer.INSTANCE.tokenize(input);
		
		String[] tags = tagger.tag(tokenWords);

		POSSample result = new POSSample(tokenWords, tags);
		
		System.out.println("Tags : \n" + Arrays.toString(tags));
		System.out.println("Words : \n" + Arrays.toString(tokenWords));
		System.out.println("Combined : \n" + result.toString());

	}

	public static void main(String[] args) throws IOException {
		POSTag();
	}

}
