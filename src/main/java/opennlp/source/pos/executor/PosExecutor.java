package opennlp.source.pos.executor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import opennlp.source.chuncker.trainer.TokenObject;
import opennlp.source.tokenizer.executor.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.InvalidFormatException;

/**
 * Create part-of-speech tags using opennlp
 * 
 * @author root
 *
 */
public class PosExecutor {
	static InputStream modelIn = null;
	static POSModel model = null;
	private static final Logger LOG = LoggerFactory.getLogger(PosExecutor.class);
	static {
		try {
			modelIn = new FileInputStream(Config.getModelDataPath() + "en-pos.bin");
			model = new POSModel(modelIn);
			LOG.info("POS model has been loaded from " + Config.getModelDataPath() + "en-pos.bin");
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
	}

	public static ResponseObject getPOSTags(String sentence) throws InvalidFormatException, IOException {
		POSTaggerME tagger = new POSTaggerME(model);
		String[] tokens = Tokenizer.getTokens(sentence);
		String tags[] = tagger.tag(tokens);

		ResponseObject response = new ResponseObject(tokens, tags);

		return response;
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		ResponseObject result = getPOSTags(ReadTxtFile.getString("build-training-models/paragraph.txt"));
		for (int i = 0; i < result.getTags().length; i++) {
			System.out.println(result.getTokens()[i] +  "_"+result.getTags()[i]);
		}
	}
}