package opennlp.source.chuncker.trainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import core.util.WriteFile;
import opennlp.source.pos.builder.PosBuilder;
import opennlp.source.sentencer.SentenceDetector;
import opennlp.tools.util.InvalidFormatException;

/**
 * This to test sample text with stanford nlp for chunker creation.
 * 
 * @author root
 *
 */
public class ChunTrainDataCreatorOpennlp {
	private static final Logger LOG = LoggerFactory.getLogger(ChunTrainDataCreatorOpennlp.class);

	public void generateChunkerTrainData(String wholeText) throws InvalidFormatException, IOException {
		String[] opennlpSentences = SentenceDetector.getSentences(wholeText);

		for (String sentence : opennlpSentences) {
			if (!sentence.trim().equals("")) {
				LOG.debug("open-nlp sentence : " + sentence);

				List<TokenObject> listOfTO = PosBuilder.getPOSTags(sentence);
				List<TokenObject> response = TokenObjectCreator.generatePhrases((ArrayList<TokenObject>) listOfTO);

				for (TokenObject tokenObject : response) {
					String result = tokenObject.getToken() + " " + tokenObject.getPOS() + " " + tokenObject.getChunkerToken();
					// LOG.debug(result);
					System.out.println(result);
					WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", result);
				}
				WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", "");
			}
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		String wholeText = "“James B Stewart” Common Sense column observes Apple, formerly market laggard, has far distanced Microsoft in share price since January 2014.";
		ChunTrainDataCreatorOpennlp ctdo = new ChunTrainDataCreatorOpennlp();
		ctdo.generateChunkerTrainData(wholeText);
	}
}
