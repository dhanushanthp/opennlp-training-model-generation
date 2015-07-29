package opennlp.source.pos.executor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import core.util.Config;
import opennlp.source.chuncker.trainer.TokenObject;
import opennlp.source.tokenizer.executor.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.InvalidFormatException;
/**
 * Create part-of-speech tags using opennlp
 * @author root
 *
 */
public class PosExecutor {
	static InputStream modelIn = null;
	static POSModel model = null;

	public static List<TokenObject> getPOSTags(String sentence) throws InvalidFormatException, IOException {
		List<TokenObject> response = new ArrayList<TokenObject>();
		try {
			modelIn = new FileInputStream(Config.getModelDataPath() + "en-pos.bin");
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
		String[] tokens = Tokenizer.getTokens(sentence);
		String tags[] = tagger.tag(tokens);

		for (int i = 0; i < tags.length; i++) {
			response.add(new TokenObject(tokens[i], tags[i]));
		}

		return response;
	}
}
