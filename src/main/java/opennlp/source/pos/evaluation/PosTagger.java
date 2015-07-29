package opennlp.source.pos.evaluation;

import java.io.FileInputStream;
import java.io.IOException;
import core.util.Config;
import core.util.ReadTxtFile;
import opennlp.source.tokenizer.executor.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class PosTagger {
	public static POSObj POSTag(String input) throws IOException {
		POSModel model = new POSModel(new FileInputStream(Config.getModelDataPath() + "en-pos.bin"));
		POSTaggerME tagger = new POSTaggerME(model);

		String tokenWords[] = Tokenizer.getTokens(input);
		String[] tags = tagger.tag(tokenWords);
		POSObj po = new POSObj(tokenWords, tags);
		return po;
	}

	public static void main(String[] args) throws IOException {
		POSObj resu = POSTag(ReadTxtFile.getString("build-training-models/paragraph.txt"));

		for (int i = 0; i < resu.getTags().length; i++) {
			System.out.println(resu.getTokens()[i] + " _ " + resu.getTags()[i]);
		}
	}
}
