package opennlp.source.pos.executor;

import java.io.FileInputStream;
import java.io.IOException;

import core.util.Config;
import core.util.ReadTxtFile;
import opennlp.source.tokenizer.executor.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;

public class PosTagger {
	public static POSObj POSTag(String input) throws IOException {
		POSModel model = new POSModel(new FileInputStream(Config.getModelDataPath() + "en-pos.bin"));
		POSTaggerME tagger = new POSTaggerME(model);

		String tokenWords[] = Tokenizer.getTokens(input);
		String[] tags = tagger.tag(tokenWords);
		POSSample result = new POSSample(tokenWords, tags);
		POSObj po = new POSObj(tokenWords, tags);
		System.out.println(result);
		return po;
	}

	public static void main(String[] args) throws IOException {
		POSObj resu = POSTag(ReadTxtFile.getString("build-training-models/paragraph.txt"));
//		System.out.println(Arrays.toString(resu.getTags()));
//		System.out.println(Arrays.toString(resu.getTokens()));
	}
}
