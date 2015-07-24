package opennlp.source.pos.builder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.source.chuncker.trainer.TokenObject;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
/**
 * Create part-of-speech tags using opennlp
 * @author root
 *
 */
public class PosBuilder {
	static InputStream modelIn = null;
	static POSModel model = null;

	public static List<TokenObject> getPOSTags(String sentence) {
		List<TokenObject> response = new ArrayList<TokenObject>();
		try {
			modelIn = new FileInputStream("en-pos-maxent.bin");
			model = new POSModel(modelIn);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
				}
			}
		}
		
		POSTaggerME tagger = new POSTaggerME(model);
		String[] tokens = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
		String tags[] = tagger.tag(tokens);

		for (int i = 0; i < tags.length; i++) {
			response.add(new TokenObject(tokens[i], tags[i]));
		}

		return response;
	}
}
